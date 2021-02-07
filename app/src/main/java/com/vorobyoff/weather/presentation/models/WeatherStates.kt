package com.vorobyoff.weather.presentation.models

sealed class WeatherStates {
    object LoadingWeather : WeatherStates()

    data class SuccessfulWeather(
        val conditions: List<CurrentConditionVO>,
        val twelveForecasts: List<OneHourWeatherForecastVO>,
        val dailyForecasts: List<OneDayWeatherForecastVO>
    ) : WeatherStates()

    class WrongWeather(val cause: Throwable) : WeatherStates()
}