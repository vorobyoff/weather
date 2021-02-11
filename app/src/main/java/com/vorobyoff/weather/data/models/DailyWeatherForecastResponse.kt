package com.vorobyoff.weather.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.vorobyoff.weather.data.models.TypedValuesResponse.TypedValueResponse

@JsonClass(generateAdapter = true)
class DailyWeatherForecastResponse(@field:Json(name = "DailyForecasts") val dailyForecasts: List<OneDayWeatherForecastResponse>) {

    @JsonClass(generateAdapter = true)
    class OneDayWeatherForecastResponse(
        @field:Json(name = "Date") val date: String,
        @field:Json(name = "Day") val day: TimeOfDayResponse,
        @field:Json(name = "Night") val night: TimeOfDayResponse,
        @field:Json(name = "Temperature") val temperature: TemperatureResponse
    ) {
        @JsonClass(generateAdapter = true)
        class TemperatureResponse(
            @field:Json(name = "Minimum") val minimum: TypedValueResponse,
            @field:Json(name = "Maximum") val maximum: TypedValueResponse
        )

        @JsonClass(generateAdapter = true)
        class TimeOfDayResponse(@field:Json(name = "IconPhrase") val description: String)
    }
}