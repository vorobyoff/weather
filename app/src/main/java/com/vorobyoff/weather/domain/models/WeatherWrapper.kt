package com.vorobyoff.weather.domain.models

sealed class WeatherWrapper {

    class Weather(
        val conditions: List<CurrentCondition>,
        val fiveDaysForecasts: List<OneDayWeatherForecast>,
        val twelveHoursForecasts: List<OneHourWeatherForecast>
    ) : WeatherWrapper()

    class Mistake(val cause: Throwable) : WeatherWrapper()

    fun isWeather() = this is Weather

    fun asWeather() = this as Weather

    fun asMistake() = this as Mistake
}