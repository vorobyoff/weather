package com.vorobyoff.weather.data.datasource

import com.vorobyoff.weather.data.datasource.remote.AccuWeatherApi
import com.vorobyoff.weather.data.models.CityResponse
import com.vorobyoff.weather.domain.wrapper.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NetworkDataSource(private val api: AccuWeatherApi) {

    suspend fun topCities(): Result<List<CityResponse>> = api.topCities()

    suspend fun searchCity(query: String): Flow<List<CityResponse>> = flow { emit(api.searchCity(query)) }
}