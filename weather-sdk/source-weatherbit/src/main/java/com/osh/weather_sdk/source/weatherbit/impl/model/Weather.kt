package com.osh.weather_sdk.source.weatherbit.impl.model

import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    val description: String
)