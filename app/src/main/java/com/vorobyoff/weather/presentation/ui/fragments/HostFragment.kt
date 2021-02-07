package com.vorobyoff.weather.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import com.vorobyoff.weather.R
import com.vorobyoff.weather.databinding.FragmentHostBinding
import com.vorobyoff.weather.databinding.FragmentHostBinding.bind
import com.vorobyoff.weather.presentation.ui.extensions.setupWithNavController
import com.vorobyoff.weather.presentation.ui.extensions.viewBinding

class HostFragment : Fragment(R.layout.fragment_host) {
    private val binding: FragmentHostBinding by viewBinding(::bind)
    private var currentController: LiveData<NavController>? = null

    override fun onViewCreated(view: View, state: Bundle?) {
        if (state == null) setupBottomNavBar()
    }

    override fun onViewStateRestored(state: Bundle?) {
        super.onViewStateRestored(state)
        setupBottomNavBar()
    }

    private fun setupBottomNavBar() {
        val ids: List<Int> = listOf(R.navigation.navigation_weather_graph)
        val controller: LiveData<NavController> = binding.bottomNavView.setupWithNavController(
            containerId = binding.fragmentHostContainer.id,
            fragmentManager = childFragmentManager,
            intent = requireActivity().intent,
            navGraphIds = ids
        )

        controller.observe(viewLifecycleOwner) { navController ->
            NavigationUI.setupWithNavController(binding.bottomNavView, navController)
        }

        currentController = controller
        requireActivity().onBackPressedDispatcher.addCallback {
            currentController?.value?.navigateUp()
        }
    }
}