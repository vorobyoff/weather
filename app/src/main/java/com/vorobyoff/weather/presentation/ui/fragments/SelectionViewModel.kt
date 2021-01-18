package com.vorobyoff.weather.presentation.ui.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vorobyoff.weather.data.RepositoryImpl
import com.vorobyoff.weather.data.datasource.NetworkDataSource
import com.vorobyoff.weather.data.datasource.network.NetworkFactory.ACCU_WEATHER_API
import com.vorobyoff.weather.domain.Repository
import com.vorobyoff.weather.domain.model.City
import com.vorobyoff.weather.domain.wrapper.asFailure
import com.vorobyoff.weather.domain.wrapper.asSuccess
import com.vorobyoff.weather.domain.wrapper.isSuccess
import com.vorobyoff.weather.domain.wrapper.map
import com.vorobyoff.weather.presentation.model.CityItem
import com.vorobyoff.weather.presentation.model.toCityItem
import kotlinx.coroutines.launch

class SelectionViewModel : ViewModel() {
    private val repository: Repository = RepositoryImpl(NetworkDataSource(ACCU_WEATHER_API))
    val topCities = MutableLiveData<List<CityItem>>()
    val error = MutableLiveData<Throwable>()

    fun topCities() {
        viewModelScope.launch {
            val result = repository.topCities().map { it.map(City::toCityItem) }
            if (result.isSuccess()) topCities.value = result.asSuccess().value
            else error.value = result.asFailure().error!!
        }
    }
}