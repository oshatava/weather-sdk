package com.osh.weather_sdk.android

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import com.osh.weather_sdk.android.di.WeatherSDKComponent
import com.osh.weather_sdk.android.fragments.weather_screen.WeatherScreenFragmentContract
import com.osh.weather_sdk.common.WeatherViewModelApi
import com.osh.weather_sdk.common.di.Provider
import com.osh.weather_sdk.common.logger.LoggerBuilder
import com.osh.weather_sdk.common.screen.weather_forecast.WeatherScreenContract
import com.osh.weather_sdk.common.source.WeatherSource

/**
 * Singleton object representing the Weather SDK, which implements the [WeatherFragmentScreenApi]
 * and [WeatherViewModelApi] interfaces.
 */
object WeatherSDK : WeatherFragmentScreenApi, WeatherViewModelApi {
    private var componentInstance: WeatherSDKComponent? = null

    internal val component: WeatherSDKComponent
        get() {
            return ensureComponentExistsOrThrow()
        }

    /**
     * Initializes the WeatherSDK.
     *
     * @param weatherSourceProvider Provider for the [WeatherSource] dependency.
     * @param loggerBuilder Provider for the [LoggerBuilder] dependency.
     */
    @MainThread
    fun create(
        weatherSourceProvider: Provider<WeatherSource>,
        loggerBuilder: Provider<LoggerBuilder>
    ) {
        componentInstance = WeatherSDKComponent(weatherSourceProvider, loggerBuilder)
    }

    /**
     * Creates a [WeatherScreenContract] for a Fragment screen with the specified ID.
     *
     * @param id The ID for the weather screen contract.
     * @return A fragment [WeatherScreenContract].
     */
    override fun createWeatherScreenContract(
        id: String
    ): WeatherScreenContract<Fragment, Fragment> {
        ensureComponentExistsOrThrow()
        return WeatherScreenFragmentContract(id)
    }

    /**
     * Creates a [CurrentWeatherViewModelDelegate].
     *
     * @return An instance of [CurrentWeatherViewModelDelegate].
     */
    override fun createCurrentWeatherViewModelDelegate() = component
        .createCurrentWeatherViewModelDelegate().get()

    /**
     * Creates an [HourlyForecastViewModelDelegate].
     *
     * @return An instance of [HourlyForecastViewModelDelegate].
     */
    override fun createHourlyForecastViewModelDelegate() = component
        .createHourlyForecastViewModelDelegate().get()

    /**
     * Creates a [WeatherForecastViewModelDelegate].
     *
     * @return An instance of [WeatherForecastViewModelDelegate].
     */
    override fun createWeatherForecastViewModelDelegate() = component
        .createWeatherForecastViewModelDelegate().get()

    private fun ensureComponentExistsOrThrow(): WeatherSDKComponent {
        return componentInstance ?: error("WeatherSDK::create method must be called first!")
    }
}

