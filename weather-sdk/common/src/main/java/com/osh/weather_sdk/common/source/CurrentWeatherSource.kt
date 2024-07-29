package com.osh.weather_sdk.common.source

import com.osh.weather_sdk.common.model.ApiResult
import com.osh.weather_sdk.common.model.CurrentWeather

/**
 * CurrentWeatherSource interface provides a method to request the current weather
 * data for a given city.
 */
interface CurrentWeatherSource {

    /**
     * Requests the current weather data for the specified city.
     *
     * @param cityName The name of the city for which to request current weather data.
     * @return An ApiResult containing the current weather data for the specified city.
     */
    suspend fun requestCurrent(cityName: String): ApiResult<CurrentWeather>
}
