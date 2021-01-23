package com.vorobyoff.weather.presentation.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vorobyoff.weather.domain.model.City
import com.vorobyoff.weather.domain.usecases.GetTopCitiesListUseCase
import com.vorobyoff.weather.domain.wrapper.asFailure
import com.vorobyoff.weather.domain.wrapper.asSuccess
import com.vorobyoff.weather.domain.wrapper.isSuccess
import com.vorobyoff.weather.domain.wrapper.map
import com.vorobyoff.weather.presentation.model.CityItem
import com.vorobyoff.weather.presentation.model.toCityItem
import kotlinx.coroutines.launch

class SelectionViewModel(private val useCase: GetTopCitiesListUseCase) : ViewModel() {
    private val _topCities = MutableLiveData<List<CityItem>>()
    val topCities: LiveData<List<CityItem>> get() = _topCities
    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> get() = _error

    fun topCities(sortedBy: ((City) -> String)? = null) {
        viewModelScope.launch {
            val result = useCase.topCities(sortedBy).map { it.map(City::toCityItem) }
            if (result.isSuccess()) _topCities.value = result.asSuccess().value
            else _error.value = result.asFailure().error!!
        }
    }
}