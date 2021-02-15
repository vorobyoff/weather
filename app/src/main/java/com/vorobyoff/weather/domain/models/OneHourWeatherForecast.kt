package com.vorobyoff.weather.domain.models

import com.vorobyoff.weather.domain.models.TypedValues.TypedValue

class OneHourWeatherForecast(val date: String, val description: String, val temperature: TypedValue) {

    fun copy(
        date: String = this.date,
        description: String = this.description,
        temperature: TypedValue = this.temperature
    ) = OneHourWeatherForecast(date, description, temperature)
}