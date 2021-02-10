package com.vorobyoff.weather.presentation.ui.viewmodels.base

import androidx.lifecycle.ViewModel
import com.vorobyoff.weather.presentation.models.State
import com.vorobyoff.weather.presentation.models.WeatherVO
import kotlinx.coroutines.flow.Flow

abstract class WeatherViewModel : ViewModel() {
    abstract val weather: Flow<State<WeatherVO>>

    abstract fun requestWeather(locationKey: String)
}