package com.osh.weather_sdk.common.model

/**
 * Data class representing a single forecast item for a specific hour.
 *
 * @param temperature The forecasted temperature for the specific hour.
 * @param description A description of the forecasted weather conditions for the specific hour.
 * @param timestampLocal The local timestamp for the specific hour the forecast is for.
 */
data class ForecastItem(
    val temperature: Double,
    val description: String,
    val timestampLocal: Long
)
