package com.vorobyoff.weather.domain.model

class OneHourForecast(
    val date: String,
    val description: String,
    val temperature: SingleTypedTemperature
) {
    class SingleTypedTemperature(val value: Int, val unit: String)
}