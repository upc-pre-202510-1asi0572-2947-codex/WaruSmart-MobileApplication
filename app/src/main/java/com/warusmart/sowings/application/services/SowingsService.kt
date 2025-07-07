package com.warusmart.sowings.application.services

import android.content.Context
import android.util.Log
import com.warusmart.iam.infrastructure.RetrofitClient
import com.warusmart.sowings.domain.model.Crop
import com.warusmart.sowings.domain.model.SowingDos
import com.warusmart.sowings.infrastructure.SowingsApi
import io.github.cdimascio.dotenv.dotenv
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketException

/**
 * Service for handling sowings-related operations and API calls.
 * Provides methods to interact with sowings and crops via network.
 */
class SowingsService(context: Context, private val bearerToken: String) {

    private val api: SowingsApi

    /**
     * Initialisace the retrofit client
     */
    init {
        val dotenv = dotenv() {
            directory = "/assets"
            filename = "env"
        }
        val BASE_URL = RetrofitClient.dotenv["API_URL"]

        Log.d("SowingsService", "Initializing SowingsService")
        if (bearerToken.isEmpty()) {
            throw IllegalArgumentException("Bearer token is missing or empty")
        }

        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder: Request.Builder = original.newBuilder()
                .header("Authorization", "Bearer $bearerToken")
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(SowingsApi::class.java)
        Log.d("SowingsService", "SowingsService initialized")
    }

    /**
     * Gets a crop by its ID from the API.
     * Returns a Crop object or null if not found or on error.
     */
    // Get a crop by its ID
    suspend fun getCropById(id: Int): Crop? {
        Log.d("SowingsService", "Fetching crop with ID: $id")
        return try {
            val crop = api.getCropById(id)
            Log.d("SowingsService", "Raw JSON response: $crop")
            crop
        } catch (e: SocketException) {
            Log.e("SowingsService", "SocketException: ${e.message}")
            null
        }
    }

    /**
     * Gets all crops from the API.
     * Returns a list of Crop objects.
     */
    // Get all crops
    suspend fun getAllCrops(): List<Crop> {
        Log.d("SowingsService", "Fetching all crops")
        return try {
            val crops = api.getAllCrops()
            Log.d("SowingsService", "Raw JSON response: $crops")
            crops
        } catch (e: SocketException) {
            Log.e("SowingsService", "SocketException: ${e.message}")
            emptyList()
        }
    }

    /**
     * Gets sowings for a specific user by user ID from the API.
     * Returns a list of SowingDos objects.
     */
    // Get sowings for a specific user by user ID
    suspend fun getSowingsByUserId(userId: Int): List<SowingDos> {
        Log.d("SowingsService", "Fetching sowings for user ID: $userId")
        return try {
            val sowings = api.getSowingsByUserId(userId)
            Log.d("SowingsService", "Sowings obtained: $sowings")
            sowings
        } catch (e: SocketException) {
            Log.e("SowingsService", "SocketException: ${e.message}")
            emptyList()
        } catch (e: Exception) {
            Log.e("SowingsService", "Error fetching sowings: ${e.message}")
            emptyList()
        }
    }

    /**
     * Adds a new sowing record to the API.
     */
    // Add a new sowing record
    suspend fun addSowingRemote(sowing: SowingDos) {
        api.addSowing(sowing)
    }
}
