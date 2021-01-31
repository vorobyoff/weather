package com.vorobyoff.weather.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.vorobyoff.weather.data.models.TypedValuesResponse.TypedValueResponse

@JsonClass(generateAdapter = true)
class DailyWeatherForecastsResponse(@field:Json(name = "DailyForecasts") val forecasts: List<OneDayWeatherForecastResponse>) {

    @JsonClass(generateAdapter = true)
    class OneDayWeatherForecastResponse(
        @field:Json(name = "DateTime") val date: String,
        @field:Json(name = "Day") val day: DayResponse,
        @field:Json(name = "Night") val night: NightResponse,
        @field:Json(name = "Temperature") val temperature: TemperatureResponse
    ) {
        @JsonClass(generateAdapter = true)
        class TemperatureResponse(
            @field:Json(name = "Minimum") val minimum: TypedValueResponse,
            @field:Json(name = "Maximum") val maximum: TypedValueResponse
        )

        @JsonClass(generateAdapter = true)
        class DayResponse(@field:Json(name = "IconPhrase") val description: String)

        @JsonClass(generateAdapter = true)
        class NightResponse(@field:Json(name = "IconPhrase") val description: String)
    }
}