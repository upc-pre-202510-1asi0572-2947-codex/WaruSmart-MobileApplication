package com.warusmart.forum.domain.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Utility for formatting dates in the forum with a standard format.
 */
class DateFormat {
    companion object {
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
        /**
         * Formats a Date object to String with the defined format.
         */
        fun format(date: Date): String {
            return dateFormat.format(date)
        }
    }
}