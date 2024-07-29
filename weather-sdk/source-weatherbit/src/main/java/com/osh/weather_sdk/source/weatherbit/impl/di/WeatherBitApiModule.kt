package com.osh.weather_sdk.source.weatherbit.impl.di

import com.osh.weather_sdk.common.di.map
import com.osh.weather_sdk.common.di.single
import com.osh.weather_sdk.source.weatherbit.WeatherSourceConfig
import com.osh.weather_sdk.source.weatherbit.impl.api.WeatherBitApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

internal class WeatherBitApiModule {

    private val apiKeyInterceptorCreator = map { apiKey: String ->
        Interceptor { chain ->
            val currentUrl = chain.request().url
            val newUrl = currentUrl.newBuilder().addQueryParameter("key", apiKey).build()
            val currentRequest = chain.request().newBuilder()
            val newRequest = currentRequest.url(newUrl).build()
            chain.proceed(newRequest)
        }
    }

    private val loggingInterceptor = single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val clientCreator = map { apiKey: String ->
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptorCreator.getOrCreate(apiKey).get())
            .addInterceptor(loggingInterceptor.get())
            .build()
    }

    private val retrofitCreator = map { config: WeatherSourceConfig ->
        val networkJson = Json { ignoreUnknownKeys = true }
        Retrofit.Builder()
            .baseUrl(config.baseUrl)
            .addConverterFactory(
                networkJson.asConverterFactory("application/json; charset=UTF8".toMediaType())
            )
            .client(clientCreator.getOrCreate(config.apiKey).get())
            .build()
    }

    fun provideWeatherBitApi(
        config: WeatherSourceConfig
    ) = single { retrofitCreator.getOrCreate(config).get().create(WeatherBitApi::class.java) }
}