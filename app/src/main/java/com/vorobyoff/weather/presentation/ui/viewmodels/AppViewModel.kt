package com.vorobyoff.weather.presentation.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vorobyoff.weather.domain.models.City
import com.vorobyoff.weather.domain.usecases.GetCityUseCase
import com.vorobyoff.weather.domain.wrapper.Result
import com.vorobyoff.weather.domain.wrapper.asFailure
import com.vorobyoff.weather.domain.wrapper.asSuccess
import com.vorobyoff.weather.domain.wrapper.isSuccess
import com.vorobyoff.weather.presentation.models.CityState
import com.vorobyoff.weather.presentation.models.CityState.Error
import com.vorobyoff.weather.presentation.models.CityState.Loading
import com.vorobyoff.weather.presentation.models.mapToVO
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppViewModel(private val useCase: GetCityUseCase) : ViewModel() {
    private val _geolocation: MutableStateFlow<CityState> = MutableStateFlow(Loading)
    val geolocation: StateFlow<CityState> = _geolocation

    fun getLocationKey(lon: Double, lat: Double): Job = viewModelScope.launch(IO) {
        val geoposition = "$lon,$lat"
        val result: Result<City> = useCase(geoposition)
        if (result.isSuccess()) {
            val data: City = result.asSuccess().value
            _geolocation.value = data.mapToVO()
        } else {
            val error: Throwable? = result.asFailure().error
            _geolocation.value = Error(error)
        }
    }
}