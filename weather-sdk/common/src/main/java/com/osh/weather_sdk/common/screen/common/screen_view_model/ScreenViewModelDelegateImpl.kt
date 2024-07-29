package com.osh.weather_sdk.common.screen.common.screen_view_model

import com.osh.weather_sdk.common.logger.Logger
import com.osh.weather_sdk.common.screen.ScreenViewModelDelegate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

internal abstract class ScreenViewModelDelegateImpl<UiState, UiEvent, NavigationEvent>(
    initialUiState: UiState,
    private val logger: Logger,
): ScreenViewModelDelegate<UiState, UiEvent, NavigationEvent> {

    private val _navigationEventsFlow = MutableSharedFlow<NavigationEvent>()
    private val _uiStateFlow = MutableStateFlow(initialUiState)
    private var _viewModelScope:CoroutineScope? = null
    private val viewModelScope:CoroutineScope get() {
        return _viewModelScope
            ?: error("View mode does not bind to CoroutineScope. Please use bindTo method.")
    }

    override val navigationEventsFlow: Flow<NavigationEvent> = _navigationEventsFlow

    override val uiStateFlow: Flow<UiState>
        get() = _uiStateFlow

    override fun bindTo(viewModelScope: CoroutineScope) {
        this._viewModelScope = viewModelScope
    }

    final override fun onClear() {
        logger.d { "Clear view model" }
        runCatching { clear() }
            .onSuccess { logger.d { "Clear data success" } }
            .onFailure { logger.e { "Clear data error - $it" } }
        viewModelScope.cancel()
    }

    final override fun onEvent(uiEvent: UiEvent) {
        logger.d { "On got event - $uiEvent" }
        viewModelScope.launch {
            runCatching { processUiEvent(_uiStateFlow.value, uiEvent) }
                .onSuccess { result ->
                    result.newState.let(::publishUiState)
                    result.navigationEvent?.let(::publishNavigationEvent)
                    result.uiEvent?.let(::onEvent)
                }
                .onFailure {
                    logger.e { "onEvent($uiEvent) error - $it" }
                }
        }
    }

    private fun publishNavigationEvent(navigationEvent: NavigationEvent) {
        viewModelScope.launch {
            _navigationEventsFlow.emit(navigationEvent)
        }
    }

    private fun publishUiState(uiState: UiState) {
        viewModelScope.launch {
            _uiStateFlow.emit(uiState)
        }
    }

    abstract suspend fun processUiEvent(
        uiState: UiState,
        uiEvent: UiEvent
    ): ReduceResult<UiState, UiEvent, NavigationEvent>

    open fun clear() {}

    data class ReduceResult<UiState, UiEvent, NavigationEvent>(
        val newState: UiState,
        val uiEvent: UiEvent? = null,
        val navigationEvent: NavigationEvent? = null
    )

    internal fun UiState.toNavigation(
        navigationEvent: NavigationEvent
    ) = ReduceResult<UiState, UiEvent, NavigationEvent>(
        newState = this,
        navigationEvent = navigationEvent,
    )

    internal fun UiState.toResult() = ReduceResult<UiState, UiEvent, NavigationEvent>(
        newState = this,
    )

    internal fun UiState.withEvent(uiEvent: UiEvent) = ReduceResult<UiState, UiEvent, NavigationEvent>(
        newState = this,
        uiEvent = uiEvent,
    )
}