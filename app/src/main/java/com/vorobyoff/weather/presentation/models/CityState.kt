package com.vorobyoff.weather.presentation.models

sealed class CityState {
    object Loading : CityState()
    class Error(throwable: Throwable?) : CityState()
    data class CityVO(val locationKey: String, val cityName: String) : CityState()
}
