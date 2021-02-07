package com.vorobyoff.weather.presentation.ui.viewmodels

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.vorobyoff.weather.data.RepositoryFactory.repository
import com.vorobyoff.weather.domain.models.City
import com.vorobyoff.weather.domain.usecases.GetCityUseCase
import com.vorobyoff.weather.domain.usecases.getCityByGeopositionUseCase
import com.vorobyoff.weather.domain.wrapper.Result
import com.vorobyoff.weather.domain.wrapper.asSuccess
import com.vorobyoff.weather.domain.wrapper.isSuccess
import com.vorobyoff.weather.presentation.models.CityVO
import com.vorobyoff.weather.presentation.models.toVO
import com.vorobyoff.weather.presentation.ui.extensions.awaitLastLocation
import com.vorobyoff.weather.presentation.ui.viewmodels.base.SharedViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import androidx.lifecycle.ViewModelProvider.Factory as VMFactory

class SharedViewModelImp(
    private val useCase: GetCityUseCase,
    private val locationProvider: FusedLocationProviderClient,
) : SharedViewModel() {
    override val city = MutableSharedFlow<CityVO>(replay = 1, extraBufferCapacity = 1)

    @ExperimentalCoroutinesApi
    @RequiresPermission(anyOf = [ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION])
    override fun findCityByGeolocation(): Job = viewModelScope.launch(IO) {
        val location = withContext(Dispatchers.Default) { locationProvider.awaitLastLocation() }
        val result: Result<City> = useCase(location.latitude, location.longitude)
        if (result.isSuccess()) city.emit(result.asSuccess().value.toVO())
    }

    class Factory(private val locationProvider: FusedLocationProviderClient) : VMFactory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val useCase: GetCityUseCase = getCityByGeopositionUseCase(repository)
            return SharedViewModelImp(useCase, locationProvider) as T
        }
    }
}