package com.example.waruSmart_appmovil_android.iam.domain.model

data class ProfileRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val cityId: Int,
    val subscriptionId: Int,
    val countryId: Int,
    val userId: Int
)
