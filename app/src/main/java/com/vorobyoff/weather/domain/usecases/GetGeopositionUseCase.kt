package com.vorobyoff.weather.domain.usecases

import com.vorobyoff.weather.domain.Repository
import com.vorobyoff.weather.domain.models.City
import com.vorobyoff.weather.domain.wrapper.Result

typealias GetCityUseCase = suspend (geolocation: String) -> Result<City>

fun geopositionSearch(repository: Repository): GetCityUseCase =
    { repository.geopositionSearch(it) }