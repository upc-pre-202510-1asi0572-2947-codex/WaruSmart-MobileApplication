package com.warusmart.crops.interfaces.adapters

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Gson TypeAdapter for serializing and deserializing Date objects.
 */
class CustomDateTypeAdapter : TypeAdapter<Date>() {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)

    /**
     * Serializes a Date object to JSON.
     */
    override fun write(out: JsonWriter, value: Date?) {
        if (value == null) {
            out.nullValue()
        } else {
            out.value(dateFormat.format(value))
        }
    }

    /**
     * Deserializes a Date object from JSON.
     */
    override fun read(`in`: JsonReader): Date? {
        return try {
            dateFormat.parse(`in`.nextString())
        } catch (e: ParseException) {
            null
        }
    }
}