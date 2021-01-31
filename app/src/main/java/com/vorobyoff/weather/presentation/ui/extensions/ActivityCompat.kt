package com.vorobyoff.weather.presentation.ui.extensions

import androidx.core.app.ActivityCompat.*
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.checkSelfPermissionCompat(permission: String): Int =
    checkSelfPermission(this, permission)

fun FragmentActivity.shouldShowRequestPermissionRationaleCompat(permission: String): Boolean =
    shouldShowRequestPermissionRationale(this, permission)

fun FragmentActivity.requestPermissionsCompat(vararg permissions: String, code: Int): Unit =
    requestPermissions(this, permissions, code)