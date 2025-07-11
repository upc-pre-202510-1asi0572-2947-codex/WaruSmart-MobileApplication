package com.warusmart.iam.domain.model

/**
 * Data class representing the request to register a new user.
 */
data class SignUpRequest(
    val username: String,
    val password: String
)