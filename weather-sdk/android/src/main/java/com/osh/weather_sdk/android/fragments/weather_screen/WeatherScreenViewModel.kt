package com.osh.weather_sdk.android.fragments.weather_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osh.weather_sdk.common.WeatherViewModelApi
import com.osh.weather_sdk.common.screen.weather_forecast.WeatherForecastViewModelContract
import com.osh.weather_sdk.common.screen.weather_forecast.WeatherForecastViewModelContract.UiEvent

internal class WeatherScreenViewModel(
    cityName:String,
    private val weatherViewModelApi: WeatherViewModelApi,
): ViewModel(),
    WeatherForecastViewModelContract.ViewModelDelegate by weatherViewModelApi.createWeatherForecastViewModelDelegate() {

    init {
        bindTo(viewModelScope)
        onEvent(UiEvent.InitEvent(cityName, 24))
    }
}