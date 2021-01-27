package com.vorobyoff.weather.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class GeopositionResponse(
    @field:Json(name = "Key") val locationKey: String,
    @field:Json(name = "LocalizedName") val cityName: String
)