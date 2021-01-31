package com.vorobyoff.weather.domain

import com.vorobyoff.weather.domain.models.CurrentCondition
import com.vorobyoff.weather.domain.models.Geoposition
import com.vorobyoff.weather.domain.models.OneDayWeatherForecast
import com.vorobyoff.weather.domain.models.OneHourWeatherForecast
import com.vorobyoff.weather.domain.wrapper.Result

interface Repository {

    suspend fun geopositionSearch(geolocation: String): Result<Geoposition>

    suspend fun currentConditions(locationKey: String): Result<List<CurrentCondition>>

    suspend fun twelveHoursHourlyForecasts(locationKey: String): Result<List<OneHourWeatherForecast>>

    suspend fun fiveDaysDailyForecasts(locationKey: String): Result<List<OneDayWeatherForecast>>
}