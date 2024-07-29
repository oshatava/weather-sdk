package com.osh.weather_sdk.common.source

/**
 * WeatherSource interface combines the capabilities of HourlyForecastWeatherSource and
 * CurrentWeatherSource interfaces, providing methods to request both current weather
 * and hourly forecast data.
 */
interface WeatherSource : HourlyForecastWeatherSource, CurrentWeatherSource
