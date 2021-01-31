package com.vorobyoff.weather.domain.usecases

import com.vorobyoff.weather.domain.Repository
import com.vorobyoff.weather.domain.models.CurrentCondition
import com.vorobyoff.weather.domain.wrapper.Result

typealias GetCurrentConditionsUseCase = suspend (locationKey: String) -> Result<List<CurrentCondition>>

fun currentConditions(repository: Repository): GetCurrentConditionsUseCase =
    { repository.currentConditions(it) }