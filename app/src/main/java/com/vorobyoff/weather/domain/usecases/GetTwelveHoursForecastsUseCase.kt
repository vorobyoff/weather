package com.vorobyoff.weather.domain.usecases

import android.annotation.SuppressLint
import com.vorobyoff.weather.domain.Repository
import com.vorobyoff.weather.domain.models.OneHourWeatherForecast

typealias GetTwelveHoursForecastsUseCase = suspend (locationKey: String) -> List<OneHourWeatherForecast>

@SuppressLint("SimpleDateFormat")
fun getTwelveHoursForecastsUseCase(repository: Repository): GetTwelveHoursForecastsUseCase =
    { locationKey: String -> repository.getTwelveHoursForecasts(locationKey) }