package com.vorobyoff.weather.data

import com.vorobyoff.weather.data.models.CityResponse
import com.vorobyoff.weather.domain.model.City

fun CityResponse.toCity() =
        City(name = name, country = country.name, countyId = country.id, locationKey = locationKey)
