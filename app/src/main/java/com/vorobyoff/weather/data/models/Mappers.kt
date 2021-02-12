package com.vorobyoff.weather.data.models

import com.vorobyoff.weather.data.models.CurrentConditionResponse.WindResponse
import com.vorobyoff.weather.data.models.DailyWeatherForecastResponse.OneDayWeatherForecastResponse
import com.vorobyoff.weather.data.models.TypedValuesResponse.TypedValueResponse
import com.vorobyoff.weather.domain.models.CurrentCondition
import com.vorobyoff.weather.domain.models.CurrentCondition.Wind
import com.vorobyoff.weather.domain.models.OneDayWeatherForecast
import com.vorobyoff.weather.domain.models.OneDayWeatherForecast.Temperature
import com.vorobyoff.weather.domain.models.OneHourWeatherForecast
import com.vorobyoff.weather.domain.models.TypedValues
import com.vorobyoff.weather.domain.models.TypedValues.TypedValue
import kotlin.math.roundToInt

fun OneDayWeatherForecastResponse.toDomain() = OneDayWeatherForecast(
    date = this.date,
    description = "${this.day.description}\n${this.night.description}",
    temperature = Temperature(
        minimum = this.temperature.minimum.toDomain(),
        maximum = this.temperature.maximum.toDomain()
    )
)

fun OneHourWeatherForecastResponse.toDomain() = OneHourWeatherForecast(
    temperature = this.temperature.toDomain(), description = this.description, date = this.date
)

fun CurrentConditionResponse.toDomain() = CurrentCondition(
    wind = this.wind.toDomain(),
    uvIndex = this.uvIndex ?: 0,
    humidity = this.humidity ?: 0,
    iconCode = this.iconCode ?: 0,
    description = this.description,
    pressure = this.pressure.toDomain(),
    feelTemperature = this.feelTemperature.toDomain(),
    actuallyTemperature = this.actuallyTemperature.toDomain()
)

fun WindResponse.toDomain() = Wind(this.speed.toDomain())

fun TypedValuesResponse.toDomain() =
    TypedValues(metric = this.metric.toDomain(), imperial = this.imperial.toDomain())

fun TypedValueResponse.toDomain() = TypedValue(unit = this.unit, value = this.value?.roundToInt() ?: 0)