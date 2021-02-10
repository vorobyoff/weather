package com.vorobyoff.weather.presentation.ui

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.vorobyoff.weather.databinding.ActivityAppBinding
import com.vorobyoff.weather.databinding.ActivityAppBinding.inflate
import com.vorobyoff.weather.presentation.ui.extensions.checkSelfPermissionCompat
import com.vorobyoff.weather.presentation.ui.fragments.HostFragment
import com.vorobyoff.weather.presentation.ui.viewmodels.SharedViewModelImp.Factory
import com.vorobyoff.weather.presentation.ui.viewmodels.base.SharedViewModel

class AppActivity : FragmentActivity() {
    companion object {
        private const val SAVED_STATE_KEY = "saved_state_key"
    }

    private val binding: ActivityAppBinding by lazy { inflate(layoutInflater) }
    private val containerId: Int get() = binding.activityHostContainer.id
    private val sharedViewModel: SharedViewModel by lazy {
        ViewModelProvider(this, Factory(getFusedLocationProviderClient(this))).get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) replace()
        if (checkSelfPermissionCompat(ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED)
            sharedViewModel.findCityByGeolocation()
    }

    private fun replace(): Unit = supportFragmentManager.commitNow {
        val clazz: Class<out Fragment> = HostFragment::class.java
        replace(containerId, clazz, null, clazz.canonicalName)
        setReorderingAllowed(true)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        val savedFragment: Fragment? = supportFragmentManager.getFragment(savedInstanceState, SAVED_STATE_KEY)
        savedFragment?.let { supportFragmentManager.commit { attach(it) } } ?: replace()
    }

    override fun onSaveInstanceState(state: Bundle) {
        super.onSaveInstanceState(state)
        val currentFragment: Fragment? = supportFragmentManager.findFragmentById(containerId)
        currentFragment?.let { supportFragmentManager.putFragment(state, SAVED_STATE_KEY, it) }
    }
}