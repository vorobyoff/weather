package com.vorobyoff.weather.data

import com.vorobyoff.weather.data.datasource.NetworkDataSource
import com.vorobyoff.weather.data.models.CurrentConditionResponse
import com.vorobyoff.weather.data.models.GeopositionResponse
import com.vorobyoff.weather.data.models.OneHourForecastResponse
import com.vorobyoff.weather.domain.Repository
import com.vorobyoff.weather.domain.model.CurrentCondition
import com.vorobyoff.weather.domain.model.Geoposition
import com.vorobyoff.weather.domain.model.OneHourForecast
import com.vorobyoff.weather.domain.wrapper.Result
import com.vorobyoff.weather.domain.wrapper.map

class RepositoryImpl(
    private val dataSource: NetworkDataSource,
    private inline val mapGeopositionResponse: (GeopositionResponse) -> Geoposition,
    private inline val mapTwelveHoursForecast: (List<OneHourForecastResponse>) -> List<OneHourForecast>,
    private inline val mapCurrentConditionResponse: (List<CurrentConditionResponse>) -> List<CurrentCondition>,
) : Repository {

    override suspend fun geopositionSearch(geoParam: String): Result<Geoposition> =
        dataSource.geopositionSearch(geoParam).map { mapGeopositionResponse(it) }

    override suspend fun currentConditions(locationKey: String): Result<List<CurrentCondition>> =
        dataSource.currentConditions(locationKey).map { mapCurrentConditionResponse(it) }

    override suspend fun twelveHoursForecast(locationKey: String): Result<List<OneHourForecast>> =
        dataSource.twelveHoursForecast(locationKey).map { mapTwelveHoursForecast(it) }
}