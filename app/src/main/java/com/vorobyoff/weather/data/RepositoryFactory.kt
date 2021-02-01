package com.vorobyoff.weather.data

import com.vorobyoff.weather.data.datasource.NetworkDataSource
import com.vorobyoff.weather.data.datasource.remote.NetworkFactory.weatherApi
import com.vorobyoff.weather.data.models.CurrentConditionResponse
import com.vorobyoff.weather.data.models.CurrentConditionResponse.WindResponse
import com.vorobyoff.weather.data.models.OneHourWeatherForecastResponse
import com.vorobyoff.weather.data.models.TypedValuesResponse
import com.vorobyoff.weather.data.models.TypedValuesResponse.TypedValueResponse
import com.vorobyoff.weather.domain.models.*
import com.vorobyoff.weather.domain.models.CurrentCondition.Wind
import com.vorobyoff.weather.domain.models.OneDayWeatherForecast.Temperature
import com.vorobyoff.weather.domain.models.TypedValues.TypedValue
import kotlin.math.roundToInt

object RepositoryFactory {

    val repository
        get() = RepositoryImpl(
            dataSource = NetworkDataSource(weatherApi),
            mapCityResponse = cityMapper(),
            mapTwelveHoursForecast = forecastsMapper(),
            mapDailyForecastsMapper = dailyForecastsMapper(),
            mapCurrentConditionResponse = currentConditionsMapper()
        )

    private fun forecastsMapper(): HourlyForecastsMapper =
        { response -> response.map { it.toDomain() } }

    private fun cityMapper(): CityMapper =
        { City(locationKey = it.locationKey, name = it.cityName) }

    private fun dailyForecastsMapper(): DailyForecastsMapper = { response ->
        response.forecasts.map {
            OneDayWeatherForecast(
                date = it.date,
                description = "${it.day.description}\n${it.night.description}",
                temperature = Temperature(
                    minimum = it.temperature.minimum.toDomain(),
                    maximum = it.temperature.maximum.toDomain()
                )
            )
        }
    }

    private fun currentConditionsMapper(): ConditionsMapper =
        { responses -> responses.map { it.toDomain() } }

    private fun OneHourWeatherForecastResponse.toDomain() = OneHourWeatherForecast(
        temperature = temperature.toDomain(),
        description = description,
        date = date
    )

    private fun CurrentConditionResponse.toDomain() = CurrentCondition(
        uvIndex = uvIndex ?: 0,
        humidity = humidity ?: 0,
        iconCode = iconCode ?: 0,
        description = description,
        wind = wind.toDomain(),
        feelTemperature = feelTemperature.toDomain(),
        actuallyTemperature = actuallyTemperature.toDomain()
    )

    private fun WindResponse.toDomain() = Wind(speed.toDomain())

    private fun TypedValuesResponse.toDomain() =
        TypedValues(metric = metric.toDomain(), imperial = imperial.toDomain())

    private fun TypedValueResponse.toDomain() =
        TypedValue(unit = unit, value = value?.roundToInt() ?: 0)
}