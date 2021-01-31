package com.vorobyoff.weather.domain.models

import com.vorobyoff.weather.domain.models.TypedValues.TypedValue

class OneHourWeatherForecast(val date: String, val description: String, val temperature: TypedValue)