// Control.kt
package com.warusmart.shared.domain.model.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Data class representing a control entity for the database.
 * Stores control attributes such as id, sowingId, date, and conditions.
 */
@Entity(tableName = "controls")
data class Control(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val sowingId: Int,
    var sowingCondition: String,
    var stemCondition: String,
    var sowingSoilMoisture: String,
    val date: Date
)