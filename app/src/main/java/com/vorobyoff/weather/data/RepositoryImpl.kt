package com.vorobyoff.weather.data

import com.vorobyoff.weather.data.datasource.WeatherNetworkDataSource
import com.vorobyoff.weather.data.models.CityResponse
import com.vorobyoff.weather.data.models.CurrentConditionResponse
import com.vorobyoff.weather.data.models.DailyWeatherForecastResponse
import com.vorobyoff.weather.data.models.OneHourWeatherForecastResponse
import com.vorobyoff.weather.domain.Repository
import com.vorobyoff.weather.domain.models.City
import com.vorobyoff.weather.domain.models.CurrentCondition
import com.vorobyoff.weather.domain.models.OneDayWeatherForecast
import com.vorobyoff.weather.domain.models.OneHourWeatherForecast
import com.vorobyoff.weather.domain.wrapper.Result
import com.vorobyoff.weather.domain.wrapper.map

typealias CityMapper = (CityResponse) -> City
typealias ConditionsMapper = (List<CurrentConditionResponse>) -> List<CurrentCondition>
typealias DailyForecastMapper = (DailyWeatherForecastResponse) -> List<OneDayWeatherForecast>
typealias HourlyForecastsMapper = (List<OneHourWeatherForecastResponse>) -> List<OneHourWeatherForecast>

class RepositoryImpl(
    private val dataSource: WeatherNetworkDataSource,
    private inline val hourlyForecastsMapper: HourlyForecastsMapper,
    private inline val currentConditionsMapper: ConditionsMapper,
    private inline val dailyForecastMapper: DailyForecastMapper,
    private inline val cityMapper: CityMapper
) : Repository {

    override suspend fun getCityByGeolocation(geolocation: String): Result<City> =
        dataSource.findCityByGeolocation(geolocation).map(cityMapper)

    override suspend fun getCurrentConditions(locationKey: String): Result<List<CurrentCondition>> =
        dataSource.fetchCurrentConditions(locationKey).map(currentConditionsMapper)

    override suspend fun getTwelveHoursForecasts(locationKey: String): Result<List<OneHourWeatherForecast>> =
        dataSource.fetchTwelveHoursForecasts(locationKey).map(hourlyForecastsMapper)

    override suspend fun getFiveDaysForecasts(locationKey: String): Result<List<OneDayWeatherForecast>> =
        dataSource.fetchFiveDaysForecast(locationKey).map(dailyForecastMapper)
}