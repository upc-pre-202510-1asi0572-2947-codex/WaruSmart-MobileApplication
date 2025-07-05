package com.example.warusmart.crops.domain.model.beans

data class Pest(
    val id: Int,
    val name: String,
    val description: String,
    val solution: String,
    val cropsPests: String
)