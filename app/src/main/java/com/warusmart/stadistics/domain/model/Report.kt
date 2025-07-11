package com.warusmart.stadistics.domain.model

/**
 * Data class representing a report for a specific zone.
 * Contains average values for temperature, humidity, and soil moisture, along with the zone name.
 */
data class Report(
    val zone: String,
    val avgTemperature: Float,
    val avgHumidity: Float,
    val avgSoilMoisture: Float
)