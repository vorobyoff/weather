package com.vorobyoff.weather.data.datasource.remote

import com.vorobyoff.weather.data.models.CurrentConditionResponse
import com.vorobyoff.weather.data.models.DailyWeatherForecastsResponse
import com.vorobyoff.weather.data.models.GeopositionResponse
import com.vorobyoff.weather.data.models.OneHourWeatherForecastResponse
import com.vorobyoff.weather.domain.wrapper.Result
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AccuWeatherApi {

    @GET("/locations/v1/cities/geoposition/search")
    suspend fun geopositionSearch(@Query("q") geolocation: String): Result<GeopositionResponse>

    @GET("/currentconditions/v1/{locationKey}")
    suspend fun currentConditions(@Path("locationKey") locationKey: String): Result<List<CurrentConditionResponse>>

    @GET("/forecasts/v1/daily/5day/{locationKey}")
    suspend fun fiveDaysDailyForecasts(@Path("locationKey") locationKey: String): Result<DailyWeatherForecastsResponse>

    @GET("/forecasts/v1/hourly/12hour/{locationKey}")
    suspend fun twelveHoursHourlyForecasts(@Path("locationKey") locationKey: String): Result<List<OneHourWeatherForecastResponse>>
}