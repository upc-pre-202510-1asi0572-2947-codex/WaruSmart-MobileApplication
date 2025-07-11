// SowingDos.kt
package com.warusmart.sowings.domain.model

/**
 * Data class representing a sowing record with its main attributes.
 * Used to store and transfer sowing information in the domain layer.
 */
data class SowingDos(
    val id: Int? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val areaLand: Int,
    val status: Boolean? = null,
    val phenologicalPhase: Int? = null,
    val cropId: Int,
    val phenologicalPhaseName: String? = null,
    val userId: Int
)
