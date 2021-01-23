package com.vorobyoff.weather.presentation.ui.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vorobyoff.weather.data.RepositoryImpl
import com.vorobyoff.weather.data.datasource.NetworkDataSource
import com.vorobyoff.weather.data.datasource.remote.NetworkFactory.weatherApi
import com.vorobyoff.weather.domain.usecases.GetTopCitiesListUseCase
import com.vorobyoff.weather.presentation.ui.viewmodels.SelectionViewModel

class SelectionViewModelFactory : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val datasource = NetworkDataSource(weatherApi)
        val repository = RepositoryImpl(datasource)
        val case = GetTopCitiesListUseCase(repository)
        return SelectionViewModel(case) as T
    }
}