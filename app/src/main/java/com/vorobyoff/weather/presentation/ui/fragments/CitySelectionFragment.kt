package com.vorobyoff.weather.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.vorobyoff.weather.R
import com.vorobyoff.weather.databinding.FragmentCitySelectionBinding
import com.vorobyoff.weather.databinding.FragmentCitySelectionBinding.bind
import com.vorobyoff.weather.domain.wrapper.HttpException
import com.vorobyoff.weather.presentation.adapter.CitiesListAdapter
import com.vorobyoff.weather.presentation.model.CityItem
import com.vorobyoff.weather.presentation.ui.extensions.addSystemWindowInsetToMargin
import com.vorobyoff.weather.presentation.ui.extensions.addSystemWindowInsetToPadding
import com.vorobyoff.weather.presentation.ui.extensions.viewBinding
import com.vorobyoff.weather.presentation.ui.viewmodels.SelectionViewModel
import com.vorobyoff.weather.presentation.ui.viewmodels.factories.SelectionViewModelFactory
import java.io.IOException
import java.net.UnknownHostException

class CitySelectionFragment : Fragment(R.layout.fragment_city_selection) {
    private val binding: FragmentCitySelectionBinding by viewBinding(::bind)
    private val selectionViewModel: SelectionViewModel by viewModels { SelectionViewModelFactory() }
    private val citiesAdapter: CitiesListAdapter by lazy { CitiesListAdapter() }

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        selectionViewModel.topCities { it.name }
    }

    override fun onViewCreated(view: View, state: Bundle?) {
        customizeUiElements()
        observeLiveData()
    }

    private fun customizeUiElements(): Unit = with(binding) {
        root.addSystemWindowInsetToPadding(top = true)
        customizeList()
        customizeFab()
    }

    private fun customizeList(): Unit = with(binding.citiesRv) {
        layoutManager = LinearLayoutManager(context, VERTICAL, false)
        addSystemWindowInsetToPadding(bottom = true)
        adapter = citiesAdapter
        setHasFixedSize(true)
    }

    private fun customizeFab(): Unit = with(binding.searchFab) {
        addSystemWindowInsetToMargin(bottom = true)
        setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, SearchCitiesFragment())
                .addToBackStack(null)
                .setReorderingAllowed(true)
                .commit()
        }
    }

    private fun observeLiveData(): Unit = with(selectionViewModel) {
        topCities.observe(viewLifecycleOwner, ::onDataReceive)
        error.observe(viewLifecycleOwner, ::onErrorReceive)
    }

    private fun onDataReceive(cities: List<CityItem>) {
        citiesAdapter.submitList(cities)
        with(binding) {
            progress.hide()
            popularCitiesTxt.visibility = VISIBLE
            searchFab.visibility = VISIBLE
            citiesRv.visibility = VISIBLE
        }
    }

    private fun onErrorReceive(error: Throwable) {
        binding.progress.hide()
        when (error) {
            is UnknownHostException -> TODO()
            is HttpException -> TODO()
            is IOException -> TODO()
            else -> TODO()
        }
    }
}