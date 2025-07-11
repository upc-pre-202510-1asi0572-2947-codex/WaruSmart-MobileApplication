package com.warusmart.crops.domain.model.beans

import java.util.Date

/**
 * Data class representing a care recommendation for a sowing.
 */
data class Cares(
    val id: Int,
    val suggestion: String,
    val date: Date,
    val sowing: Sowing
)