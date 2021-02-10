package com.vorobyoff.weather.presentation.ui.viewmodels.base

import androidx.lifecycle.ViewModel
import com.vorobyoff.weather.presentation.models.CityVO
import com.vorobyoff.weather.presentation.models.State
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

abstract class SharedViewModel : ViewModel() {
    abstract val city: Flow<State<CityVO>>

    abstract fun findCityByGeolocation(): Job
}