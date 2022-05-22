package com.dicoding.storyapp.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateHelper {

    fun String.withDateFormat(): String {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val date = format.parse(this) as Date
        return DateFormat.getDateInstance(DateFormat.FULL).format(date)
    }
}