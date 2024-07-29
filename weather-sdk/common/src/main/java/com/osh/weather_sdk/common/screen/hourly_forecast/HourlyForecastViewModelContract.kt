package com.osh.weather_sdk.common.screen.hourly_forecast

import com.osh.weather_sdk.common.logger.LoggerBuilder
import com.osh.weather_sdk.common.model.ForecastHourly
import com.osh.weather_sdk.common.model.NavigationEvent
import com.osh.weather_sdk.common.screen.ScreenViewModelDelegate
import com.osh.weather_sdk.common.screen.common.model.LoadableState
import com.osh.weather_sdk.common.screen.hourly_forecast.impl.HourlyForecastViewModelDelegateImpl
import com.osh.weather_sdk.common.source.HourlyForecastWeatherSource

/**
 * Contract for the hourly forecast ViewModel delegate, defining the UI state, UI events, and the ViewModel delegate.
 */
interface HourlyForecastViewModelContract {

    /**
     * Data class representing the UI state for the hourly forecast screen.
     *
     * @param forecastHourly The hourly forecast data, initially set to a loading state.
     */
    data class UiState(
        val forecastHourly: LoadableState<ForecastHourly> = LoadableState.Load,
    )

    /**
     * Sealed class representing the different events that can occur in the hourly forecast UI.
     */
    sealed class UiEvent {
        /**
         * Event to initialize the hourly forecast screen with a specified city name and number of hours.
         *
         * @param cityName The name of the city to initialize the forecast screen with.
         * @param hours The number of hours for which to initialize the forecast.
         */
        data class InitEvent(val cityName: String, val hours: Int) : UiEvent()

        /**
         * Event to load the hourly forecast data for a specified city name and number of hours.
         *
         * @param cityName The name of the city to load the forecast data for.
         * @param hours The number of hours for which to load the forecast.
         */
        data class LoadEvent(val cityName: String, val hours: Int) : UiEvent()
    }

    /**
     * Interface for the ViewModel delegate, extending the ScreenViewModelDelegate interface.
     */
    interface ViewModelDelegate : ScreenViewModelDelegate<UiState, UiEvent, NavigationEvent> {
        companion object {
            /**
             * Factory method to create an instance of ViewModelDelegate.
             *
             * @param source The source of hourly forecast data.
             * @param logger The logger builder for logging purposes.
             * @return An instance of ViewModelDelegate.
             */
            fun from(
                source: HourlyForecastWeatherSource,
                logger: LoggerBuilder
            ): ViewModelDelegate = HourlyForecastViewModelDelegateImpl(
                source, logger
            )
        }
    }
}


