package com.osh.weather_sdk.source.weatherbit.impl.di

import com.osh.weather_sdk.common.logger.LoggerBuilder
import com.osh.weather_sdk.common.di.Provider
import com.osh.weather_sdk.common.source.WeatherSource
import com.osh.weather_sdk.source.weatherbit.WeatherSourceConfig

internal class WeatherSourceComponent(
    private val loggerBuilder: Provider<LoggerBuilder>
) {
    private val weatherBitApiModule by lazy (::WeatherBitApiModule)
    private val weatherSourceModule by lazy {
        WeatherSourceModule(loggerBuilder = loggerBuilder)
    }

    fun provideWeatherSource(
        config: WeatherSourceConfig
    ): Provider<WeatherSource> {
        return weatherSourceModule.provideWeatherSource(
            weatherBitApi = weatherBitApiModule.provideWeatherBitApi(config)
        )
    }
}