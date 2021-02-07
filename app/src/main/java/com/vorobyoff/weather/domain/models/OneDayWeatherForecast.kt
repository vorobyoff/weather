package com.vorobyoff.weather.domain.models

import com.vorobyoff.weather.domain.models.TypedValues.TypedValue

class OneDayWeatherForecast(val date: String, val description: String, val temperature: Temperature) {
    class Temperature(val minimum: TypedValue, val maximum: TypedValue)
}