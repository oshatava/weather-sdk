package com.osh.weather_sdk.common

import com.osh.weather_sdk.common.screen.current_weather.CurrentWeatherViewModelContract
import com.osh.weather_sdk.common.screen.hourly_forecast.HourlyForecastViewModelContract
import com.osh.weather_sdk.common.screen.weather_forecast.WeatherForecastViewModelContract

/**
 * WeatherViewModelApi interface provides methods to create ViewModelDelegates
 * for different weather-related ViewModels.
 */
interface WeatherViewModelApi {

    /**
     * Creates and returns a ViewModelDelegate for the current weather.
     *
     * @return A ViewModelDelegate for the CurrentWeatherViewModel.
     */
    fun createCurrentWeatherViewModelDelegate(): CurrentWeatherViewModelContract.ViewModelDelegate

    /**
     * Creates and returns a ViewModelDelegate for the hourly weather forecast.
     *
     * @return A ViewModelDelegate for the HourlyForecastViewModel.
     */
    fun createHourlyForecastViewModelDelegate(): HourlyForecastViewModelContract.ViewModelDelegate

    /**
     * Creates and returns a ViewModelDelegate for the weather forecast.
     *
     * @return A ViewModelDelegate for the WeatherForecastViewModel.
     */
    fun createWeatherForecastViewModelDelegate(): WeatherForecastViewModelContract.ViewModelDelegate
}
