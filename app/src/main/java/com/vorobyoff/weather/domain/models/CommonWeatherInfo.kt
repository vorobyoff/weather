package com.vorobyoff.weather.domain.models

import com.vorobyoff.weather.domain.wrapper.Result

class CommonWeatherInfo(
    val geoposition: Result<Geoposition>,
    val conditions: Result<List<CurrentCondition>>,
    val twelveForecasts: Result<List<OneHourWeatherForecast>>,
    val dailyForecasts: Result<List<OneDayWeatherForecast>>
)