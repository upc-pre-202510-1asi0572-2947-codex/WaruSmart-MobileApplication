package com.example.waruSmart_appmovil_android.crops.domain.model.beans

data class Pest(
    val id: Int,
    val name: String,
    val description: String,
    val solution: String,
    val cropsPests: String
)