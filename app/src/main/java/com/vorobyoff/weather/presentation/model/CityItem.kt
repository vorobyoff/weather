package com.vorobyoff.weather.presentation.model

data class CityItem(
    val name: String,
    val area: String,
    val areaId: String,
    val country: String,
    val countyId: String,
    val locationKey: Int,
)