package com.vorobyoff.weather.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import com.vorobyoff.weather.R
import com.vorobyoff.weather.databinding.FragmentFlowHomeBinding
import com.vorobyoff.weather.databinding.FragmentFlowHomeBinding.bind
import com.vorobyoff.weather.presentation.ui.extensions.viewBinding

class HomeFlowFragment : Fragment(R.layout.fragment_flow_home) {
    private val binding: FragmentFlowHomeBinding by viewBinding(::bind)

    override fun onViewCreated(view: View, state: Bundle?): Unit = replaceFragment()

    private fun replaceFragment(): Unit = childFragmentManager.commitNow {
        replace(binding.homeFlowContainer.id, WeatherFragment::class.java, null)
        setReorderingAllowed(true)
    }
}