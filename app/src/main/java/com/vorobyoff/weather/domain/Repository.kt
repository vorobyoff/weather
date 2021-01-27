package com.vorobyoff.weather.domain

import com.vorobyoff.weather.domain.model.CurrentCondition
import com.vorobyoff.weather.domain.model.Geoposition
import com.vorobyoff.weather.domain.model.OneHourForecast
import com.vorobyoff.weather.domain.wrapper.Result

interface Repository {

    suspend fun geopositionSearch(geoParam: String): Result<Geoposition>

    suspend fun currentConditions(locationKey: String): Result<List<CurrentCondition>>

    suspend fun twelveHoursForecast(locationKey: String): Result<List<OneHourForecast>>
}