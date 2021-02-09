package com.vorobyoff.weather.presentation.ui.fragments

import androidx.fragment.app.Fragment
import com.vorobyoff.weather.R.layout.fragment_city_selection
import com.vorobyoff.weather.databinding.FragmentCitySelectionBinding
import com.vorobyoff.weather.databinding.FragmentCitySelectionBinding.bind
import com.vorobyoff.weather.presentation.ui.extensions.viewBinding

class CitySelectionFragment : Fragment(fragment_city_selection) {

    private val binding: FragmentCitySelectionBinding by viewBinding(::bind)
}