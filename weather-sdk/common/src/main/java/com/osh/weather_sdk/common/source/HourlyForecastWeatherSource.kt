package com.osh.weather_sdk.common.source

import com.osh.weather_sdk.common.model.ApiResult
import com.osh.weather_sdk.common.model.ForecastHourly

/**
 * HourlyForecastWeatherSource interface provides a method to request the hourly
 * weather forecast data for a given city.
 */
interface HourlyForecastWeatherSource {

    /**
     * Requests the hourly weather forecast data for the specified city.
     *
     * @param cityName The name of the city for which to request hourly weather forecast data.
     * @param hours The number of hours for which to request the forecast.
     * @return An ApiResult containing the hourly weather forecast data for the specified city.
     */
    suspend fun requestForecastHourly(cityName: String, hours: Int): ApiResult<ForecastHourly>
}
