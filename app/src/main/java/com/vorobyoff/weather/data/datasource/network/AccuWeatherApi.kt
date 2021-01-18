package com.vorobyoff.weather.data.datasource.network

import com.vorobyoff.weather.data.models.CityResponse
import com.vorobyoff.weather.domain.wrapper.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface AccuWeatherApi {
    @GET("/locations/v1/topcities/100")
    suspend fun topCities(): Result<List<CityResponse>>

    @GET("/locations/v1/cities/autocomplete")
    suspend fun searchCity(@Query("q") query: String): Result<List<CityResponse>>
}