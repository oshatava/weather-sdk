package com.osh.weather_sdk.common

import com.osh.weather_sdk.common.screen.weather_forecast.WeatherScreenContract

/**
 * WeatherScreenApi interface provides a method to create a WeatherScreenContract
 * for different weather-related screens.
 *
 * @param BindContext The type of the context used for binding.
 * @param UiScreen The type of the UI screen.
 */
interface WeatherScreenApi<BindContext, UiScreen> {

    /**
     * Creates and returns a WeatherScreenContract for the weather screen.
     *
     * @param id An optional identifier for the weather screen, defaulting to "WeatherScreen".
     * @return A WeatherScreenContract for the specified weather screen.
     */
    fun createWeatherScreenContract(
        id: String = "WeatherScreen"
    ): WeatherScreenContract<BindContext, UiScreen>
}
