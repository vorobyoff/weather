package com.vorobyoff.weather.data

import com.vorobyoff.weather.data.datasource.NetworkDataSource
import com.vorobyoff.weather.data.models.CurrentConditionResponse
import com.vorobyoff.weather.data.models.DailyWeatherForecastsResponse
import com.vorobyoff.weather.data.models.GeopositionResponse
import com.vorobyoff.weather.data.models.OneHourWeatherForecastResponse
import com.vorobyoff.weather.domain.Repository
import com.vorobyoff.weather.domain.models.CurrentCondition
import com.vorobyoff.weather.domain.models.Geoposition
import com.vorobyoff.weather.domain.models.OneDayWeatherForecast
import com.vorobyoff.weather.domain.models.OneHourWeatherForecast
import com.vorobyoff.weather.domain.wrapper.Result
import com.vorobyoff.weather.domain.wrapper.map

typealias GeopositionMapper = (GeopositionResponse) -> Geoposition
typealias ConditionsMapper = (List<CurrentConditionResponse>) -> List<CurrentCondition>
typealias HourlyForecastsMapper = (List<OneHourWeatherForecastResponse>) -> List<OneHourWeatherForecast>
typealias DailyForecastsMapper = (DailyWeatherForecastsResponse) -> List<OneDayWeatherForecast>

class RepositoryImpl(
    private val dataSource: NetworkDataSource,
    private inline val mapGeopositionResponse: GeopositionMapper,
    private inline val mapTwelveHoursForecast: HourlyForecastsMapper,
    private inline val mapCurrentConditionResponse: ConditionsMapper,
    private inline val mapDailyForecastsMapper: DailyForecastsMapper
) : Repository {

    override suspend fun geopositionSearch(geolocation: String): Result<Geoposition> =
        dataSource.geopositionSearch(geolocation).map(mapGeopositionResponse)

    override suspend fun currentConditions(locationKey: String): Result<List<CurrentCondition>> =
        dataSource.currentConditions(locationKey).map(mapCurrentConditionResponse)

    override suspend fun twelveHoursHourlyForecasts(locationKey: String): Result<List<OneHourWeatherForecast>> =
        dataSource.twelveHoursHourlyForecasts(locationKey).map(mapTwelveHoursForecast)

    override suspend fun fiveDaysDailyForecasts(locationKey: String): Result<List<OneDayWeatherForecast>> =
        dataSource.fiveDaysDailyForecasts(locationKey).map(mapDailyForecastsMapper)
}