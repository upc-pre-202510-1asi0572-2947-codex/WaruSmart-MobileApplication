package com.warusmart.stadistics.domain.model

/**
 * Data class representing a control record for statistics.
 * Contains control attributes such as id, sowingId, and date.
 *
 * Example:
 *  "id": 37,
 *     "sowingId": 10,
 *     "date": "2024-06-28T13:05:51.336324",
 *     "sowingCondition": "Dry",
 *     "stemCondition": "Dry",
 *     "soilMoisture": "Dry"
 */
class Control(
    var id: Int,
    var sowingId: Int,
    var date: String,
    var sowingCondition: String,
    var stemCondition: String,
    var soilMoisture: String
) {
}