package com.vorobyoff.weather.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.vorobyoff.weather.R
import com.vorobyoff.weather.databinding.FragmentWeatherBinding
import com.vorobyoff.weather.databinding.FragmentWeatherBinding.bind
import com.vorobyoff.weather.presentation.ui.extensions.viewBinding

class WeatherFragment : Fragment(R.layout.fragment_weather) {
    private val binding: FragmentWeatherBinding by viewBinding(::bind)

    override fun onViewCreated(view: View, state: Bundle?) {

    }
}