package com.vorobyoff.weather.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CurrentConditionResponse(
    @field:Json(name = "UVIndex") val uvIndex: Int?,
    @field:Json(name = "Wind") val wind: WindResponse,
    @field:Json(name = "WeatherIcon") val iconCode: Int?,
    @field:Json(name = "RelativeHumidity") val humidity: Int?,
    @field:Json(name = "WeatherText") val description: String,
    @field:Json(name = "Pressure") val pressure: TypedValuesResponse,
    @field:Json(name = "Temperature") val actuallyTemperature: TypedValuesResponse,
    @field:Json(name = "RealFeelTemperature") val feelTemperature: TypedValuesResponse
) {
    @JsonClass(generateAdapter = true)
    class WindResponse(@field:Json(name = "Speed") val speed: TypedValuesResponse)
}