package com.vorobyoff.weather.presentation.ui.fragments

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.annotation.SuppressLint
import android.content.pm.PackageManager.PERMISSION_GRANTED
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
import com.vorobyoff.weather.R.id.action_weather_fragment_to_citySelectionFragment
import com.vorobyoff.weather.R.layout.fragment_weather
import com.vorobyoff.weather.databinding.FragmentWeatherBinding
import com.vorobyoff.weather.databinding.FragmentWeatherBinding.bind
import com.vorobyoff.weather.presentation.models.CityVO
import com.vorobyoff.weather.presentation.models.State
import com.vorobyoff.weather.presentation.ui.extensions.checkSelfPermissionCompat
import com.vorobyoff.weather.presentation.ui.extensions.viewBinding
import com.vorobyoff.weather.presentation.ui.viewmodels.WeatherViewModelImp.Factory
import com.vorobyoff.weather.presentation.ui.viewmodels.base.SharedViewModel
import com.vorobyoff.weather.presentation.ui.viewmodels.base.WeatherViewModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

class WeatherFragment : Fragment(fragment_weather) {
    companion object {
        private val TAG = WeatherFragment::class.java.canonicalName!!
    }

    private val binding: FragmentWeatherBinding by viewBinding(::bind)
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val weatherViewModel: WeatherViewModel by viewModels { Factory() }

    private val registerPermissionLauncher: ActivityResultLauncher<String> by lazy {
        registerForActivityResult(RequestPermission()) { isGranted ->
            if (isGranted) sharedViewModel.findCityByGeolocation()
            else findNavController().navigate(action_weather_fragment_to_citySelectionFragment)
        }
    }

    @ExperimentalCoroutinesApi
    override fun onCreate(instanceState: Bundle?) {
        super.onCreate(instanceState)

        @SuppressLint("missingPermission")
        if (requireActivity().checkSelfPermissionCompat(ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED)
            sharedViewModel.findCityByGeolocation()
        else registerPermissionLauncher.launch(ACCESS_COARSE_LOCATION)

        observeSharedViewModelFlows()
    }

    private fun observeSharedViewModelFlows(): Job = lifecycleScope.launchWhenCreated {
        sharedViewModel.city.collect { cityState: State<CityVO> ->
            when (cityState) {
                is State.Successed -> onSuccessReceived(cityState)
                is State.Loading -> withContext(Main) { onLoadingReceived() }
                is State.Errored<*> -> withContext(Main) { onErrorReceived(cityState.error) }
            }
        }
    }

    private fun onLoadingReceived(): Int = Log.d(TAG, "CityState is loading")

    private fun onErrorReceived(cause: Throwable): Int =
        Log.d(TAG, "Something went wrong\ncause: ${cause}\nmessage: ${cause.message}")

    private suspend fun onSuccessReceived(state: State.Successed<CityVO>) {
        weatherViewModel.requestWeather(state.value.locationKey)
        withContext(Main) { binding.cityNameTxt.text = state.value.cityName }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeOwnViewModelFlows()
    }

    private fun observeOwnViewModelFlows(): Job = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
        weatherViewModel.weather.collect { weatherState ->
            when (weatherState) {
                is State.Successed -> with(binding) {
                    val condition = weatherState.value.conditions[0]
                    currentTempTxt.text = "${condition.actuallyTemperature.metric.value}"
                    realFeelTxt.text = "${condition.feelTemperature.metric.value}"
                    humidityParamsTxt.text = "${condition.humidity}"
                    uvParamsTxt.text = "${condition.uvIndex}"
                }
                is State.Loading -> withContext(Main) { onLoadingReceived() }
                is State.Errored<*> -> withContext(Main) { onErrorReceived(weatherState.error) }
            }
        }
    }
}