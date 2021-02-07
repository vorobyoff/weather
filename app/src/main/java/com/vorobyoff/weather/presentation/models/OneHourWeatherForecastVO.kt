package com.vorobyoff.weather.presentation.models

import com.vorobyoff.weather.presentation.models.TypedValuesVO.TypedValueVO

data class OneHourWeatherForecastVO(val date: String, val description: String, val temperature: TypedValueVO)