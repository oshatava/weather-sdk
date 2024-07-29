package com.osh.weather_sdk.common.screen

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

/**
 * Interface for a ViewModel delegate that manages UI state and events,
 * and handles navigation events.
 *
 * @param UiState The type representing the state of the UI.
 * @param UiEvent The type representing the events that can occur in the UI.
 * @param NavigationEvent The type representing navigation events.
 */
interface ScreenViewModelDelegate<UiState, UiEvent, NavigationEvent> {
    /**
     * A flow of navigation events to be observed.
     */
    val navigationEventsFlow: Flow<NavigationEvent>

    /**
     * A flow of UI state to be observed.
     */
    val uiStateFlow: Flow<UiState>

    /**
     * Binds the ViewModel to the provided CoroutineScope for managing coroutine lifecycles.
     *
     * @param viewModelScope The CoroutineScope to bind the ViewModel to.
     */
    fun bindTo(viewModelScope: CoroutineScope)

    /**
     * Handles UI events by processing them and updating the UI state accordingly.
     *
     * @param uiEvent The event that occurred in the UI.
     */
    fun onEvent(uiEvent: UiEvent)

    /**
     * Clears resources and performs cleanup when the ViewModel is no longer needed.
     */
    fun onClear()
}
