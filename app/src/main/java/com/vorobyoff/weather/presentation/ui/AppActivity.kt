package com.vorobyoff.weather.presentation.ui

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.vorobyoff.weather.R
import com.vorobyoff.weather.R.layout.activity_app
import com.vorobyoff.weather.presentation.ui.extensions.awaitLastLocation
import com.vorobyoff.weather.presentation.ui.extensions.checkSelfPermissionCompat
import com.vorobyoff.weather.presentation.ui.extensions.requestPermissionsCompat
import com.vorobyoff.weather.presentation.ui.viewmodels.AppViewModel
import com.vorobyoff.weather.presentation.ui.viewmodels.AppViewModelFactory
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class AppActivity : FragmentActivity() {
    companion object {
        const val PERMISSION_REQUEST_LOCATION = 0
    }

    private val appViewModel: AppViewModel by viewModels { AppViewModelFactory() }
    private val locationClient: FusedLocationProviderClient
        get() = getFusedLocationProviderClient(this)


    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstansState: Bundle?) {
        super.onCreate(savedInstansState)
        setContentView(activity_app)
        if (checkSelfPermissionCompat(ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED) sendLocation()
        else requestPermissionsCompat(ACCESS_COARSE_LOCATION, code = PERMISSION_REQUEST_LOCATION)
    }

    @ExperimentalCoroutinesApi
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) sendLocation()
                else TODO("Open fragment with cities list")
            }
            else -> TODO("Ignore this case")
        }
    }

    @ExperimentalCoroutinesApi
    private fun sendLocation(): Job = lifecycleScope.launchWhenCreated {
        val lastLocation: Location =
            withContext(lifecycleScope.coroutineContext + Default) { locationClient.awaitLastLocation() }
        appViewModel.getLocationKey(lat = lastLocation.latitude, lon = lastLocation.longitude)
    }
}