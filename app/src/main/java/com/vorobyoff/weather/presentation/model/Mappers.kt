package com.vorobyoff.weather.presentation.model

import com.vorobyoff.weather.domain.model.City

fun City.toCityItem() =
        CityItem(name = name, countyId = countyId, country = country, locationKey = locationKey)