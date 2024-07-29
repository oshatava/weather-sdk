package com.osh.weather_sdk.common.screen.hourly_forecast.impl

import com.osh.weather_sdk.common.logger.LoggerBuilder
import com.osh.weather_sdk.common.model.NavigationEvent
import com.osh.weather_sdk.common.screen.common.model.LoadableState
import com.osh.weather_sdk.common.screen.common.model.toUIState
import com.osh.weather_sdk.common.screen.common.screen_view_model.ScreenViewModelDelegateImpl
import com.osh.weather_sdk.common.screen.hourly_forecast.HourlyForecastViewModelContract.UiEvent
import com.osh.weather_sdk.common.screen.hourly_forecast.HourlyForecastViewModelContract.UiState
import com.osh.weather_sdk.common.screen.hourly_forecast.HourlyForecastViewModelContract.ViewModelDelegate
import com.osh.weather_sdk.common.source.HourlyForecastWeatherSource

internal class HourlyForecastViewModelDelegateImpl(
    private val source: HourlyForecastWeatherSource,
    logger: LoggerBuilder,
) : ViewModelDelegate, ScreenViewModelDelegateImpl<UiState, UiEvent, NavigationEvent>(
        initialUiState = UiState(),
        logger = logger.tag(LOGGER_TAG)
    ) {

    override suspend fun processUiEvent(
        uiState: UiState,
        uiEvent: UiEvent
    ): ReduceResult<UiState, UiEvent, NavigationEvent> {
        return when (uiEvent) {
            is UiEvent.LoadEvent -> uiState
                .copy(
                    forecastHourly = source.requestForecastHourly(
                        cityName = uiEvent.cityName,
                        hours = uiEvent.hours
                    ).toUIState()
                ).let {
                    when {
                        it.forecastHourly is LoadableState.Error -> it.toNavigation(
                            NavigationEvent.OnFinishedWithError
                        )
                        else -> it.toResult()
                    }
                }

            is UiEvent.InitEvent -> uiState.copy(
                forecastHourly = LoadableState.Load,
            ).withEvent(
                UiEvent.LoadEvent(
                    cityName = uiEvent.cityName,
                    hours = uiEvent.hours
                )
            )
        }
    }

    companion object{
        const val LOGGER_TAG = "HourlyForecastViewModelDelegateImpl"
    }
}