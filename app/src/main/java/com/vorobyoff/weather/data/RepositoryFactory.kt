package com.vorobyoff.weather.data

import com.vorobyoff.weather.data.datasource.NetworkDataSource
import com.vorobyoff.weather.data.datasource.remote.NetworkFactory.weatherApi
import com.vorobyoff.weather.data.models.CurrentConditionResponse
import com.vorobyoff.weather.data.models.CurrentConditionResponse.TypedValuesResponse
import com.vorobyoff.weather.data.models.CurrentConditionResponse.WindResponse
import com.vorobyoff.weather.data.models.GeopositionResponse
import com.vorobyoff.weather.data.models.TypedValueResponse
import com.vorobyoff.weather.domain.Repository
import com.vorobyoff.weather.domain.model.CurrentCondition
import com.vorobyoff.weather.domain.model.CurrentCondition.TypedValues
import com.vorobyoff.weather.domain.model.CurrentCondition.Wind
import com.vorobyoff.weather.domain.model.Geoposition
import com.vorobyoff.weather.domain.model.TypedValue
import kotlin.math.roundToInt

object RepositoryFactory {

//    fun create(): Repository = RepositoryImpl(
//        dataSource = NetworkDataSource(weatherApi),
//        mapGeopositionResponse = createGeopositionMapper(),
//        mapTwelveHoursForecast =,
//        mapCurrentConditionResponse = createCurrentConditionsMapper()
//    )

    private fun createGeopositionMapper(): (GeopositionResponse) -> Geoposition = {
        Geoposition(locationKey = it.locationKey, cityName = it.locationKey)
    }

    private fun createCurrentConditionsMapper(): (List<CurrentConditionResponse>) -> List<CurrentCondition> =
        { responses -> responses.map { mapCurrentConditionResponse(it) } }

    private fun mapCurrentConditionResponse(response: CurrentConditionResponse) = CurrentCondition(
        uvIndex = response.uvIndex,
        humidity = response.humidity,
        iconCode = response.iconCode,
        description = response.description,
        wind = mapWindResponse(response.wind),
        feelTemperature = mapTypedValuesResponse(response.feelTemperature),
        actuallyTemperature = mapTypedValuesResponse(response.actuallyTemperature)
    )

    private fun mapWindResponse(response: WindResponse): Wind =
        Wind(speed = mapTypedValuesResponse(response.speed))

    private fun mapTypedValuesResponse(response: TypedValuesResponse) = TypedValues(
        metric = mapTypedValueResponse(response.metric),
        imperial = mapTypedValueResponse(response.imperial)
    )

    private fun mapTypedValueResponse(response: TypedValueResponse) =
        TypedValue(unit = response.unit, value = response.value?.roundToInt() ?: 0)
}