package com.vorobyoff.weather.data.datasource.network

import com.vorobyoff.weather.data.datasource.network.adapter.ResultAdapterFactory
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object NetworkFactory {
    private const val API_KEY = "TRDr3RAE8uvrdfUx8kj3bCojJJKd0PEM"
    private const val BASE_URL = "http://dataservice.accuweather.com"

    val ACCU_WEATHER_API: AccuWeatherApi by lazy {
        Retrofit.Builder()
                .addCallAdapterFactory(ResultAdapterFactory())
                .addConverterFactory(converter())
                .baseUrl(BASE_URL)
                .client(client())
                .build().create()
    }

    private fun converter(): Converter.Factory = MoshiConverterFactory.create()

    private fun client(): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor())
            .addInterceptor(requestInterceptor())
            .retryOnConnectionFailure(true)
            .build()

    private fun loggingInterceptor(): Interceptor = HttpLoggingInterceptor().setLevel(Level.BODY)

    private fun requestInterceptor() = Interceptor {
        val old: Request = it.request()
        val url: HttpUrl = old.url.newBuilder().addQueryParameter("apikey", API_KEY).build()
        it.proceed(old.newBuilder().url(url).build())
    }
}