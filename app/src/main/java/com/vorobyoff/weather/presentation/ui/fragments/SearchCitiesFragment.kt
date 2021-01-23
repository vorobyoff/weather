package com.vorobyoff.weather.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.vorobyoff.weather.R
import com.vorobyoff.weather.databinding.FragmentSearchCitiesBinding
import com.vorobyoff.weather.databinding.FragmentSearchCitiesBinding.bind
import com.vorobyoff.weather.domain.wrapper.HttpException
import com.vorobyoff.weather.presentation.adapter.CitiesListAdapter
import com.vorobyoff.weather.presentation.ui.extensions.addSystemWindowInsetToPadding
import com.vorobyoff.weather.presentation.ui.extensions.inputStateFlow
import com.vorobyoff.weather.presentation.ui.extensions.viewBinding
import com.vorobyoff.weather.presentation.model.CityItem
import com.vorobyoff.weather.presentation.ui.viewmodels.SearchCitiesViewModel
import com.vorobyoff.weather.presentation.ui.viewmodels.factories.SearchViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.UnknownHostException

class SearchCitiesFragment : Fragment(R.layout.fragment_search_cities) {
    private val searchViewModel: SearchCitiesViewModel by viewModels { SearchViewModelFactory() }
    private val binding: FragmentSearchCitiesBinding by viewBinding(::bind)
    private val citiesAdapter = CitiesListAdapter()

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        customizeUiElements()
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun customizeUiElements() {
        binding.root.addSystemWindowInsetToPadding(top = true)
        lifecycleScope.launch { customizeSearchView() }
        customizeList()
    }

    private fun customizeList(): Unit = with(binding.foundCities) {
        layoutManager = LinearLayoutManager(context, VERTICAL, false)
        addSystemWindowInsetToPadding(bottom = true)
        adapter = citiesAdapter
        setHasFixedSize(true)
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private suspend fun customizeSearchView(): Unit = with(binding.searchView) {
        inputStateFlow()
            .debounce(300)
            .filter { it.isNotEmpty() }
            .distinctUntilChanged()
            .flatMapLatest { searchViewModel.foundCities(it) }
            .flowOn(Dispatchers.IO)
            .catch { onErrorReceived(it) }
            .collect { onDataReceived(it) }
    }

    private fun onDataReceived(items: List<CityItem>): Unit = citiesAdapter.submitList(items)

    private fun onErrorReceived(error: Throwable): Unit = when (error) {
        is UnknownHostException -> TODO()
        is HttpException -> TODO()
        is IOException -> TODO()
        else -> TODO()
    }
}