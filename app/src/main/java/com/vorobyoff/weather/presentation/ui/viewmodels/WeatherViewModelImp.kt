package com.vorobyoff.weather.presentation.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vorobyoff.weather.data.RepositoryFactory.repository
import com.vorobyoff.weather.domain.models.Weather
import com.vorobyoff.weather.domain.usecases.*
import com.vorobyoff.weather.domain.wrapper.Result
import com.vorobyoff.weather.domain.wrapper.asFailure
import com.vorobyoff.weather.domain.wrapper.asSuccess
import com.vorobyoff.weather.domain.wrapper.isSuccess
import com.vorobyoff.weather.presentation.models.State
import com.vorobyoff.weather.presentation.models.WeatherVO
import com.vorobyoff.weather.presentation.models.toVO
import com.vorobyoff.weather.presentation.ui.viewmodels.base.WeatherViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class WeatherViewModelImp(private val useCase: GetWeatherUseCase) : WeatherViewModel() {
    override val weather: MutableStateFlow<State<WeatherVO>> = MutableStateFlow(State.Loading)

    override fun requestWeather(locationKey: String) {
        viewModelScope.launch(IO) {
            val result: Result<Weather> = useCase(locationKey)
            val weatherState: State<WeatherVO> =
                if (result.isSuccess()) State.Successed(result.asSuccess().value.toVO())
                else State.Errored(result.asFailure().error!!)

            weather.value = weatherState
        }
    }

    class Factory : ViewModelProvider.Factory {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val currentConditionsUC: GetCurrentConditionsUseCase =
                getCurrentConditionsUseCase(repository)
            val twelveHoursForecastsUC: GetTwelveHoursForecastsUseCase =
                getTwelveHoursForecastsUseCase(repository)
            val fiveDaysForecastsUC: GetFiveDaysForecastsUseCase =
                getFiveDaysForecastsUseCase(repository)
            val commonWeatherInfoUC: GetWeatherUseCase = getWeatherUseCase(
                twelveHoursForecastsUC = twelveHoursForecastsUC,
                fiveDaysForecastsUC = fiveDaysForecastsUC,
                currentConditionsUC = currentConditionsUC
            )

            return WeatherViewModelImp(commonWeatherInfoUC) as T
        }
    }
}