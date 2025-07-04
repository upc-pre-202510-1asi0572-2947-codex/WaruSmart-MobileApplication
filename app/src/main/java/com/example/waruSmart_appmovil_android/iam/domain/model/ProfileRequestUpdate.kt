package com.example.waruSmart_appmovil_android.iam.domain.model

data class ProfileRequestUpdate (
    val fullName: String,
    val emailAddress: String,
    val countryId: Int,
    val cityId: Int,
    val subscriptionId: Int
)
