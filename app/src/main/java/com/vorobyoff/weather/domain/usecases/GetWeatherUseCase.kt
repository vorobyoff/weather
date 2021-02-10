package com.vorobyoff.weather.domain.usecases

import com.vorobyoff.weather.domain.models.CurrentCondition
import com.vorobyoff.weather.domain.models.OneDayWeatherForecast
import com.vorobyoff.weather.domain.models.OneHourWeatherForecast
import com.vorobyoff.weather.domain.models.Weather
import com.vorobyoff.weather.domain.wrapper.Result
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope
import kotlin.coroutines.cancellation.CancellationException

typealias GetWeatherUseCase = suspend (locationKey: String) -> Result<Weather>

fun getWeatherUseCase(
    currentConditionsUC: GetCurrentConditionsUseCase,
    fiveDaysForecastsUC: GetFiveDaysForecastsUseCase,
    twelveHoursForecastsUC: GetTwelveHoursForecastsUseCase
): GetWeatherUseCase = { locationKey: String ->
    supervisorScope {
        val defConditions: Deferred<List<CurrentCondition>> = async(IO) { currentConditionsUC(locationKey) }
        val defDailyForecasts: Deferred<List<OneDayWeatherForecast>> =
            async(IO) { fiveDaysForecastsUC(locationKey) }
        val defHourlyForecasts: Deferred<List<OneHourWeatherForecast>> =
            async(IO) { twelveHoursForecastsUC(locationKey) }

        return@supervisorScope try {
            val weather = Weather(
                conditions = defConditions.await(),
                fiveDaysForecasts = defDailyForecasts.await(),
                twelveHoursForecasts = defHourlyForecasts.await()
            )
            Result.Success.Value(weather)
        } catch (cause: Exception) {
            when (cause) {
                is CancellationException -> throw cause
                else -> Result.Failure.Error(cause)
            }
        }
    }
}