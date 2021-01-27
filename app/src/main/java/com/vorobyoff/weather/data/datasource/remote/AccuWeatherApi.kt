package com.vorobyoff.weather.data.datasource.remote

import com.vorobyoff.weather.data.models.CurrentConditionResponse
import com.vorobyoff.weather.data.models.GeopositionResponse
import com.vorobyoff.weather.data.models.OneHourForecastResponse
import com.vorobyoff.weather.domain.wrapper.Result
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AccuWeatherApi {

    @GET("/locations/v1/cities/geoposition/search")
    suspend fun geopositionSearch(@Query("q") geoposition: String): Result<GeopositionResponse>

    @GET("/currentconditions/v1/{locationKey}")
    suspend fun currentConditions(@Path("locationKey") locationKey: String): Result<List<CurrentConditionResponse>>

    @GET("/forecasts/v1/hourly/12hour/{locationKey}")
    suspend fun twelveHoursForecasts(@Path("locationKey") locationKey: String): Result<List<OneHourForecastResponse>>
}