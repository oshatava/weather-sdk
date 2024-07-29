package com.osh.weather_sdk.source.weatherbit.impl.mappers

import com.osh.weather_sdk.common.model.ApiResult
import com.osh.weather_sdk.common.model.ForecastHourly
import com.osh.weather_sdk.common.model.ForecastItem
import com.osh.weather_sdk.common.model.toError
import com.osh.weather_sdk.common.model.toSuccess
import com.osh.weather_sdk.source.weatherbit.impl.model.ForecastResponse

internal class HourlyForecastWeatherMapper {
    fun map(forecastResponse: ForecastResponse): ApiResult<ForecastHourly> {
        return forecastResponse.data?.let { data ->
            ForecastHourly(
                data.map {
                    ForecastItem(
                        it.temp,
                        it.weather.description,
                        it.ts,
                    )
                }
            ).toSuccess()
        } ?: forecastResponse.error.toError()
    }
}