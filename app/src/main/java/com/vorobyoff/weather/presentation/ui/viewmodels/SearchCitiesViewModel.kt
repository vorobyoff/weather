package com.vorobyoff.weather.presentation.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.vorobyoff.weather.domain.model.City
import com.vorobyoff.weather.domain.usecases.GetFoundCitiesUseCase
import com.vorobyoff.weather.presentation.model.CityItem
import com.vorobyoff.weather.presentation.model.toCityItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchCitiesViewModel(private val useCase: GetFoundCitiesUseCase) : ViewModel() {
    suspend fun foundCities(query: String): Flow<List<CityItem>> =
        useCase.foundCities(query).map { it.map(City::toCityItem) }
}