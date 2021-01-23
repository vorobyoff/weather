package com.vorobyoff.weather.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CityResponse(
    @field:Json(name = "Key") val locationKey: Int,
    @field:Json(name = "LocalizedName") val name: String,
    @field:Json(name = "Country") val country: CountryResponse,
    @field:Json(name = "AdministrativeArea") val area: AdminAreaResponse
)