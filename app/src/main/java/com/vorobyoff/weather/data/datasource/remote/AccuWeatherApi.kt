package com.vorobyoff.weather.data.datasource.remote

import com.vorobyoff.weather.data.models.CityResponse
import com.vorobyoff.weather.data.models.CurrentConditionResponse
import com.vorobyoff.weather.data.models.DailyWeatherForecastResponse
import com.vorobyoff.weather.data.models.OneHourWeatherForecastResponse
import com.vorobyoff.weather.domain.wrapper.Result
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AccuWeatherApi {

    @GET("/locations/v1/cities/geoposition/search")
    suspend fun findCityByGeolocation(@Query("q") geolocation: String): Result<CityResponse>

    @GET("/currentconditions/v1/{locationKey}?details=true")
    suspend fun receiveCurrentConditions(@Path("locationKey") locationKey: String): List<CurrentConditionResponse>

    @GET("/forecasts/v1/daily/5day/{locationKey}?details=true")
    suspend fun receiveFiveDaysForecast(@Path("locationKey") locationKey: String): DailyWeatherForecastResponse

    @GET("/forecasts/v1/hourly/12hour/{locationKey}")
    suspend fun receiveTwelveHoursForecasts(@Path("locationKey") locationKey: String): List<OneHourWeatherForecastResponse>
}