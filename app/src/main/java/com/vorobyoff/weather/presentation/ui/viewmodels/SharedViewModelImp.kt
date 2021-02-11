package com.vorobyoff.weather.presentation.ui.viewmodels

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.location.Location
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.vorobyoff.weather.data.RepositoryFactory.repository
import com.vorobyoff.weather.domain.models.City
import com.vorobyoff.weather.domain.usecases.GetCityUseCase
import com.vorobyoff.weather.domain.usecases.getCityByGeopositionUseCase
import com.vorobyoff.weather.domain.wrapper.Result
import com.vorobyoff.weather.domain.wrapper.asFailure
import com.vorobyoff.weather.domain.wrapper.asSuccess
import com.vorobyoff.weather.domain.wrapper.isSuccess
import com.vorobyoff.weather.presentation.models.CityVO
import com.vorobyoff.weather.presentation.models.State
import com.vorobyoff.weather.presentation.models.toVO
import com.vorobyoff.weather.presentation.exception.LocationDisabledException
import com.vorobyoff.weather.presentation.ui.extensions.awaitLastLocation
import com.vorobyoff.weather.presentation.ui.viewmodels.base.SharedViewModel
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException
import androidx.lifecycle.ViewModelProvider.Factory as VMFactory

class SharedViewModelImp(
    private val useCase: GetCityUseCase,
    private val locationProvider: FusedLocationProviderClient,
) : SharedViewModel() {
    override val city = MutableSharedFlow<State<CityVO>>(replay = 1, extraBufferCapacity = 1)

    @RequiresPermission(anyOf = [ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION])
    override fun findCityByGeolocation(): Job = viewModelScope.launch(IO) {
        city.emit(State.Loading)
        val location: Location = withContext(Default) { locationProvider.awaitLastLocation() }
        val cityState: State<CityVO> = try {
            val result: Result<City> = useCase(location.latitude, location.longitude)

            if (result.isSuccess()) State.Successed(result.asSuccess().value.toVO())
            else State.Errored(result.asFailure().error!!)
        } catch (cause: Exception) {
            when (cause) {
                is NullPointerException -> State.Errored(cause as LocationDisabledException)
                is CancellationException -> throw cause
                else -> State.Errored(cause)
            }
        }

        city.emit(cityState)
    }

    class Factory(private val locationProvider: FusedLocationProviderClient) : VMFactory {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val useCase: GetCityUseCase = getCityByGeopositionUseCase(repository)
            return SharedViewModelImp(useCase, locationProvider) as T
        }
    }
}