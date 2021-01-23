package com.vorobyoff.weather.domain.usecases

import com.vorobyoff.weather.domain.Repository
import com.vorobyoff.weather.domain.model.City
import com.vorobyoff.weather.domain.wrapper.Result

class GetTopCitiesListUseCase(private val repository: Repository) {
    suspend fun topCities(sort: ((City) -> String)?): Result<List<City>> =
        repository.topCities(sort)
}