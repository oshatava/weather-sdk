package com.osh.ginisample

import android.app.Application
import com.osh.weather_sdk.android.WeatherSDK
import com.osh.weather_sdk.logger.WeatherAndroidLogger
import com.osh.weather_sdk.source.weatherbit.WeatherBitSourceProvider
import com.osh.weather_sdk.source.weatherbit.WeatherSourceConfig

internal class App:Application() {

    override fun onCreate() {
        super.onCreate()

        val config = WeatherSourceConfig(apiKey = "7af006aded104594b0f08a3cf1e1422c")
        val loggerBuilder = WeatherAndroidLogger.create()
        val source = WeatherBitSourceProvider.create(loggerBuilder).source { config }
        WeatherSDK.create(
            weatherSourceProvider = source,
            loggerBuilder = loggerBuilder
        )
    }
}