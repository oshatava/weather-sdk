package com.osh.weather_sdk.android.di

import com.osh.weather_sdk.common.di.Provider
import com.osh.weather_sdk.common.di.factory
import com.osh.weather_sdk.common.logger.LoggerBuilder
import com.osh.weather_sdk.common.source.WeatherSource

internal class WeatherSDKComponent(
    private val weatherSource: Provider<WeatherSource>,
    private val loggerBuilder: Provider<LoggerBuilder>,
) {
    private val weatherScreenViewModelFactoryModule by lazy {
        WeatherScreenViewModelFactoryModule()
    }

    private val weatherScreenViewModelDelegateModule by lazy {
        WeatherScreenViewModelDelegateModule(weatherSource, loggerBuilder)
    }

    fun provideWeatherScreenViewModelAssisted(cityName: String) =
        weatherScreenViewModelFactoryModule.provideWeatherScreenViewModel(
            cityName = cityName,
            weatherScreenViewModelDelegateModule
        )

    fun createCurrentWeatherViewModelDelegate() = factory {
        weatherScreenViewModelDelegateModule.createCurrentWeatherViewModelDelegate()
    }

    fun createHourlyForecastViewModelDelegate() = factory {
        weatherScreenViewModelDelegateModule.createHourlyForecastViewModelDelegate()
    }

    fun createWeatherForecastViewModelDelegate() = factory {
        weatherScreenViewModelDelegateModule.createWeatherForecastViewModelDelegate()
    }
}