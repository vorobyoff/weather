package com.vorobyoff.weather.data.datasource

import com.vorobyoff.weather.data.datasource.remote.AccuWeatherApi
import com.vorobyoff.weather.data.models.CurrentConditionResponse
import com.vorobyoff.weather.data.models.DailyWeatherForecastsResponse
import com.vorobyoff.weather.data.models.CityResponse
import com.vorobyoff.weather.data.models.OneHourWeatherForecastResponse
import com.vorobyoff.weather.domain.wrapper.Result

class NetworkDataSource(private val api: AccuWeatherApi) {

    suspend fun geopositionSearch(geolocation: String): Result<CityResponse> =
        api.geopositionSearch(geolocation)

    suspend fun currentConditions(locationKey: String): Result<List<CurrentConditionResponse>> =
        api.currentConditions(locationKey)

    suspend fun fiveDaysDailyForecasts(locationKey: String): Result<DailyWeatherForecastsResponse> =
        api.fiveDaysDailyForecasts(locationKey)

    suspend fun twelveHoursHourlyForecasts(locationKey: String): Result<List<OneHourWeatherForecastResponse>> =
        api.twelveHoursHourlyForecasts(locationKey)
}