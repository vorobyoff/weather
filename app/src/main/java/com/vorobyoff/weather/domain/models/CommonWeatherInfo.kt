package com.vorobyoff.weather.domain.models

import com.vorobyoff.weather.domain.wrapper.Result

class CommonWeatherInfo(
    val city: Result<City>,
    val conditions: Result<List<CurrentCondition>>,
    val twelveForecasts: Result<List<OneHourWeatherForecast>>,
    val dailyForecasts: Result<List<OneDayWeatherForecast>>
)