package com.vorobyoff.weather.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import com.vorobyoff.weather.R.layout.fragment_host
import com.vorobyoff.weather.R.navigation.weather_graph
import com.vorobyoff.weather.databinding.FragmentHostBinding
import com.vorobyoff.weather.databinding.FragmentHostBinding.bind
import com.vorobyoff.weather.presentation.ui.extensions.setupWithNavController
import com.vorobyoff.weather.presentation.ui.extensions.viewBinding

class HostFragment : Fragment(fragment_host) {
    private val containerId: Int get() = binding.fragmentHostContainer.id
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
        val ids: List<Int> = listOf(weather_graph)

        val navController: LiveData<NavController> = binding.bottomNavView.setupWithNavController(
            fragmentManager = childFragmentManager,
            intent = requireActivity().intent,
            containerId = containerId,
            navGraphIds = ids
        )
        currentController = navController
        observeController(navController)

        registerOnBackPressedDispatcher()
    }

    private fun observeController(navController: LiveData<NavController>): Unit =
        navController.observe(viewLifecycleOwner) { controller: NavController ->
            NavigationUI.setupWithNavController(binding.bottomNavView, controller)
        }

    private fun registerOnBackPressedDispatcher(): Unit = requireActivity().onBackPressedDispatcher
        .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isEnabled) currentController?.value?.navigateUp()
            }
        })
}