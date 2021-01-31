package com.vorobyoff.weather.presentation.models

sealed class GeopositionState {
    object Loading : GeopositionState()
    class Error(throwable: Throwable?) : GeopositionState()
    data class GeopositionVO(val locationKey: String, val cityName: String) : GeopositionState()
}
