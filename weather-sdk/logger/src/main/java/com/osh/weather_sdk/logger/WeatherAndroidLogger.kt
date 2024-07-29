package com.osh.weather_sdk.logger

import com.osh.weather_sdk.common.di.Provider
import com.osh.weather_sdk.common.di.single
import com.osh.weather_sdk.common.logger.LoggerBuilder
import com.osh.weather_sdk.logger.impl.LoggerBuilderImpl


/**
 * Singleton object for creating a logger builder provider specific to Android.
 */
object WeatherAndroidLogger {

    /**
     * Creates a provider for the LoggerBuilder.
     *
     * @return A provider for LoggerBuilder.
     */
    fun create(): Provider<LoggerBuilder> = single { LoggerBuilderImpl() }
}
