package com.vorobyoff.weather.data.datasource

import com.vorobyoff.weather.data.datasource.remote.AccuWeatherApi
import com.vorobyoff.weather.data.models.CityResponse
import com.vorobyoff.weather.data.models.CurrentConditionResponse
import com.vorobyoff.weather.data.models.DailyWeatherForecastResponse
import com.vorobyoff.weather.data.models.OneHourWeatherForecastResponse
import com.vorobyoff.weather.domain.wrapper.Result

class WeatherNetworkDataSource(private val weatherApi: AccuWeatherApi) {

    suspend fun receiveCityByGeolocation(geolocation: String): Result<CityResponse> =
        weatherApi.findCityByGeolocation(geolocation)

    suspend fun receiveCurrentConditions(locationKey: String): Result<List<CurrentConditionResponse>> =
        weatherApi.receiveCurrentConditions(locationKey)

    suspend fun receiveFiveDaysForecast(locationKey: String): Result<DailyWeatherForecastResponse> =
        weatherApi.receiveFiveDaysForecast(locationKey)

    suspend fun receiveTwelveHoursForecasts(locationKey: String): Result<List<OneHourWeatherForecastResponse>> =
        weatherApi.receiveTwelveHoursForecasts(locationKey)
}