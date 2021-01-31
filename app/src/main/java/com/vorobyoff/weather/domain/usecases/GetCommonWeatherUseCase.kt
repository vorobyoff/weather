package com.vorobyoff.weather.domain.usecases

import com.vorobyoff.weather.domain.Repository
import com.vorobyoff.weather.domain.models.*
import com.vorobyoff.weather.domain.wrapper.Result
import com.vorobyoff.weather.domain.wrapper.Result.Failure
import com.vorobyoff.weather.domain.wrapper.Result.Success
import com.vorobyoff.weather.domain.wrapper.asFailure
import com.vorobyoff.weather.domain.wrapper.asSuccess
import com.vorobyoff.weather.domain.wrapper.isSuccess
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

typealias GetCommonWeatherInfoUseCase = suspend (String) -> CommonWeatherInfo

fun commonWeatherInfo(repository: Repository, coordinate: String): GetCommonWeatherInfoUseCase = {
    val geopositionUC: GetGeopositionUseCase = geopositionSearch(repository)
    val geopositionResult: Result<Geoposition> = geopositionUC(coordinate)
    if (geopositionResult.isSuccess()) onSuccess(
        geoposition = geopositionResult.asSuccess(),
        repository = repository
    ) else onFailure(geopositionResult.asFailure())
}

private suspend fun onSuccess(
    repository: Repository,
    geoposition: Success<Geoposition>
): CommonWeatherInfo = coroutineScope {
    val locationKey: String = geoposition.value.locationKey

    val hourlyForecastsDef: Deferred<Result<List<OneHourWeatherForecast>>> =
        async { twelveHoursForecastsResult(repository, locationKey) }
    val conditionsDef: Deferred<Result<List<CurrentCondition>>> =
        async { currentConditionsResult(repository, locationKey) }
    val dailyForecastsDef: Deferred<Result<List<OneDayWeatherForecast>>> =
        async { fiveDaysDailyForecastsResult(repository, locationKey) }

    CommonWeatherInfo(
        geoposition = geoposition,
        conditions = conditionsDef.await(),
        dailyForecasts = dailyForecastsDef.await(),
        twelveForecasts = hourlyForecastsDef.await()
    )
}

private fun <T : Throwable> onFailure(failure: Failure<T>) =
    CommonWeatherInfo(failure, failure, failure, failure)

suspend fun twelveHoursForecastsResult(
    repository: Repository,
    locationKey: String
): Result<List<OneHourWeatherForecast>> {
    val twelveHoursForecastsUC: GetTwelveHoursForecastsUseCase = twelveHoursForecasts(repository)
    return twelveHoursForecastsUC(locationKey)
}

suspend fun currentConditionsResult(
    repository: Repository,
    locationKey: String
): Result<List<CurrentCondition>> {
    val currentConditionsUC: GetCurrentConditionsUseCase = currentConditions(repository)
    return currentConditionsUC(locationKey)
}

suspend fun fiveDaysDailyForecastsResult(
    repository: Repository,
    locationKey: String
): Result<List<OneDayWeatherForecast>> {
    val dailyWeatherForecast = fiveDaysDailyForecasts(repository)
    return dailyWeatherForecast(locationKey)
}