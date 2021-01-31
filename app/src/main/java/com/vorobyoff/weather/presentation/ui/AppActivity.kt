package com.vorobyoff.weather.presentation.ui

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.annotation.SuppressLint
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import android.location.LocationManager
import android.location.LocationManager.GPS_PROVIDER
import android.location.LocationManager.NETWORK_PROVIDER
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.vorobyoff.weather.R.layout.activity_app
import com.vorobyoff.weather.presentation.ui.extensions.checkSelfPermissionCompat
import com.vorobyoff.weather.presentation.ui.extensions.requestPermissionsCompat
import com.vorobyoff.weather.presentation.ui.viewmodels.AppViewModel
import com.vorobyoff.weather.presentation.ui.viewmodels.AppViewModelFactory

class AppActivity : FragmentActivity() {
    companion object {
        const val PERMISSION_REQUEST_LOCATION = 0
    }

    private val appViewModel: AppViewModel by viewModels { AppViewModelFactory() }
    private val locationClient: FusedLocationProviderClient by lazy {
        getFusedLocationProviderClient(this)
    }

    override fun onCreate(savedInstansState: Bundle?) {
        super.onCreate(savedInstansState)
        setContentView(activity_app)
        navigateTo()
    }

    @SuppressLint("missingPermission")
    private fun navigateTo() {
        if (checkSelfPermissionCompat(ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED) {
            if (isLocationEnabled()) locationClient.lastLocation.addOnCompleteListener {
                val location: Location = it.result
                appViewModel.getLocationKey(location.longitude, location.latitude)
            } else TODO()
        } else requestPermissionsCompat(ACCESS_COARSE_LOCATION, code = PERMISSION_REQUEST_LOCATION)
    }

    private fun isLocationEnabled(): Boolean {
        val manager = getSystemService(LOCATION_SERVICE) as LocationManager
        return manager.run { isProviderEnabled(GPS_PROVIDER) || isProviderEnabled(NETWORK_PROVIDER) }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) navigateTo()
                else TODO()
            }
            else -> TODO("Ignore this case")
        }
    }
}