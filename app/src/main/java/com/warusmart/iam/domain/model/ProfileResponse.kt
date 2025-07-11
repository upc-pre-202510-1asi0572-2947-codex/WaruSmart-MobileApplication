package com.warusmart.iam.domain.model

/**
 * Represents a response containing user profile information.
 */
data class ProfileResponse(
    val id: Int,
    val fullName: String,
    val email: String,
    val countryId: Int,
    val cityId: Int,
    val subscriptionId: Int,
    val userId: Int
)
