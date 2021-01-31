package com.vorobyoff.weather.presentation.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vorobyoff.weather.data.RepositoryFactory.repository
import com.vorobyoff.weather.domain.usecases.GetGeopositionUseCase
import com.vorobyoff.weather.domain.usecases.geopositionSearch

class AppViewModelFactory : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val useCase: GetGeopositionUseCase = geopositionSearch(repository)
        return AppViewModel(useCase) as T
    }
}