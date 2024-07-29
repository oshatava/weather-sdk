package com.osh.weather_sdk.source.weatherbit

/**
 * The default base URL for the weather service API.
 */
const val DEFAULT_BASE_URL = "https://api.weatherbit.io"

/**
 * Data class representing the configuration for the weather source.
 *
 * @param apiKey The API key used to authenticate requests to the weather service.
 * @param baseUrl The base URL of the weather service API. Defaults to [DEFAULT_BASE_URL].
 */
data class WeatherSourceConfig(
    val apiKey: String,
    val baseUrl: String = DEFAULT_BASE_URL,
)
