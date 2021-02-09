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

    val weatherApi: AccuWeatherApi by lazy {
        Retrofit.Builder()
            .addCallAdapterFactory(createAdapter())
            .addConverterFactory(createConverter())
            .client(createHttpClient())
            .baseUrl(BASE_URL)
            .build().create()
    }

    private fun createAdapter(): CallAdapter.Factory = ResultAdapterFactory()

    private fun createConverter(): Converter.Factory = MoshiConverterFactory.create()

    private fun createHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(createLoggingInterceptor())
        .addInterceptor(createRequestInterceptor())
        .retryOnConnectionFailure(true)
        .build()

    private fun createLoggingInterceptor(): Interceptor = HttpLoggingInterceptor().setLevel(Level.BODY)

    private fun createRequestInterceptor() = Interceptor { chain ->
        val old: Request = chain.request()
        val newUrl: HttpUrl = old.url.newBuilder().addQueryParameter(QUERY_NAME, API_KEY).build()
        val builder: Request.Builder = old.newBuilder()
            .addHeader(name = "Content-Type", value = "application/json;charset=utf-8")
            .addHeader(name = "Accept", value = "application/json")
            .url(newUrl)

        chain.proceed(builder.build())
    }
}