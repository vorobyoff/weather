package com.vorobyoff.weather.presentation.models

import com.vorobyoff.weather.domain.wrapper.Result

class CommonWeatherVO(
    val geoposition: Result<GeopositionVO>,
    val conditions: Result<List<CurrentConditionVO>>,
    val twelveForecasts: Result<List<OneHourForecastVO>>
)