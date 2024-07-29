package com.osh.weather_sdk.common.model

/**
 * Data class representing the hourly weather forecast.
 *
 * @param hourly A list of forecast items, each representing the weather forecast for a specific hour.
 */
data class ForecastHourly(
    val hourly: List<ForecastItem>
)
