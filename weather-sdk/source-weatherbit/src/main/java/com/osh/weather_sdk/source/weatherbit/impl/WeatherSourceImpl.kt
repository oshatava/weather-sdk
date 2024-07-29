package com.osh.weather_sdk.source.weatherbit.impl

import com.osh.weather_sdk.common.logger.LoggerBuilder
import com.osh.weather_sdk.common.di.Provider
import com.osh.weather_sdk.common.model.ApiResult
import com.osh.weather_sdk.common.model.CurrentWeather
import com.osh.weather_sdk.common.model.ForecastHourly
import com.osh.weather_sdk.common.source.WeatherSource
import com.osh.weather_sdk.source.weatherbit.impl.api.WeatherBitApi
import com.osh.weather_sdk.source.weatherbit.impl.mappers.CurrentWeatherMapper
import com.osh.weather_sdk.source.weatherbit.impl.mappers.HourlyForecastWeatherMapper

internal class WeatherSourceImpl(
    private val api: WeatherBitApi,
    private val currentWeatherMapper: CurrentWeatherMapper,
    private val hourlyForecastWeatherMapper: HourlyForecastWeatherMapper,
    loggerBuilder: Provider<LoggerBuilder>,
): WeatherSource {

    private val logger = loggerBuilder.get().tag(LOGGER_TAG)

    override suspend fun requestCurrent(cityName: String): ApiResult<CurrentWeather> {
        return kotlin.runCatching {
            api.getCurrentWeather(cityName).let (currentWeatherMapper::map)
        }.getOrElse {
            logger.e { "Error for requestCurrent($cityName) - $it" }
            ApiResult.Error(it.message)
        }
    }

    override suspend fun requestForecastHourly(cityName: String, hours: Int): ApiResult<ForecastHourly> {
        return kotlin.runCatching {
            api.getHourlyWeatherForecast(cityName, hours = hours)
                .let(hourlyForecastWeatherMapper::map)
        }.getOrElse {
            logger.e { "Error for requestForecastHourly($cityName, $hours) - $it" }
            ApiResult.Error(it.message)
        }
    }

    companion object{
        private const val LOGGER_TAG = "WeatherSource"
    }
}