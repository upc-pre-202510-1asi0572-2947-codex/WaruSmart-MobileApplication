package com.warusmart.iam.domain.model

/**
 * Data class representing the request to create a user profile.
 */
data class ProfileRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val cityId: Int,
    val subscriptionId: Int,
    val countryId: Int,
    val userId: Int,
    val role: String
)
