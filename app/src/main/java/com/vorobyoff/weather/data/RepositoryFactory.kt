package com.vorobyoff.weather.data

import com.vorobyoff.weather.data.datasource.WeatherNetworkDataSource
import com.vorobyoff.weather.data.datasource.remote.WeatherNetworkFactory.weatherApi
import com.vorobyoff.weather.data.models.*
import com.vorobyoff.weather.data.models.DailyWeatherForecastResponse.OneDayWeatherForecastResponse
import com.vorobyoff.weather.domain.Repository
import com.vorobyoff.weather.domain.models.City

object RepositoryFactory {

    val repository: Repository
        get() = RepositoryImpl(
            dataSource = WeatherNetworkDataSource(weatherApi),
            currentConditionsMapper = createCurrentConditionsMapper(),
            hourlyForecastsMapper = createHourlyForecastsMapper(),
            dailyForecastMapper = createDailyForecastMapper(),
            cityMapper = createCityMapper()
        )

    private fun createCurrentConditionsMapper(): ConditionsMapper =
        { conditions: List<CurrentConditionResponse> ->
            conditions.map { condition: CurrentConditionResponse -> condition.toDomain() }
        }

    private fun createHourlyForecastsMapper(): HourlyForecastsMapper =
        { hourlyForecasts: List<OneHourWeatherForecastResponse> ->
            hourlyForecasts.map { forecast: OneHourWeatherForecastResponse -> forecast.toDomain() }
        }

    private fun createDailyForecastMapper(): DailyForecastMapper =
        { dailyForecast: DailyWeatherForecastResponse ->
            dailyForecast.dailyForecasts.map { oneDayForecast: OneDayWeatherForecastResponse ->
                oneDayForecast.toDomain()
            }
        }

    private fun createCityMapper(): CityMapper = { city: CityResponse ->
        City(locationKey = city.locationKey, cityName = city.cityName)
    }
}