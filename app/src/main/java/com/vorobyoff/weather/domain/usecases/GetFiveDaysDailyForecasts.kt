package com.vorobyoff.weather.domain.usecases

import com.vorobyoff.weather.domain.Repository
import com.vorobyoff.weather.domain.models.OneDayWeatherForecast
import com.vorobyoff.weather.domain.wrapper.Result

typealias GetFiveDaysForecastsUseCase = suspend (locationKey: String) -> Result<List<OneDayWeatherForecast>>

fun getFiveDaysForecastsUseCase(repository: Repository): GetFiveDaysForecastsUseCase =
    { locationKey: String -> repository.getFiveDaysForecasts(locationKey) }