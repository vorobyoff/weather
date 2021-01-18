package com.vorobyoff.weather.data.datasource

import com.vorobyoff.weather.data.datasource.network.AccuWeatherApi
import com.vorobyoff.weather.data.models.CityResponse
import com.vorobyoff.weather.domain.wrapper.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class NetworkDataSource(private val api: AccuWeatherApi) {

    suspend fun topCities(): Result<List<CityResponse>> = api.topCities()

    suspend fun searchCity(query: String): Result<List<CityResponse>> = api.searchCity(query)
}