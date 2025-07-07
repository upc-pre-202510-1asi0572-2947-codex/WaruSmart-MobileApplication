// DateConverter.java
package Entities

import androidx.room.TypeConverter
import java.util.Date

/**
 * Object for converting between Date and Long for Room database.
 */
object DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}