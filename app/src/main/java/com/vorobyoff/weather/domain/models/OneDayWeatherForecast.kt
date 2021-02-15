package com.vorobyoff.weather.domain.models

import com.vorobyoff.weather.domain.models.TypedValues.TypedValue

class OneDayWeatherForecast(val date: String, val description: String, val temperature: Temperature) {

    fun copy(
        date: String = this.date,
        description: String = this.description,
        temperature: Temperature = this.temperature
    ) = OneDayWeatherForecast(date, description, temperature)

    class Temperature(val minimum: TypedValue, val maximum: TypedValue)
}