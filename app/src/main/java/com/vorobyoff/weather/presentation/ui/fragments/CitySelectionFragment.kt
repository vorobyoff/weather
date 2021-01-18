package com.vorobyoff.weather.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
import com.vorobyoff.weather.databinding.FragmentCitySelectionBinding
import com.vorobyoff.weather.databinding.FragmentCitySelectionBinding.inflate
import com.vorobyoff.weather.presentation.adapter.CityListAdapter
import com.vorobyoff.weather.presentation.ui.addSystemWindowInsetToMargin
import com.vorobyoff.weather.presentation.ui.addSystemWindowInsetToPadding

class CitySelectionFragment : Fragment() {
    private var _binding: FragmentCitySelectionBinding? = null
    private val binding: FragmentCitySelectionBinding get() = _binding!!
    private val selectionViewModel: SelectionViewModel by viewModels()
    private val citiesAdapter: CityListAdapter by lazy {
        CityListAdapter { Snackbar.make(binding.root, it.name, LENGTH_SHORT).show() }
    }

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        selectionViewModel.topCities()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View {
        _binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.searchFab.addSystemWindowInsetToMargin(bottom = true)
        binding.root.addSystemWindowInsetToPadding(top = true)
        customizeList()
        selectionViewModel.topCities.observe(viewLifecycleOwner) { citiesAdapter.submitList(it) }

        selectionViewModel.error.observe(viewLifecycleOwner) {
            Snackbar.make(binding.root, "Error!", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun customizeList(): Unit = with(binding.citiesRv) {
        layoutManager = LinearLayoutManager(context, VERTICAL, false)
        addSystemWindowInsetToPadding(bottom = true)
        adapter = citiesAdapter
        setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}