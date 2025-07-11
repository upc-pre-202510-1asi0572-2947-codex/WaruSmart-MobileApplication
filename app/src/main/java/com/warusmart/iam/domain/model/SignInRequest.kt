package com.warusmart.iam.domain.model

/**
 * Data class representing the request to sign in a user.
 */
data class SignInRequest(
    val username: String,
    val password: String
)
