package com.warusmart.iam.infrastructure

import com.warusmart.iam.domain.model.ProfileRequest
import com.warusmart.iam.domain.model.ProfileRequestUpdate
import com.warusmart.iam.domain.model.ProfileResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Retrofit service interface for user profile endpoints.
 */
interface ProfileService {
    /**
     * Saves a new user profile.
     */
    @POST("profiles")
    fun saveProfile(@Header("Authorization") token: String, @Body request: ProfileRequest): Call<ProfileResponse>

    /**
     * Retrieves all user profiles.
     */
    @GET("profiles")
    fun getAllProfiles(@Header("Authorization") token: String): Call<List<ProfileResponse>>

    /**
     * Retrieves a user profile by its ID.
     */
    @GET("profiles/{profileId}")
    fun getProfileById(@Header("Authorization") token: String, profileId: Int): Call<ProfileResponse>

    /**
     * Updates an existing user profile.
     */
    @PUT("profiles/{profileId}")
    fun updateProfile(
        @Header("Authorization") token: String,
        @Path("profileId") id: Int,
        @Body updatedProfile: ProfileRequestUpdate
    ): Call<ProfileResponse>
}