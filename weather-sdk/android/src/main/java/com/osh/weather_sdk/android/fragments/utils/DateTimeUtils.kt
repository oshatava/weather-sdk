package com.osh.weather_sdk.android.fragments.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField

private val timeFormatter = DateTimeFormatterBuilder()
    .appendValue(ChronoField.HOUR_OF_DAY, 2)
    .appendLiteral(':')
    .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
    .toFormatter()


internal fun Long.toLocalDateTime(): LocalDateTime{
    return Instant.ofEpochSecond( this )
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
}

internal fun LocalDateTime.formatAsTime(): String{
    return this.format(timeFormatter)
}
