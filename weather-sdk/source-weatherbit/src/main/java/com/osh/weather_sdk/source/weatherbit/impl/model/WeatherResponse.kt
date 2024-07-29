package com.osh.weather_sdk.source.weatherbit.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val data: List<WeatherData>? = null,
    val error: String? = null
)

@Serializable
data class WeatherData(
    @SerialName("city_name") val cityName: String,
    @SerialName("temp") val temp: Double,
    @SerialName("ts") val ts: Long,
    @SerialName("weather") val weather: Weather,
)

