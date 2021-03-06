package com.vorobyoff.weather.data.datasource.remote

import com.vorobyoff.weather.data.datasource.remote.adapter.ResultAdapterFactory
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object WeatherNetworkFactory {
    private const val QUERY_NAME = "apikey"
    private const val API_KEY = "TRDr3RAE8uvrdfUx8kj3bCojJJKd0PEM"
    private const val BASE_URL = "http://dataservice.accuweather.com"

    val weatherApi: AccuWeatherApi
        get() = Retrofit.Builder()
            .addCallAdapterFactory(adapter())
            .addConverterFactory(converter())
            .baseUrl(BASE_URL)
            .client(client())
            .build().create()

    private fun adapter(): CallAdapter.Factory = ResultAdapterFactory()

    private fun converter(): Converter.Factory = MoshiConverterFactory.create()

    private fun client(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor())
        .addInterceptor(requestInterceptor())
        .retryOnConnectionFailure(true)
        .build()

    private fun loggingInterceptor(): Interceptor = HttpLoggingInterceptor().setLevel(Level.BODY)

    private fun requestInterceptor() = Interceptor { chain ->
        val old: Request = chain.request()
        val newUrl: HttpUrl = old.url.newBuilder().addQueryParameter(QUERY_NAME, API_KEY).build()
        val builder: Request.Builder = old.newBuilder()
            .addHeader(name = "Content-Type", value = "application/json;charset=utf-8")
            .addHeader(name = "Accept", value = "application/json")
            .url(newUrl)

        chain.proceed(builder.build())
    }
}