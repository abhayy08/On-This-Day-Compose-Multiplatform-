package com.abhay.onthisday.presentation.util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun formatIsoTimeToDisplay(isoTime: String, timeZone: TimeZone = TimeZone.currentSystemDefault()): String {
    val instant = Instant.parse(isoTime)
    val localDateTime = instant.toLocalDateTime(timeZone)

    val month = localDateTime.month.name.lowercase().replaceFirstChar { it.uppercase() }.take(3)
    val day = localDateTime.dayOfMonth.toString().padStart(2, '0')
    val year = localDateTime.year

    val hour = if (localDateTime.hour % 12 == 0) 12 else localDateTime.hour % 12
    val minute = localDateTime.minute.toString().padStart(2, '0')
    val amPm = if (localDateTime.hour < 12) "AM" else "PM"

    return "$month $day, $year $hour:$minute $amPm"
}
