package com.vorobyoff.weather.data

import com.vorobyoff.weather.data.datasource.NetworkDataSource
import com.vorobyoff.weather.data.models.CurrentConditionResponse
import com.vorobyoff.weather.data.models.DailyWeatherForecastsResponse
import com.vorobyoff.weather.data.models.CityResponse
import com.vorobyoff.weather.data.models.OneHourWeatherForecastResponse
import com.vorobyoff.weather.domain.Repository
import com.vorobyoff.weather.domain.models.CurrentCondition
import com.vorobyoff.weather.domain.models.City
import com.vorobyoff.weather.domain.models.OneDayWeatherForecast
import com.vorobyoff.weather.domain.models.OneHourWeatherForecast
import com.vorobyoff.weather.domain.wrapper.Result
import com.vorobyoff.weather.domain.wrapper.map

typealias CityMapper = (CityResponse) -> City
typealias ConditionsMapper = (List<CurrentConditionResponse>) -> List<CurrentCondition>
typealias HourlyForecastsMapper = (List<OneHourWeatherForecastResponse>) -> List<OneHourWeatherForecast>
typealias DailyForecastsMapper = (DailyWeatherForecastsResponse) -> List<OneDayWeatherForecast>

class RepositoryImpl(
    private val dataSource: NetworkDataSource,
    private inline val mapCityResponse: CityMapper,
    private inline val mapTwelveHoursForecast: HourlyForecastsMapper,
    private inline val mapCurrentConditionResponse: ConditionsMapper,
    private inline val mapDailyForecastsMapper: DailyForecastsMapper
) : Repository {

    override suspend fun geopositionSearch(geolocation: String): Result<City> =
        dataSource.geopositionSearch(geolocation).map(mapCityResponse)

    override suspend fun currentConditions(locationKey: String): Result<List<CurrentCondition>> =
        dataSource.currentConditions(locationKey).map(mapCurrentConditionResponse)

    override suspend fun twelveHoursHourlyForecasts(locationKey: String): Result<List<OneHourWeatherForecast>> =
        dataSource.twelveHoursHourlyForecasts(locationKey).map(mapTwelveHoursForecast)

    override suspend fun fiveDaysDailyForecasts(locationKey: String): Result<List<OneDayWeatherForecast>> =
        dataSource.fiveDaysDailyForecasts(locationKey).map(mapDailyForecastsMapper)
}