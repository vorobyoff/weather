package com.vorobyoff.weather.data

import com.vorobyoff.weather.data.datasource.NetworkDataSource
import com.vorobyoff.weather.data.models.CityResponse
import com.vorobyoff.weather.domain.Repository
import com.vorobyoff.weather.domain.model.City
import com.vorobyoff.weather.domain.wrapper.Result
import com.vorobyoff.weather.domain.wrapper.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepositoryImpl(private val dataSource: NetworkDataSource) : Repository {

    override suspend fun topCities(sortedBy: ((City) -> String)?): Result<List<City>> =
        dataSource.topCities().map { responses: List<CityResponse> ->
            val cities = responses.asSequence().map(CityResponse::toCity)
            if (sortedBy == null) cities.toList() else cities.sortedBy(sortedBy).toList()
        }

    override suspend fun searchCity(query: String): Flow<List<City>> =
        dataSource.searchCity(query).map { it.map(CityResponse::toCity) }
}