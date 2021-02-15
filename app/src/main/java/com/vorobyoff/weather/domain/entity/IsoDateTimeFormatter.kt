package com.vorobyoff.weather.domain.entity

import java.text.SimpleDateFormat
import java.util.*

abstract class IsoDateTimeFormatter(protected val locale: Locale) {
    companion object {
        private const val ISO_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"
    }

    private val format = SimpleDateFormat(ISO_PATTERN, locale)
    protected val calendar: Calendar by lazy { Calendar.getInstance() }

    abstract fun parse(input: String): String

    protected fun getDateFromInput(input: String): Date = format.parse(input)
        ?: throw IllegalArgumentException("Input doesn't comply with ISO 8601: $input")
}