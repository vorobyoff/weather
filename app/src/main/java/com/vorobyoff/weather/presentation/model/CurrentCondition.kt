package com.vorobyoff.weather.presentation.model

class CurrentCondition(
    val description: String,
    val metricTemp: Double,
    val isDayTime: Boolean,
    val metricUnit: String,
    val dateTime: String,
    val icon: Int
)