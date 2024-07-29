package com.osh.weather_sdk.common.screen.current_weather

import com.osh.weather_sdk.common.logger.LoggerBuilder
import com.osh.weather_sdk.common.model.CurrentWeather
import com.osh.weather_sdk.common.model.NavigationEvent
import com.osh.weather_sdk.common.screen.ScreenViewModelDelegate
import com.osh.weather_sdk.common.screen.common.model.LoadableState
import com.osh.weather_sdk.common.screen.current_weather.impl.CurrentWeatherViewModelDelegateImpl
import com.osh.weather_sdk.common.source.CurrentWeatherSource

/**
 * Contract for the current weather ViewModel delegate, defining the UI state, UI events, and the ViewModel delegate.
 */
interface CurrentWeatherViewModelContract {

    /**
     * Data class representing the UI state for the current weather screen.
     *
     * @param currentWeather The current weather data, initially set to a loading state.
     */
    data class UiState(
        val currentWeather: LoadableState<CurrentWeather> = LoadableState.Load,
    )

    /**
     * Sealed class representing the different events that can occur in the current weather UI.
     */
    sealed class UiEvent {
        /**
         * Event to initialize the current weather screen with a specified city name.
         *
         * @param cityName The name of the city to initialize the weather screen with.
         */
        data class InitEvent(val cityName: String) : UiEvent()

        /**
         * Event to load the current weather data for a specified city name.
         *
         * @param cityName The name of the city to load the weather data for.
         */
        data class LoadEvent(val cityName: String) : UiEvent()
    }

    /**
     * Interface for the ViewModel delegate, extending the ScreenViewModelDelegate interface.
     */
    interface ViewModelDelegate : ScreenViewModelDelegate<UiState, UiEvent, NavigationEvent> {
        companion object {
            /**
             * Factory method to create an instance of ViewModelDelegate.
             *
             * @param source The source of current weather data.
             * @param logger The logger builder for logging purposes.
             * @return An instance of ViewModelDelegate.
             */
            fun from(
                source: CurrentWeatherSource,
                logger: LoggerBuilder
            ): ViewModelDelegate = CurrentWeatherViewModelDelegateImpl(
                source, logger
            )
        }
    }
}


