package com.vorobyoff.weather.presentation.models

import com.vorobyoff.weather.domain.models.*
import com.vorobyoff.weather.domain.models.CurrentCondition.Wind
import com.vorobyoff.weather.domain.models.TypedValues.TypedValue
import com.vorobyoff.weather.domain.wrapper.map
import com.vorobyoff.weather.presentation.models.CurrentConditionVO.WindVO
import com.vorobyoff.weather.presentation.models.TypedValuesVO.TypedValueVO
import com.vorobyoff.weather.presentation.models.GeopositionState.GeopositionVO

fun CommonWeatherInfo.mapToVO() = CommonWeatherVO(
    geoposition = geoposition.map { it.mapToVO() },
    conditions = conditions.map { it.map { condition -> condition.mapToVO() } },
    twelveForecasts = twelveForecasts.map { it.map { forecast -> forecast.mapToVO() } }
)

fun Geoposition.mapToVO() = GeopositionVO(locationKey = locationKey, cityName = cityName)

fun OneHourWeatherForecast.mapToVO() = OneHourForecastVO(
    temperature = temperature.mapToVO(),
    description = description,
    date = date
)

fun CurrentCondition.mapToVO() = CurrentConditionVO(
    uvIndex = uvIndex,
    humidity = humidity,
    iconCode = iconCode,
    wind = wind.mapToVO(),
    description = description,
    feelTemperature = feelTemperature.mapToVO(),
    actuallyTemperature = actuallyTemperature.mapToVO()
)

fun Wind.mapToVO() = WindVO(speed = speed.mapToVO())

fun TypedValues.mapToVO() = TypedValuesVO(metric = metric.mapToVO(), imperial = imperial.mapToVO())

fun TypedValue.mapToVO() = TypedValueVO(unit = unit, value = value)