package com.warusmart.iam.domain.model

/**
 * Singleton object for managing user session data in memory.
 */
object SessionManager {
    var signInResponse: SignInResponse? = null

    /**
     * Returns the current user's ID if signed in.
     */
    val id: Int?
        get() = signInResponse?.id

    /**
     * Returns the current user's username if signed in.
     */
    val username: String?
        get() = signInResponse?.username

    /**
     * Returns the current user's authentication token if signed in.
     */
    val token: String?
        get() = signInResponse?.token

    var profileId: Int = 0

    /**
     * Clears the session data, effectively signing out the user.
     */
    fun signOut() {
        signInResponse = null
        profileId = 0
    }
}