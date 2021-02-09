package com.vorobyoff.weather.presentation.ui.fragments

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.annotation.SuppressLint
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.util.Log
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
import com.vorobyoff.weather.domain.wrapper.HttpException
import com.vorobyoff.weather.presentation.models.WeatherStates.*
import com.vorobyoff.weather.presentation.ui.extensions.checkSelfPermissionCompat
import com.vorobyoff.weather.presentation.ui.extensions.viewBinding
import com.vorobyoff.weather.presentation.ui.viewmodels.WeatherViewModelImp.Factory
import com.vorobyoff.weather.presentation.ui.viewmodels.base.SharedViewModel
import com.vorobyoff.weather.presentation.ui.viewmodels.base.WeatherViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

class WeatherFragment : Fragment(fragment_weather) {
    companion object {
        private val TAG = WeatherFragment::class.java.canonicalName!!
    }

    private val binding: FragmentWeatherBinding by viewBinding(::bind)
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val weatherViewModel: WeatherViewModel by viewModels { Factory() }

    @ExperimentalCoroutinesApi
    @Suppress("missingPermission")
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
        observeOwnViewModelFlows()
    }

    @ExperimentalCoroutinesApi
    private fun observeSharedViewModelFlows() = lifecycleScope.launchWhenCreated {
        sharedViewModel.city.collect {
            weatherViewModel.requestWeather(it.locationKey)
            binding.cityNameTxt.text = it.cityName
        }
    }

    private fun observeOwnViewModelFlows() = lifecycleScope.launchWhenCreated {
        weatherViewModel.weather.collect { weatherState ->
            when (weatherState) {
                is LoadingWeather -> onLoadingReceived()
                is WrongWeather -> onWrongReceived(weatherState)
                is SuccessfulWeather -> onSuccessfulReceived(weatherState)
            }
        }
    }

    private fun onLoadingReceived() {
        Log.d(TAG, "LOADING")
    }

    private fun onWrongReceived(wrongWeather: WrongWeather) {
        if (wrongWeather.cause is HttpException) {
            Log.d(TAG, "${wrongWeather.cause.statusMessage}")
            Log.d(TAG, "${wrongWeather.cause.statusCode}")
            Log.d(TAG, "${wrongWeather.cause.url}")
        } else Log.d(TAG, "${wrongWeather.cause}")
    }

    private fun onSuccessfulReceived(weather: SuccessfulWeather): Unit = with(binding) {
        currentTempTxt.text = "${weather.conditions[0].actuallyTemperature.metric.value}"
        realFeelTxt.text = "${weather.conditions[0].feelTemperature.metric.value}"
    }
}