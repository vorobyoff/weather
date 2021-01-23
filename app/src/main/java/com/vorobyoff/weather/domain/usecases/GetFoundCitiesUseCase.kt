package com.vorobyoff.weather.domain.usecases

import com.vorobyoff.weather.domain.Repository
import com.vorobyoff.weather.domain.model.City
import kotlinx.coroutines.flow.Flow

class GetFoundCitiesUseCase(private val repository: Repository) {
    suspend fun foundCities(query: String): Flow<List<City>> = repository.searchCity(query)
}