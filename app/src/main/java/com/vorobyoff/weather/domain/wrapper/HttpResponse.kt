package com.vorobyoff.weather.domain.wrapper

interface HttpResponse {
    val statusCode: Int
    val statusMessage: String?
    val url: String?
}