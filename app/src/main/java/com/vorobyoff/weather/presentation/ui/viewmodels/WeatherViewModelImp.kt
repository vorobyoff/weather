package com.vorobyoff.weather.presentation.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vorobyoff.weather.data.RepositoryFactory.repository
import com.vorobyoff.weather.domain.models.WeatherWrapper
import com.vorobyoff.weather.domain.usecases.*
import com.vorobyoff.weather.presentation.models.WeatherStates
import com.vorobyoff.weather.presentation.models.WeatherStates.LoadingWeather
import com.vorobyoff.weather.presentation.models.toVO
import com.vorobyoff.weather.presentation.ui.viewmodels.base.WeatherViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.contracts.ExperimentalContracts

class WeatherViewModelImp(private val useCase: GetWeatherUseCase) : WeatherViewModel() {
    override val weather: MutableStateFlow<WeatherStates> = MutableStateFlow(LoadingWeather)

    @ExperimentalContracts
    override fun requestWeather(locationKey: String) {
        viewModelScope.launch(IO) {
            val info: WeatherWrapper = useCase(locationKey)
            if (info.isWeather()) weather.value = info.asWeather().toVO()
            else weather.value = info.asMistake().toVO()
        }
    }

    class Factory : ViewModelProvider.Factory {

        @ExperimentalContracts
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val currentConditionsUC: GetCurrentConditionsUseCase =
                getCurrentConditionsUseCase(repository)
            val twelveHoursForecastsUC: GetTwelveHoursForecastsUseCase =
                getTwelveHoursForecastsUseCase(repository)
            val fiveDaysForecastsUC: GetFiveDaysForecastsUseCase =
                getFiveDaysForecastsUseCase(repository)
            val commonWeatherInfoUC: GetWeatherUseCase = getWeatherUseCase(
                getTwelveHoursForecastsUseCase = twelveHoursForecastsUC,
                getFiveDaysForecastsUseCase = fiveDaysForecastsUC,
                getCurrentConditionsUseCase = currentConditionsUC
            )

            return WeatherViewModelImp(commonWeatherInfoUC) as T
        }
    }
}