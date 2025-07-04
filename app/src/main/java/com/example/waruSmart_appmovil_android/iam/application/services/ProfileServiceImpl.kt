package com.example.waruSmart_appmovil_android.iam.application.services

import android.util.Log
import com.example.waruSmart_appmovil_android.iam.domain.model.ProfileRequest
import com.example.waruSmart_appmovil_android.iam.domain.model.ProfileRequestUpdate
import com.example.waruSmart_appmovil_android.iam.domain.model.ProfileResponse
import com.example.waruSmart_appmovil_android.iam.infrastructure.ProfileService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileServiceImpl(private val profileService: ProfileService) {

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