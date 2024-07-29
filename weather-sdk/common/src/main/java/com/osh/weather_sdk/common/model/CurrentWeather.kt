package com.osh.weather_sdk.common.model

/**
 * Data class representing the current weather information.
 *
 * @param location The location where the weather data is recorded.
 * @param temperature The current temperature at the specified location.
 * @param description A description of the current weather conditions.
 * @param timestampLocal The local timestamp when the weather data was recorded.
 */
data class CurrentWeather(
    val location: String,
    val temperature: Double,
    val description: String,
    val timestampLocal: Long
)
