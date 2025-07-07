package com.warusmart.iam.application.services

import android.util.Log
import com.warusmart.iam.domain.model.ProfileRequest
import com.warusmart.iam.domain.model.ProfileRequestUpdate
import com.warusmart.iam.domain.model.ProfileResponse
import com.warusmart.iam.infrastructure.ProfileService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Implementation of profile-related operations using ProfileService.
 * Provides methods to save, retrieve, and update user profiles.
 */
class ProfileServiceImpl(private val profileService: ProfileService) {

    /**
     * Saves a new user profile and returns the result via callback.
     */
    fun saveProfile(token: String, request: ProfileRequest, callback: (ProfileResponse?) -> Unit) {
        Log.d("Data for profile to create", request.toString())
        val call = profileService.saveProfile("Bearer $token", request)
        call.enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

    /**
     * Retrieves all user profiles and returns the result via callback.
     */
    fun getAllProfiles(token: String, callback: (List<ProfileResponse>?) -> Unit) {
        val call = profileService.getAllProfiles("Bearer $token")
        call.enqueue(object : Callback<List<ProfileResponse>> {
            override fun onResponse(call: Call<List<ProfileResponse>>, response: Response<List<ProfileResponse>>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<ProfileResponse>>, t: Throwable) {
                callback(null)
            }
        })
    }

    /**
     * Retrieves a user profile by its ID and returns the result via callback.
     */
    fun getProfileById(token: String, profileId: Int, callback: (ProfileResponse?) -> Unit) {
        val call = profileService.getProfileById("Bearer $token", profileId)
        call.enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

    /**
     * Updates an existing user profile and returns the result via callback.
     */
    fun updateProfile(token: String, id: Int, updatedProfile: ProfileRequestUpdate, callback: (ProfileResponse?) -> Unit) {
        val call = profileService.updateProfile(token, id, updatedProfile)
        call.enqueue(object : retrofit2.Callback<ProfileResponse> {
            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                callback(null)
            }
        })
    }
}