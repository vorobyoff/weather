package com.vorobyoff.weather.presentation.models

import com.vorobyoff.weather.presentation.models.TypedValuesVO.TypedValueVO

data class OneDayWeatherForecastVO(
    val date: String,
    val description: String,
    val temperature: TemperatureVO
) {
    data class TemperatureVO(val minimum: TypedValueVO, val maximum: TypedValueVO)
}