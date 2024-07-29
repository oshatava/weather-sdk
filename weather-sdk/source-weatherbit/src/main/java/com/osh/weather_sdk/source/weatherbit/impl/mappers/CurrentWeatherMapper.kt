package com.osh.weather_sdk.source.weatherbit.impl.mappers

import com.osh.weather_sdk.common.model.ApiResult
import com.osh.weather_sdk.common.model.CurrentWeather
import com.osh.weather_sdk.common.model.toError
import com.osh.weather_sdk.common.model.toSuccess
import com.osh.weather_sdk.source.weatherbit.impl.model.WeatherResponse

internal class CurrentWeatherMapper {
    fun map(weatherResponse: WeatherResponse): ApiResult<CurrentWeather> {
        return weatherResponse.data?.firstOrNull()?.let { weather ->
            CurrentWeather(
                location = weather.cityName,
                temperature = weather.temp,
                description = weather.weather.description,
                timestampLocal = weather.ts,
            ).toSuccess()
        } ?: weatherResponse.error.toError()
    }
}