package com.vorobyoff.weather.presentation.model

import com.vorobyoff.weather.domain.model.City

fun City.toCityItem() = CityItem(
    locationKey = locationKey,
    countyId = countyId,
    country = country,
    areaId = areaId,
    area = area,
    name = name
)