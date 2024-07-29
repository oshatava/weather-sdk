package com.osh.weather_sdk.common.screen.weather_forecast

import com.osh.weather_sdk.common.logger.LoggerBuilder
import com.osh.weather_sdk.common.model.CurrentWeather
import com.osh.weather_sdk.common.model.ForecastHourly
import com.osh.weather_sdk.common.model.NavigationEvent
import com.osh.weather_sdk.common.screen.ScreenViewModelDelegate
import com.osh.weather_sdk.common.screen.common.model.LoadableState
import com.osh.weather_sdk.common.screen.current_weather.CurrentWeatherViewModelContract
import com.osh.weather_sdk.common.screen.hourly_forecast.HourlyForecastViewModelContract
import com.osh.weather_sdk.common.screen.weather_forecast.impl.WeatherForecastViewModelDelegateImpl

/**
 * Contract for the weather forecast ViewModel delegate, defining the UI state, UI events, and the ViewModel delegate.
 */
interface WeatherForecastViewModelContract {

    /**
     * Data class representing the UI state for the weather forecast screen.
     *
     * @param currentWeather The current weather data, initially set to a loading state.
     * @param hourly The hourly forecast data, initially set to a loading state.
     */
    data class UiState(
        val currentWeather: LoadableState<CurrentWeather> = LoadableState.Load,
        val hourly: LoadableState<ForecastHourly> = LoadableState.Load,
    )

    /**
     * Sealed class representing the different events that can occur in the weather forecast UI.
     */
    sealed class UiEvent {
        /**
         * Event to initialize the weather forecast screen with a specified city name and number of hours.
         *
         * @param cityName The name of the city to initialize the forecast screen with.
         * @param hours The number of hours for which to initialize the forecast.
         */
        data class InitEvent(val cityName: String, val hours: Int) : UiEvent()

        /**
         * Event to close the weather forecast screen.
         */
        data object CloseEvent : UiEvent()
    }

    /**
     * Interface for the ViewModel delegate, extending the ScreenViewModelDelegate interface.
     */
    interface ViewModelDelegate :
        ScreenViewModelDelegate<UiState, UiEvent, NavigationEvent> {
        companion object {
            /**
             * Factory method to create an instance of ViewModelDelegate.
             *
             * @param currentWeatherViewModel The ViewModelDelegate for current weather.
             * @param hourlyForecastViewModel The ViewModelDelegate for hourly forecast.
             * @param loggerBuilder The logger builder for logging purposes.
             * @return An instance of [ViewModelDelegate].
             */
            fun from(
                currentWeatherViewModel: CurrentWeatherViewModelContract.ViewModelDelegate,
                hourlyForecastViewModel: HourlyForecastViewModelContract.ViewModelDelegate,
                loggerBuilder: LoggerBuilder,
            ): ViewModelDelegate = WeatherForecastViewModelDelegateImpl(
                currentWeatherViewModel, hourlyForecastViewModel, loggerBuilder
            )
        }
    }
}
