package com.example.waruSmart_appmovil_android.iam.domain.model

data class ProfileResponse(
    val id: Int,
    val fullName: String,
    val email: String,
    val countryId: Int,
    val cityId: Int,
    val subscriptionId: Int,
    val userId: Int
)
