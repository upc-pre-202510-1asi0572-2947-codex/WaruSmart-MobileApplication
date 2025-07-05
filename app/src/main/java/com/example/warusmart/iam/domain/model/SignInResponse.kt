package com.example.warusmart.iam.domain.model

data class SignInResponse(
    val id: Int,
    val username: String,
    val token: String
)