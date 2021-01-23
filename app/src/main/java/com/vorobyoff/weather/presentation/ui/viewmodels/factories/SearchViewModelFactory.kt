package com.vorobyoff.weather.presentation.ui.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vorobyoff.weather.data.RepositoryImpl
import com.vorobyoff.weather.data.datasource.NetworkDataSource
import com.vorobyoff.weather.data.datasource.remote.NetworkFactory.weatherApi
import com.vorobyoff.weather.domain.usecases.GetFoundCitiesUseCase
import com.vorobyoff.weather.presentation.ui.viewmodels.SearchCitiesViewModel

class SearchViewModelFactory : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val dataSource = NetworkDataSource(weatherApi)
        val repository = RepositoryImpl(dataSource)
        val useCase = GetFoundCitiesUseCase(repository)
        return SearchCitiesViewModel(useCase) as T
    }
}