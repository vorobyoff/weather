package com.vorobyoff.weather.data.datasource

import com.vorobyoff.weather.data.datasource.remote.AccuWeatherApi
import com.vorobyoff.weather.data.models.CurrentConditionResponse
import com.vorobyoff.weather.data.models.GeopositionResponse
import com.vorobyoff.weather.data.models.OneHourForecastResponse
import com.vorobyoff.weather.domain.wrapper.Result

class NetworkDataSource(private val api: AccuWeatherApi) {

    suspend fun geopositionSearch(geoParam: String): Result<GeopositionResponse> =
        api.geopositionSearch(geoParam)

    suspend fun currentConditions(locationKey: String): Result<List<CurrentConditionResponse>> =
        api.currentConditions(locationKey)

    suspend fun twelveHoursForecast(locationKey: String): Result<List<OneHourForecastResponse>> =
        api.twelveHoursForecasts(locationKey)
}