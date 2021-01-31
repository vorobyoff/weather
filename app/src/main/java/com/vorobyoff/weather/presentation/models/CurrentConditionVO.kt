package com.vorobyoff.weather.presentation.models

data class CurrentConditionVO(
    val wind: WindVO,
    val uvIndex: Int,
    val humidity: Int,
    val iconCode: Int,
    val description: String,
    val feelTemperature: TypedValuesVO,
    val actuallyTemperature: TypedValuesVO
) {
    data class WindVO(val speed: TypedValuesVO)
}