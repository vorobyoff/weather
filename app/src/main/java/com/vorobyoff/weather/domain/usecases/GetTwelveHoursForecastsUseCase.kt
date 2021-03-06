package com.vorobyoff.weather.domain.usecases

import com.vorobyoff.weather.domain.Repository
import com.vorobyoff.weather.domain.models.OneHourWeatherForecast
import com.vorobyoff.weather.domain.wrapper.Result

typealias GetTwelveHoursForecastsUseCase = suspend (locationKey: String) -> Result<List<OneHourWeatherForecast>>

fun getTwelveHoursForecastsUseCase(repository: Repository): GetTwelveHoursForecastsUseCase =
    { locationKey: String -> repository.getTwelveHoursForecasts(locationKey) }