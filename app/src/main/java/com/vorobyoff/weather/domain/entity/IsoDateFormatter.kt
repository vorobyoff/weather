package com.vorobyoff.weather.domain.entity

import java.util.*

class IsoDateFormatter(locale: Locale = Locale.ROOT) : IsoDateTimeFormatter(locale) {

    override fun parse(input: String): String {
        calendar.time = getDateFromInput(input)

        return buildString {
            append("${calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, locale)} ")
            append("${calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, locale)} ")
            append("${calendar[Calendar.DAY_OF_MONTH]} ")
            append("${calendar[Calendar.YEAR]}")
        }
    }
}