package com.vorobyoff.weather.presentation.ui.viewmodels.base

import androidx.lifecycle.ViewModel
import com.vorobyoff.weather.presentation.models.WeatherStates
import kotlinx.coroutines.flow.Flow

abstract class WeatherViewModel : ViewModel() {
    abstract val weather: Flow<WeatherStates>

    abstract fun requestWeather(locationKey: String)
}