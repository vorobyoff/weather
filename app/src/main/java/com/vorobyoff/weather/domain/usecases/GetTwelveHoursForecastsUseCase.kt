package com.vorobyoff.weather.domain.usecases

import android.annotation.SuppressLint
import com.vorobyoff.weather.domain.Repository
import com.vorobyoff.weather.domain.entity.IsoDateTimeFormatter
import com.vorobyoff.weather.domain.entity.IsoTimeFormatter
import com.vorobyoff.weather.domain.models.OneHourWeatherForecast

typealias GetTwelveHoursForecastsUseCase = suspend (locationKey: String) -> List<OneHourWeatherForecast>

@SuppressLint("SimpleDateFormat")
fun getTwelveHoursForecastsUseCase(repository: Repository): GetTwelveHoursForecastsUseCase =
    { locationKey: String ->
        val forecasts: Sequence<OneHourWeatherForecast> =
            repository.getTwelveHoursForecasts(locationKey).asSequence()
        val timeFormatter: IsoDateTimeFormatter = IsoTimeFormatter()

        forecasts.map { forecast ->
            val time = timeFormatter.parse(forecast.date)
            forecast.copy(date = time)
        }.toList()
    }