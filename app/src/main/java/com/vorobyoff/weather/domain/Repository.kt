package com.vorobyoff.weather.domain

import com.vorobyoff.weather.domain.model.City
import com.vorobyoff.weather.domain.wrapper.Result

interface Repository {

    suspend fun topCities(): Result<List<City>>

    suspend fun searchCity(query: String): Result<List<City>>
}