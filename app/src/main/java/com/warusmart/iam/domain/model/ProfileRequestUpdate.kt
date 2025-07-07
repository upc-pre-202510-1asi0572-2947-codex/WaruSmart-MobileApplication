package com.warusmart.iam.domain.model

/**
 * Data class representing the request to update a user profile.
 */
data class ProfileRequestUpdate (
    val fullName: String,
    val emailAddress: String,
    val countryId: Int,
    val cityId: Int,
    val subscriptionId: Int
)
