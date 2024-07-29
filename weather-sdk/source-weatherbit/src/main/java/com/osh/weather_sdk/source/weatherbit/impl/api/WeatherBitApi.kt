package com.osh.weather_sdk.source.weatherbit.impl.api

import com.osh.weather_sdk.source.weatherbit.impl.model.ForecastResponse
import com.osh.weather_sdk.source.weatherbit.impl.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query


internal interface WeatherBitApi {
    @GET("v2.0/current")
    suspend fun getCurrentWeather(@Query("city") cityName: String): WeatherResponse

    @GET("v2.0/forecast/hourly")
    suspend fun getHourlyWeatherForecast(
        @Query("city") cityName: String,
        @Query("hours") hours: Int,
    ): ForecastResponse

}