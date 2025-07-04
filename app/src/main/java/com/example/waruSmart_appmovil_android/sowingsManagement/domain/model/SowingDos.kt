// SowingDos.kt
package com.example.waruSmart_appmovil_android.sowingsManagement.domain.model

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
