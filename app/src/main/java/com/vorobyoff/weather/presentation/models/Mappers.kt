package com.vorobyoff.weather.presentation.models

import com.vorobyoff.weather.domain.models.*
import com.vorobyoff.weather.domain.models.CurrentCondition.Wind
import com.vorobyoff.weather.domain.models.OneDayWeatherForecast.Temperature
import com.vorobyoff.weather.domain.models.TypedValues.TypedValue
import com.vorobyoff.weather.domain.models.WeatherWrapper.Mistake
import com.vorobyoff.weather.domain.models.WeatherWrapper.Weather
import com.vorobyoff.weather.presentation.models.CurrentConditionVO.WindVO
import com.vorobyoff.weather.presentation.models.OneDayWeatherForecastVO.TemperatureVO
import com.vorobyoff.weather.presentation.models.TypedValuesVO.TypedValueVO
import com.vorobyoff.weather.presentation.models.WeatherStates.SuccessfulWeather
import com.vorobyoff.weather.presentation.models.WeatherStates.WrongWeather

fun Weather.toVO() = SuccessfulWeather(
    twelveForecasts = this.twelveHoursForecasts.map { it.toVO() },
    dailyForecasts = this.fiveDaysForecasts.map { it.toVO() },
    conditions = this.conditions.map { it.toVO() }
)

fun Mistake.toVO() = WrongWeather(cause = this.cause)

fun CurrentCondition.toVO() = CurrentConditionVO(
    uvIndex = this.uvIndex,
    wind = this.wind.toVO(),
    humidity = this.humidity,
    iconCode = this.iconCode,
    description = this.description,
    feelTemperature = this.feelTemperature.toVO(),
    actuallyTemperature = this.actuallyTemperature.toVO()
)

fun OneDayWeatherForecast.toVO() = OneDayWeatherForecastVO(
    temperature = this.temperature.toVO(), description = this.description, date = this.date
)

fun OneHourWeatherForecast.toVO() = OneHourWeatherForecastVO(
    temperature = this.temperature.toVO(), description = this.description, date = this.date
)

fun Temperature.toVO() = TemperatureVO(minimum = this.minimum.toVO(), maximum = this.maximum.toVO())

fun City.toVO() = CityVO(locationKey = this.locationKey, cityName = this.cityName)

fun Wind.toVO() = WindVO(speed = this.speed.toVO())

fun TypedValues.toVO() = TypedValuesVO(metric = this.metric.toVO(), imperial = this.imperial.toVO())

fun TypedValue.toVO() = TypedValueVO(unit = this.unit, value = this.value)