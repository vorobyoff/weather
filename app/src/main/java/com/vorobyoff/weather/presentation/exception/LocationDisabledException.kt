package com.vorobyoff.weather.presentation.exception

class LocationDisabledException : Exception() {
    override val message: String = "Location disabled or GooglePlay services are not installed"
}