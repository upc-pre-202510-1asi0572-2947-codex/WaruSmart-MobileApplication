package com.warusmart.shared.domain.model.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Data class representing a product entity for the database.
 * Stores product attributes such as id, name, sowingId, and other details.
 */
@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val sowingId: Int,
    var name: String,
    var type: String,
    var quantity: Float,
    var date: Date
)