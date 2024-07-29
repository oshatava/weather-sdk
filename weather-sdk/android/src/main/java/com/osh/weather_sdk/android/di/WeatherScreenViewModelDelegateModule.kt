package com.osh.weather_sdk.android.di

import com.osh.weather_sdk.common.WeatherViewModelApi
import com.osh.weather_sdk.common.di.Provider
import com.osh.weather_sdk.common.logger.LoggerBuilder
import com.osh.weather_sdk.common.screen.current_weather.CurrentWeatherViewModelContract
import com.osh.weather_sdk.common.screen.hourly_forecast.HourlyForecastViewModelContract
import com.osh.weather_sdk.common.screen.weather_forecast.WeatherForecastViewModelContract
import com.osh.weather_sdk.common.source.WeatherSource

internal class WeatherScreenViewModelDelegateModule(
    private val weatherSource: Provider<WeatherSource>,
    private val loggerBuilder: Provider<LoggerBuilder>,
): WeatherViewModelApi {

    override fun createCurrentWeatherViewModelDelegate(): CurrentWeatherViewModelContract.ViewModelDelegate {
        return CurrentWeatherViewModelContract.ViewModelDelegate.from(
            weatherSource.get(),
            loggerBuilder.get()
        )
    }

    override fun createHourlyForecastViewModelDelegate(): HourlyForecastViewModelContract.ViewModelDelegate {
        return HourlyForecastViewModelContract.ViewModelDelegate.from(
            weatherSource.get(),
            loggerBuilder.get()
        )
    }

    override fun createWeatherForecastViewModelDelegate(): WeatherForecastViewModelContract.ViewModelDelegate {
        return WeatherForecastViewModelContract.ViewModelDelegate.from(
            createCurrentWeatherViewModelDelegate(),
            createHourlyForecastViewModelDelegate(),
            loggerBuilder.get()
        )
    }
}