package com.vorobyoff.weather.presentation.delegate

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.Lifecycle.State
import androidx.lifecycle.LifecycleEventObserver
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ViewBindingDelegate<T : ViewBinding>(
    private val fragment: Fragment,
    private val bindingFactory: (View) -> T
) : ReadOnlyProperty<Fragment, T> {
    private var binding: T? = null

    init {
        fragment.lifecycle.addObserver(LifecycleEventObserver { owner, event ->
            if (event == Event.ON_CREATE) {
                fragment.viewLifecycleOwnerLiveData.observe(owner) { lifecycleOwner ->
                    lifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
                        if (event == Event.ON_DESTROY) binding = null
                    })
                }
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val binding = binding
        if (binding != null) return binding
        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(State.INITIALIZED)) throw IllegalStateException("Should not initialize binding when fragment view are destroyed")
        return bindingFactory.invoke(thisRef.requireView()).also { this.binding = it }
    }
}