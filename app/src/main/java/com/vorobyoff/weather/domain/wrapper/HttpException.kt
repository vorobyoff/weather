package com.vorobyoff.weather.domain.wrapper

class HttpException(
    val statusMessage: String?,
    val statusCode: Int,
    cause: Throwable?,
    val url: String?
) : Exception(null, cause)