package com.vorobyoff.weather.data

import com.vorobyoff.weather.data.models.CityResponse
import com.vorobyoff.weather.domain.model.City

fun CityResponse.toCity() = City(
    locationKey = locationKey,
    country = country.name,
    countyId = country.id,
    area = area.name,
    areaId = area.id,
    name = name
)