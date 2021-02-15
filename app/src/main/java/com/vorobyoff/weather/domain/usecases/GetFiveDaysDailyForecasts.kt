package com.vorobyoff.weather.domain.usecases

import com.vorobyoff.weather.domain.Repository
import com.vorobyoff.weather.domain.entity.IsoDateFormatter
import com.vorobyoff.weather.domain.entity.IsoDateTimeFormatter
import com.vorobyoff.weather.domain.models.OneDayWeatherForecast

typealias GetFiveDaysForecastsUseCase = suspend (locationKey: String) -> List<OneDayWeatherForecast>

fun getFiveDaysForecastsUseCase(repository: Repository): GetFiveDaysForecastsUseCase =
    { locationKey: String ->
        val forecasts: Sequence<OneDayWeatherForecast> = repository.getFiveDaysForecasts(locationKey).asSequence()
        val dateFormatter: IsoDateTimeFormatter = IsoDateFormatter()

        forecasts.map { forecast ->
            val formattedDate: String = dateFormatter.parse(forecast.date)
            forecast.copy(date = formattedDate)
        }.toList()
    }