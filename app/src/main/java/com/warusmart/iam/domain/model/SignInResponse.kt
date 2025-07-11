package com.warusmart.iam.domain.model

/**
 * Data class representing the response after a successful sign in.
 */
data class SignInResponse(
    val id: Int,
    val username: String,
    val token: String
)