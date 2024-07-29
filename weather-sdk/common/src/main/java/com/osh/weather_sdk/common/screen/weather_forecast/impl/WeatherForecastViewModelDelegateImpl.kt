package com.osh.weather_sdk.common.screen.weather_forecast.impl

import com.osh.weather_sdk.common.logger.LoggerBuilder
import com.osh.weather_sdk.common.model.NavigationEvent
import com.osh.weather_sdk.common.screen.ScreenViewModelDelegate
import com.osh.weather_sdk.common.screen.current_weather.CurrentWeatherViewModelContract
import com.osh.weather_sdk.common.screen.hourly_forecast.HourlyForecastViewModelContract
import com.osh.weather_sdk.common.screen.weather_forecast.WeatherForecastViewModelContract.UiEvent
import com.osh.weather_sdk.common.screen.weather_forecast.WeatherForecastViewModelContract.UiState
import com.osh.weather_sdk.common.screen.weather_forecast.WeatherForecastViewModelContract.ViewModelDelegate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch

internal class WeatherForecastViewModelDelegateImpl(
    private val currentWeatherViewModel: CurrentWeatherViewModelContract.ViewModelDelegate,
    private val hourlyForecastViewModel: HourlyForecastViewModelContract.ViewModelDelegate,
    loggerBuilder: LoggerBuilder
) : ViewModelDelegate, ScreenViewModelDelegate<UiState, UiEvent, NavigationEvent> {

    private val logger by lazy{
        loggerBuilder.tag(LOGGER_TAG)
    }

    private val ownNavigationEventsFlow = MutableSharedFlow<NavigationEvent>()
    private var _viewModelScope: CoroutineScope? = null
    private val viewModelScope: CoroutineScope get() {
        return _viewModelScope?: error("viewModelScope can not be null. bindTo mast be called first!")
    }

    override val navigationEventsFlow: Flow<NavigationEvent> = merge(
        currentWeatherViewModel.navigationEventsFlow,
        hourlyForecastViewModel.navigationEventsFlow,
        ownNavigationEventsFlow
    )

    override val uiStateFlow: Flow<UiState> = combine(
        currentWeatherViewModel.uiStateFlow.map { it.currentWeather },
        hourlyForecastViewModel.uiStateFlow.map { it.forecastHourly },
        ::UiState
    )

    override fun bindTo(viewModelScope: CoroutineScope) {
        _viewModelScope = viewModelScope
            .also(currentWeatherViewModel::bindTo)
            .also(hourlyForecastViewModel::bindTo)
    }

    override fun onClear() {
        currentWeatherViewModel.onClear()
        hourlyForecastViewModel.onClear()
    }

    override fun onEvent(uiEvent: UiEvent) {
        logger.d { "On event $uiEvent" }
        when (uiEvent) {
            UiEvent.CloseEvent -> viewModelScope.launch {
                ownNavigationEventsFlow.emit(NavigationEvent.OnFinished)
            }
            is UiEvent.InitEvent -> {
                currentWeatherViewModel.onEvent(
                    CurrentWeatherViewModelContract.UiEvent.InitEvent(uiEvent.cityName)
                )
                hourlyForecastViewModel.onEvent(
                    HourlyForecastViewModelContract.UiEvent.InitEvent(
                        uiEvent.cityName,
                        uiEvent.hours
                    )
                )
            }
        }
    }

    companion object{
        const val LOGGER_TAG = "WeatherForecastViewModelDelegate"
    }
}