package com.example.warusmart.sowings.beans

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Data class for deserializing date strings from JSON responses.
 * Used by Gson to convert date strings to Date objects.
 */
class DateDeserializer : JsonDeserializer<Date> {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Date {
        return dateFormat.parse(json.asString) ?: throw IllegalArgumentException("Invalid date format")
    }
}