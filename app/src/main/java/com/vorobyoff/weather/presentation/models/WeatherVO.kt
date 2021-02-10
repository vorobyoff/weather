package com.vorobyoff.weather.presentation.models

data class WeatherVO(
    val conditions: List<CurrentConditionVO>,
    val fiveDaysForecasts: List<OneDayWeatherForecastVO>,
    val twelveHoursForecasts: List<OneHourWeatherForecastVO>
)
