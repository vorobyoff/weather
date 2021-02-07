package com.vorobyoff.weather.presentation.ui

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.annotation.SuppressLint
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.vorobyoff.weather.R.id.activity_host_container
import com.vorobyoff.weather.R.layout.activity_app
import com.vorobyoff.weather.presentation.ui.extensions.checkSelfPermissionCompat
import com.vorobyoff.weather.presentation.ui.fragments.HostFragment
import com.vorobyoff.weather.presentation.ui.viewmodels.SharedViewModelImp.Factory
import com.vorobyoff.weather.presentation.ui.viewmodels.base.SharedViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

class AppActivity : FragmentActivity(activity_app) {
    companion object {
        private const val SAVED_STATE_KEY = "saved_state_key"
    }

    private val sharedViewModel: SharedViewModel by viewModels {
        Factory(getFusedLocationProviderClient(this))
    }

    @ExperimentalCoroutinesApi
    @SuppressLint("missingPermission")
    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        if (state == null) replace()
        if (checkSelfPermissionCompat(ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED) {
            sharedViewModel.findCityByGeolocation()
        }
    }

    private fun replace(): Unit = supportFragmentManager.commitNow {
        val fragmentClass = HostFragment::class.java
        replace(activity_host_container, fragmentClass, null, fragmentClass.canonicalName)
        setReorderingAllowed(true)
    }

    override fun onRestoreInstanceState(state: Bundle) {
        val savedFragment = supportFragmentManager.getFragment(state, SAVED_STATE_KEY)!!
        supportFragmentManager.commit { attach(savedFragment) }
    }

    override fun onSaveInstanceState(state: Bundle) {
        super.onSaveInstanceState(state)
        val currentFragment = supportFragmentManager.findFragmentById(activity_host_container)!!
        supportFragmentManager.putFragment(state, SAVED_STATE_KEY, currentFragment)
    }
}