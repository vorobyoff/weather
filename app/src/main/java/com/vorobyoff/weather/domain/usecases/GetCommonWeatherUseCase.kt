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
    val geopositionUC: GetCityUseCase = geopositionSearch(repository)
    val cityResult: Result<City> = geopositionUC(coordinate)
    if (cityResult.isSuccess()) onSuccess(
        city = cityResult.asSuccess(),
        repository = repository
    ) else onFailure(cityResult.asFailure())
}

private suspend fun onSuccess(
    repository: Repository,
    city: Success<City>
): CommonWeatherInfo = coroutineScope {
    val locationKey: String = city.value.locationKey

    val hourlyForecastsDef: Deferred<Result<List<OneHourWeatherForecast>>> =
        async { twelveHoursForecastsResult(repository, locationKey) }
    val conditionsDef: Deferred<Result<List<CurrentCondition>>> =
        async { currentConditionsResult(repository, locationKey) }
    val dailyForecastsDef: Deferred<Result<List<OneDayWeatherForecast>>> =
        async { fiveDaysDailyForecastsResult(repository, locationKey) }

    CommonWeatherInfo(
        city = city,
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