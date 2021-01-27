package com.vorobyoff.weather.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class OneHourForecastResponse(
    @field:Json(name = "DateTime") val date: String,
    @field:Json(name = "IconPhrase") val description: String,
    @field:Json(name = "Temperature") val temperature: TypedValueResponse
)