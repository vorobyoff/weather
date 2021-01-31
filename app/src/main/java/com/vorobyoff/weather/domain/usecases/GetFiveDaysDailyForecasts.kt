package com.vorobyoff.weather.domain.usecases

import com.vorobyoff.weather.domain.Repository
import com.vorobyoff.weather.domain.models.OneDayWeatherForecast
import com.vorobyoff.weather.domain.wrapper.Result

typealias GetFiveDaysDailyForecasts = suspend (locationKey: String) -> Result<List<OneDayWeatherForecast>>

fun fiveDaysDailyForecasts(repository: Repository): GetFiveDaysDailyForecasts =
    { repository.fiveDaysDailyForecasts(it) }