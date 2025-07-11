package com.warusmart.sowings.domain.model

/**
 * Class representing a device entity with its properties.
 */
class Device(
    val id: Int,
    val name: String,
    val deviceType: String,
    val deviceId: String,
    val status: String,
    val lastSyncDate: String,
    val location: String,
    val sowingId: Int,
    val humidity: Double,
    val temperature: Double,
    val soilMoisture: Double,
    val timeSinceLastSync: String
){

}