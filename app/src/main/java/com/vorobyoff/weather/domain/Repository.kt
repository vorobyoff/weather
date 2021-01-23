package com.vorobyoff.weather.domain

import com.vorobyoff.weather.domain.model.City
import com.vorobyoff.weather.domain.wrapper.Result
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun topCities(sortedBy: ((City) -> String)?): Result<List<City>>

    suspend fun searchCity(query: String): Flow<List<City>>
}