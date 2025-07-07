package com.warusmart.crops.domain.model.beans

/**
 * Data class representing a pest entity with its properties.
 */
data class Pest(
    val id: Int,
    val name: String,
    val description: String,
    val solution: String,
    val cropsPests: String
)