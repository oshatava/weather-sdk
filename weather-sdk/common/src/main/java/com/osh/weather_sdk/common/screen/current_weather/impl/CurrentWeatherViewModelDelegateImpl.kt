package com.osh.weather_sdk.common.screen.current_weather.impl

import com.osh.weather_sdk.common.logger.LoggerBuilder
import com.osh.weather_sdk.common.model.NavigationEvent
import com.osh.weather_sdk.common.screen.common.model.LoadableState
import com.osh.weather_sdk.common.screen.common.model.toUIState
import com.osh.weather_sdk.common.screen.common.screen_view_model.ScreenViewModelDelegateImpl
import com.osh.weather_sdk.common.screen.current_weather.CurrentWeatherViewModelContract
import com.osh.weather_sdk.common.screen.current_weather.CurrentWeatherViewModelContract.UiEvent
import com.osh.weather_sdk.common.screen.current_weather.CurrentWeatherViewModelContract.UiState
import com.osh.weather_sdk.common.source.CurrentWeatherSource

internal class CurrentWeatherViewModelDelegateImpl(
    private val source: CurrentWeatherSource,
    logger: LoggerBuilder,
) : CurrentWeatherViewModelContract.ViewModelDelegate,
    ScreenViewModelDelegateImpl<UiState, UiEvent, NavigationEvent>(
        initialUiState = UiState(),
        logger = logger.tag(LOGGER_TAG)
    ) {

    override suspend fun processUiEvent(
        uiState: UiState,
        uiEvent: UiEvent
    ): ReduceResult<UiState, UiEvent, NavigationEvent> {
        return when (uiEvent) {
            is UiEvent.LoadEvent -> uiState
                .copy(currentWeather = source.requestCurrent(uiEvent.cityName).toUIState())
                .let {
                    when {
                        it.currentWeather is LoadableState.Error -> it.toNavigation(
                            NavigationEvent.OnFinishedWithError
                        )

                        else -> it.toResult()
                    }
                }


            is UiEvent.InitEvent -> uiState.copy(
                currentWeather = LoadableState.Load,
            ).withEvent(UiEvent.LoadEvent(uiEvent.cityName))
        }
    }

    companion object {
        const val LOGGER_TAG = "CurrentWeatherViewModelDelegateImpl"
    }
}