package com.example.warusmart.iam.domain.model

object SessionManager {
    var signInResponse: SignInResponse? = null

    val id: Int?
        get() = signInResponse?.id

    val username: String?
        get() = signInResponse?.username

    val token: String?
        get() = signInResponse?.token

    var profileId: Int = 0

    fun signOut() {
        signInResponse = null
        profileId = 0
    }
}