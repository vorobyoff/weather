package com.vorobyoff.weather.presentation.ui.fragments

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.annotation.SuppressLint
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.vorobyoff.weather.R.id.action_weather_fragment_to_citySelectionFragment
import com.vorobyoff.weather.R.layout.fragment_weather
import com.vorobyoff.weather.R.string.*
import com.vorobyoff.weather.databinding.FragmentWeatherBinding
import com.vorobyoff.weather.databinding.FragmentWeatherBinding.bind
import com.vorobyoff.weather.presentation.adapters.DailyForecastsListAdapter
import com.vorobyoff.weather.presentation.adapters.HourlyForecastsListAdapter
import com.vorobyoff.weather.presentation.models.CityVO
import com.vorobyoff.weather.presentation.models.CurrentConditionVO
import com.vorobyoff.weather.presentation.models.State
import com.vorobyoff.weather.presentation.models.WeatherVO
import com.vorobyoff.weather.presentation.ui.extensions.checkSelfPermissionCompat
import com.vorobyoff.weather.presentation.ui.extensions.viewBinding
import com.vorobyoff.weather.presentation.ui.viewmodels.base.SharedViewModel
import com.vorobyoff.weather.presentation.ui.viewmodels.base.WeatherViewModel
import kotlinx.coroutines.flow.collect
import com.vorobyoff.weather.presentation.ui.viewmodels.SharedViewModelImp.Factory as SharedFactory
import com.vorobyoff.weather.presentation.ui.viewmodels.WeatherViewModelImp.Factory as WeatherFactory

class WeatherFragment : Fragment(fragment_weather) {
    companion object {
        private val TAG = WeatherFragment::class.java.canonicalName!!
    }

    private val binding: FragmentWeatherBinding by viewBinding(::bind)
    private val weatherViewModel: WeatherViewModel by viewModels { WeatherFactory() }
    private val sharedViewModel: SharedViewModel by activityViewModels {
        SharedFactory(getFusedLocationProviderClient(requireContext()))
    }

    @Suppress("missingPermission")
    private val registerPermissionLauncher: ActivityResultLauncher<String> by lazy {
        registerForActivityResult(RequestPermission()) { isGranted ->
            if (isGranted) sharedViewModel.findCityByGeolocation()
            else findNavController().navigate(action_weather_fragment_to_citySelectionFragment)
        }
    }

    override fun onCreate(instanceState: Bundle?) {
        super.onCreate(instanceState)

        @SuppressLint("missingPermission")
        if (checkSelfPermissionCompat(ACCESS_COARSE_LOCATION) == PERMISSION_DENIED)
            registerPermissionLauncher.launch(ACCESS_COARSE_LOCATION)
        else sharedViewModel.findCityByGeolocation()

        observeSharedViewModelFlows()
    }

    private fun observeSharedViewModelFlows() {
        lifecycleScope.launchWhenCreated {
            sharedViewModel.city.collect { cityState: State<CityVO> ->
                if (cityState is State.Successed) onCityRequestSuccessed(cityState)
                else {
                    val error: Throwable = (cityState as State.Errored<*>).error
                    onErrorReceived(error)
                }
            }
        }
    }

    private fun onCityRequestSuccessed(state: State.Successed<CityVO>) {
        weatherViewModel.requestWeather(state.value.locationKey)
        binding.cityNameTxt.text = state.value.cityName
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = observeOwnViewModelFlows()

    private fun observeOwnViewModelFlows() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            weatherViewModel.weather.collect { weatherState -> handleStates(weatherState) }
        }
    }

    private fun handleStates(weatherState: State<WeatherVO>): Unit = when (weatherState) {
        is State.Loading -> onLoadingReceived()
        is State.Errored<*> -> onErrorReceived(weatherState.error)
        is State.Successed -> onWeatherRequestSuccessed(weatherState.value)
    }

    private fun onLoadingReceived() {
        Log.d(TAG, "WeatherState is loading")
    }

    private fun onErrorReceived(cause: Throwable) {
        Log.d(TAG, "Something went wrong\ncause: ${cause}\nmessage: ${cause.message}")
    }

    private val dailyForecastsListAdapter = DailyForecastsListAdapter()
    private val hourlyForecastsListAdapter = HourlyForecastsListAdapter()

    private fun onWeatherRequestSuccessed(weather: WeatherVO) {
        hourlyForecastsListAdapter.submitList(weather.twelveHoursForecasts)
        dailyForecastsListAdapter.submitList(weather.fiveDaysForecasts)
        with(binding) {
            hourlyForecastsRv.adapter = hourlyForecastsListAdapter
            dailyForecastsRv.adapter = dailyForecastsListAdapter
        }
        printConditionInfo(weather.conditions[0])
    }

    private fun printConditionInfo(condition: CurrentConditionVO): Unit = with(binding) {
        currentTempTxt.text =
            getString(current_temperature_pattern, condition.actuallyTemperature.metric.value)
        realFeelTxt.text = getString(real_feel_temperature_pattern, condition.feelTemperature.metric.value)
        windSpeedParamsTxt.text = getString(wind_speed_metric_pattern, condition.wind.speed.metric.value)
        pressureParamsTxt.text = getString(pressure_pattern, condition.pressure.metric.value)
        humidityParamsTxt.text = getString(humidity_pattern, condition.humidity)
        descriptionTxt.text = condition.description
        uvParamsTxt.text = "${condition.uvIndex}"
    }
}