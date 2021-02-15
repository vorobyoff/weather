package com.vorobyoff.weather.domain.entity

import java.util.*

class IsoTimeFormatter(locale: Locale = Locale.ROOT) : IsoDateTimeFormatter(locale) {

    override fun parse(input: String): String {
        calendar.time = getDateFromInput(input)
        val hours: Int = calendar[Calendar.HOUR]
        val minutes: Int = calendar[Calendar.MINUTE]

        return when {
            hours < 10 && minutes < 10 -> "0$hours:0$minutes"
            minutes < 10 -> "$hours:0$minutes"
            hours < 10 -> "0$hours:$minutes"
            else -> "$hours:$minutes"
        }
    }
}