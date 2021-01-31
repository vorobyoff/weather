package com.vorobyoff.weather.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class TypedValuesResponse(
    @field:Json(name = "Metric") val metric: TypedValueResponse,
    @field:Json(name = "Imperial") val imperial: TypedValueResponse
) {
    @JsonClass(generateAdapter = true)
    class TypedValueResponse(
        @field:Json(name = "Unit") val unit: String,
        @field:Json(name = "Value") val value: Double?
    )
}