package com.vorobyoff.weather.domain

import com.vorobyoff.weather.domain.models.City
import com.vorobyoff.weather.domain.models.CurrentCondition
import com.vorobyoff.weather.domain.models.OneDayWeatherForecast
import com.vorobyoff.weather.domain.models.OneHourWeatherForecast
import com.vorobyoff.weather.domain.wrapper.Result

interface Repository {

    suspend fun getCityByGeolocation(geolocation: String): Result<City>

    suspend fun getCurrentConditions(locationKey: String): List<CurrentCondition>

    suspend fun getFiveDaysForecasts(locationKey: String): List<OneDayWeatherForecast>

    suspend fun getTwelveHoursForecasts(locationKey: String): List<OneHourWeatherForecast>
}