package com.vorobyoff.weather.presentation.ui.extensions

import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun SearchView.inputStateFlow(): StateFlow<String> {
    val flow = MutableStateFlow("")

    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?) = true

        override fun onQueryTextChange(text: String): Boolean {
            flow.value = text
            return true
        }
    })

    return flow
}