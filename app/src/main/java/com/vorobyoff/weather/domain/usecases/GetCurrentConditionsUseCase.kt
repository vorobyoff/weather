package com.vorobyoff.weather.domain.usecases

import com.vorobyoff.weather.domain.Repository
import com.vorobyoff.weather.domain.models.CurrentCondition

typealias GetCurrentConditionsUseCase = suspend (locationKey: String) -> List<CurrentCondition>

fun getCurrentConditionsUseCase(repository: Repository): GetCurrentConditionsUseCase =
    { locationKey: String -> repository.getCurrentConditions(locationKey) }