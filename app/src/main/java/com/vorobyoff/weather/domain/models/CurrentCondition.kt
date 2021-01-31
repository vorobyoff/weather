package com.vorobyoff.weather.domain.models

class CurrentCondition(
    val wind: Wind,
    val uvIndex: Int,
    val humidity: Int,
    val iconCode: Int,
    val description: String,
    val feelTemperature: TypedValues,
    val actuallyTemperature: TypedValues
) {
    class Wind(val speed: TypedValues)
}