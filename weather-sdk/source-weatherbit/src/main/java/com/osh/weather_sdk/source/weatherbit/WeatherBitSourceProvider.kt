package com.osh.weather_sdk.source.weatherbit

import androidx.annotation.MainThread
import com.osh.weather_sdk.common.di.Provider
import com.osh.weather_sdk.common.logger.LoggerBuilder
import com.osh.weather_sdk.common.source.WeatherSource
import com.osh.weather_sdk.source.weatherbit.impl.di.WeatherSourceComponent

/**
 * Singleton object responsible for providing instances of [WeatherSource].
 */
object WeatherBitSourceProvider {

    private var componentInstance: WeatherSourceComponent? = null

    private val component: WeatherSourceComponent
        get() {
            return ensureComponentExistsOrThrow()
        }

    /**
     * Initializes the WeatherBitSourceProvider.
     *
     * @param loggerBuilder Provider for the [LoggerBuilder] dependency.
     * @return The [WeatherBitSourceProvider] instance for method chaining.
     */
    @MainThread
    fun create(
        loggerBuilder: Provider<LoggerBuilder>
    ) = apply {
        componentInstance = WeatherSourceComponent(loggerBuilder)
    }

    /**
     * Provides a [WeatherSource] instance based on the given configuration.
     *
     * @param config A lambda function that provides a [WeatherSourceConfig] instance.
     * @return A [Provider] for [WeatherSource] configured with the provided [WeatherSourceConfig].
     */
    fun source(config: () -> WeatherSourceConfig): Provider<WeatherSource> {
        return component.provideWeatherSource(config())
    }

    private fun ensureComponentExistsOrThrow(): WeatherSourceComponent {
        return componentInstance
            ?: error("WeatherSourceComponent::create method must be called first!")
    }
}
