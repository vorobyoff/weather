package com.vorobyoff.weather.presentation.ui.viewmodels.base

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import com.vorobyoff.weather.presentation.models.CityVO
import com.vorobyoff.weather.presentation.models.State
import kotlinx.coroutines.flow.Flow

abstract class SharedViewModel : ViewModel() {
    abstract val city: Flow<State<CityVO>>

    @RequiresPermission(anyOf = [ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION])
    abstract fun findCityByGeolocation()
}