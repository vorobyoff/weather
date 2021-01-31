package com.vorobyoff.weather.presentation.ui.extensions

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.vorobyoff.weather.presentation.delegate.FragmentViewBindingDelegate

fun <T : ViewBinding> Fragment.viewBinding(factory: (View) -> T) =
    FragmentViewBindingDelegate(this, factory)