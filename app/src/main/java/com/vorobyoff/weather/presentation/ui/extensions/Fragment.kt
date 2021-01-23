package com.vorobyoff.weather.presentation.ui.extensions

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.vorobyoff.weather.presentation.delegate.ViewBindingDelegate

fun <T : ViewBinding> Fragment.viewBinding(factory: (View) -> T) =
    ViewBindingDelegate(this, factory)