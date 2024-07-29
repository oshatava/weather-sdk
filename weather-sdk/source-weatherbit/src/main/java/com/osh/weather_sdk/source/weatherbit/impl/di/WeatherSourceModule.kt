package com.osh.weather_sdk.source.weatherbit.impl.di

import com.osh.weather_sdk.common.logger.LoggerBuilder
import com.osh.weather_sdk.common.di.Provider
import com.osh.weather_sdk.common.di.factory
import com.osh.weather_sdk.common.di.single
import com.osh.weather_sdk.common.source.WeatherSource
import com.osh.weather_sdk.source.weatherbit.impl.WeatherSourceImpl
import com.osh.weather_sdk.source.weatherbit.impl.api.WeatherBitApi
import com.osh.weather_sdk.source.weatherbit.impl.mappers.CurrentWeatherMapper
import com.osh.weather_sdk.source.weatherbit.impl.mappers.HourlyForecastWeatherMapper

internal class WeatherSourceModule(
    private val loggerBuilder: Provider<LoggerBuilder>
) {

    private val currentWeatherMapper = single { CurrentWeatherMapper() }

    private val hourlyForecastWeatherMapper = single { HourlyForecastWeatherMapper() }

    fun provideWeatherSource(
        weatherBitApi:Provider<WeatherBitApi>
    ): Provider<WeatherSource> = factory {
        WeatherSourceImpl(
            api = weatherBitApi.get(),
            currentWeatherMapper = currentWeatherMapper.get(),
            hourlyForecastWeatherMapper = hourlyForecastWeatherMapper.get(),
            loggerBuilder = loggerBuilder,
        )
    }
}