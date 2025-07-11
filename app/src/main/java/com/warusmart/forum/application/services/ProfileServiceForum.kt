package com.warusmart.forum.application.services

import android.util.Log
import com.warusmart.iam.domain.model.SessionManager
import com.warusmart.forum.infrastructure.ForumApi
import com.warusmart.iam.domain.model.ProfileResponse
import io.github.cdimascio.dotenv.dotenv
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Service for handling forum user profiles, including API communication.
 */
class ProfileServiceForum {
    val dotenv = dotenv(){
        directory = "/assets"
        filename = "env"
    }

    private val api: ForumApi
    private val token = SessionManager.token

    init {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder: Request.Builder = original.newBuilder()
                .header("Authorization", "Bearer $token")
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(dotenv["API_URL"])
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(ForumApi::class.java)
    }

    /**
     * Retrieves a user profile by its ID from the API.
     */
    suspend fun getProfileById(profileId:Int): ProfileResponse? {
        return try {
            val profile = api.getProfileById(profileId)
            Log.d("ProfilesService","Raw JSON response: $profile")
            profile
        }catch (e: Exception) {
            Log.e("ProfilesService", "Error: ${e.message}")
            null
        }
    }

    /**
     * Retrieves all user profiles from the API.
     */
    suspend fun getAllProfiles():List<ProfileResponse>? {
        return try {
            val profiles = api.getAllProfiles()
            Log.d("ProfilesService","Raw JSON response: $profiles")
            profiles
        }catch (e: Exception) {
            Log.e("ProfilesService", "Error: ${e.message}")
            null
        }
    }
}