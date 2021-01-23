package com.vorobyoff.weather.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class AdminAreaResponse(
    @field:Json(name = "ID") val id: String,
    @field:Json(name = "LocalizedName") val name: String
)