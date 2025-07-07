// app/src/main/java/com/example/chaquitaclla_appmovil_android/crops_details/beans/Disease.kt
package com.warusmart.crops.domain.model.beans

/**
 * Data class representing a disease entity with its properties and related crops.
 */
data class Disease(
    val id: Int,
    val name: String,
    val description: String,
    val solution: String,
    val crops: List<Crop> // Assuming Crop is another data class
)