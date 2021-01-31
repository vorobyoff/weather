package com.vorobyoff.weather.presentation.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vorobyoff.weather.domain.models.Geoposition
import com.vorobyoff.weather.domain.usecases.GetGeopositionUseCase
import com.vorobyoff.weather.domain.wrapper.Result
import com.vorobyoff.weather.domain.wrapper.asFailure
import com.vorobyoff.weather.domain.wrapper.asSuccess
import com.vorobyoff.weather.domain.wrapper.isSuccess
import com.vorobyoff.weather.presentation.models.GeopositionState
import com.vorobyoff.weather.presentation.models.GeopositionState.Error
import com.vorobyoff.weather.presentation.models.GeopositionState.Loading
import com.vorobyoff.weather.presentation.models.mapToVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppViewModel(private val useCase: GetGeopositionUseCase) : ViewModel() {
    private val _geolocation: MutableStateFlow<GeopositionState> = MutableStateFlow(Loading)
    val geolocation: StateFlow<GeopositionState> = _geolocation

    fun getLocationKey(lon: Double, lan: Double) {
        viewModelScope.launch {
            val geoposition = "$lon,$lan"
            val result: Result<Geoposition> = useCase(geoposition)
            if (result.isSuccess()) {
                val data: Geoposition = result.asSuccess().value
                _geolocation.value = data.mapToVO()
            } else {
                val error: Throwable? = result.asFailure().error
                _geolocation.value = Error(error)
            }
        }
    }
}