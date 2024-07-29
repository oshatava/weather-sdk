package com.osh.weather_sdk.android.di

import com.osh.weather_sdk.android.fragments.weather_screen.WeatherScreenViewModel
import com.osh.weather_sdk.common.WeatherViewModelApi
import com.osh.weather_sdk.common.di.factory

internal class WeatherScreenViewModelFactoryModule {

    fun provideWeatherScreenViewModel(
        cityName: String,
        weatherViewModelApi: WeatherViewModelApi,
    ) = factory {
        WeatherScreenViewModel(
            cityName = cityName,
            weatherViewModelApi
        )
    }
}