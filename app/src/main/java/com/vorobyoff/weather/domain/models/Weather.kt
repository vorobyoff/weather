package com.vorobyoff.weather.domain.models

class Weather(
    val conditions: List<CurrentCondition>,
    val fiveDaysForecasts: List<OneDayWeatherForecast>,
    val twelveHoursForecasts: List<OneHourWeatherForecast>
)