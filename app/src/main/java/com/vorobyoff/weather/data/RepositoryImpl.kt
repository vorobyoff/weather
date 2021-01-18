package com.vorobyoff.weather.data

import com.vorobyoff.weather.data.datasource.NetworkDataSource
import com.vorobyoff.weather.data.models.CityResponse
import com.vorobyoff.weather.domain.Repository
import com.vorobyoff.weather.domain.model.City
import com.vorobyoff.weather.domain.wrapper.*

class RepositoryImpl(private val dataSource: NetworkDataSource) : Repository {

    override suspend fun topCities(): Result<List<City>> =
            dataSource.topCities().map { it.map(CityResponse::toCity) }

    override suspend fun searchCity(query: String): Result<List<City>> =
            dataSource.searchCity(query).map { it.map(CityResponse::toCity) }
}