package com.vorobyoff.weather.domain.model

class CurrentCondition(
    val wind: Wind,
    val uvIndex: Int? = 0,
    val humidity: Int? = 0,
    val iconCode: Int? = 0,
    val description: String,
    val feelTemperature: TypedValues,
    val actuallyTemperature: TypedValues
) {
    class Wind(val speed: TypedValues)

    class TypedValues(val metric: TypedValue, val imperial: TypedValue)
}