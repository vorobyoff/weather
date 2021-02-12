package com.vorobyoff.weather.presentation.ui.extensions

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.vorobyoff.weather.presentation.delegate.FragmentViewBindingDelegate

fun <T : ViewBinding> Fragment.viewBinding(factory: (View) -> T): FragmentViewBindingDelegate<T> =
    FragmentViewBindingDelegate(this, factory)

fun Fragment.checkSelfPermissionCompat(permission: String): Int =
    requireActivity().checkSelfPermissionCompat(permission)

fun Fragment.shouldShowRequestPermissionRationaleCompat(permission: String): Boolean =
    requireActivity().shouldShowRequestPermissionRationaleCompat(permission)

fun Fragment.requestPermissionsCompat(vararg permissions: String, code: Int): Unit =
    requireActivity().requestPermissionsCompat(*permissions, code = code)