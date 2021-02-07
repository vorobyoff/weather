package com.vorobyoff.weather.domain.usecases

import com.vorobyoff.weather.domain.models.CurrentCondition
import com.vorobyoff.weather.domain.models.OneDayWeatherForecast
import com.vorobyoff.weather.domain.models.OneHourWeatherForecast
import com.vorobyoff.weather.domain.models.WeatherWrapper
import com.vorobyoff.weather.domain.models.WeatherWrapper.*
import com.vorobyoff.weather.domain.wrapper.Result
import com.vorobyoff.weather.domain.wrapper.asFailure
import com.vorobyoff.weather.domain.wrapper.asSuccess
import com.vorobyoff.weather.domain.wrapper.isFailure
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlin.contracts.ExperimentalContracts

typealias GetWeatherUseCase = suspend (locationKey: String) -> WeatherWrapper

@ExperimentalContracts
fun getWeatherUseCase(
    getCurrentConditionsUseCase: GetCurrentConditionsUseCase,
    getFiveDaysForecastsUseCase: GetFiveDaysForecastsUseCase,
    getTwelveHoursForecastsUseCase: GetTwelveHoursForecastsUseCase
): GetWeatherUseCase = { locationKey: String ->
    coroutineScope {
        val defConditions: Deferred<Result<List<CurrentCondition>>> =
            async(IO) { getCurrentConditionsUseCase(locationKey) }
        val defHourlyForecasts: Deferred<Result<List<OneHourWeatherForecast>>> =
            async(IO) { getTwelveHoursForecastsUseCase(locationKey) }
        val defDailyForecasts: Deferred<Result<List<OneDayWeatherForecast>>> =
            async(IO) { getFiveDaysForecastsUseCase(locationKey) }

        val results: List<Result<Any>> = awaitAll(defConditions, defHourlyForecasts, defDailyForecasts)

        @Suppress("unchecked_cast")
        return@coroutineScope try {
            Weather(
                conditions = results[0].asSuccess().value as List<CurrentCondition>,
                fiveDaysForecasts = results[1].asSuccess().value as List<OneDayWeatherForecast>,
                twelveHoursForecasts = results[2].asSuccess().value as List<OneHourWeatherForecast>
            )
        } catch (castException: ClassCastException) {
            val cause: Throwable = when {
                results[0].isFailure() -> results[0].asFailure().error!!
                results[1].isFailure() -> results[1].asFailure().error!!
                results[2].isFailure() -> results[2].asFailure().error!!
                else -> Throwable("No one doesn't has an exception")
            }
            Mistake(cause)
        }
    }
}