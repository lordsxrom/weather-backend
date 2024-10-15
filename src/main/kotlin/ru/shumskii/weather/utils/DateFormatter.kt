package ru.shumskii.weather.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun formatDate(
    inputDateTimeString: String,
    inputPattern: String,
    outputPattern: String,
    ): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.US)
    val dateTime = LocalDateTime.parse(inputDateTimeString, inputFormatter)
    val outputFormatter = DateTimeFormatter.ofPattern(outputPattern, Locale.US)
    val formattedDateTimeString = dateTime.format(outputFormatter)
    return formattedDateTimeString
}