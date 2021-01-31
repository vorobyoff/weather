package com.vorobyoff.weather.domain.usecases

import com.vorobyoff.weather.domain.Repository
import com.vorobyoff.weather.domain.models.Geoposition
import com.vorobyoff.weather.domain.wrapper.Result

typealias GetGeopositionUseCase = suspend (geolocation: String) -> Result<Geoposition>

fun geopositionSearch(repository: Repository): GetGeopositionUseCase =
    { repository.geopositionSearch(it) }