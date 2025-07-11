package com.warusmart.sowings.domain.model

import androidx.room.TypeConverter
import java.util.Date

/**
 * Converter class for Room database to handle Date objects.
 * Provides methods to convert between Date and Long for database storage.
 */
class DateConverter {
    /**
     * Converts a timestamp to a Date object.
     */
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    /**
     * Converts a Date object to a timestamp.
     */
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}