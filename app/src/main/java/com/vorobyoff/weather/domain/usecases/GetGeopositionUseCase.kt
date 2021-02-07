package com.vorobyoff.weather.domain.usecases

import com.vorobyoff.weather.domain.Repository
import com.vorobyoff.weather.domain.models.City
import com.vorobyoff.weather.domain.wrapper.Result

typealias GetCityUseCase = suspend (latitude: Double, longitude: Double) -> Result<City>

fun getCityByGeopositionUseCase(repository: Repository): GetCityUseCase =
    { lat: Double, lon: Double -> repository.getCityByGeolocation("${lat},${lon}") }