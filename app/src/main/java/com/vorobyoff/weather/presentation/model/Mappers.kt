package com.vorobyoff.weather.presentation.model

fun City.toCityItem() = CityItem(
    locationKey = locationKey,
    countyId = countyId,
    country = country,
    areaId = areaId,
    area = area,
    name = name
)

fun Condition.toCurrentCondition() = CurrentCondition(
    description = description,
    metricTemp = metricTemp,
    metricUnit = metricUnit,
    isDayTime = isDayTime,
    dateTime = dateTime,
    icon = icon
)