package com.osh.weather_sdk.source.weatherbit.impl.model

import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponse(
    val data: List<ForecastData>? = null,
    val error: String? = null
)

@Serializable
data class ForecastData(
    val temp: Double,
    val ts: Long,
    val weather: Weather,
)