package com.example.waruSmart_appmovil_android.sowingsManagement.application.services

import android.content.Context
import android.util.Log
import com.example.waruSmart_appmovil_android.sowingsManagement.domain.model.Crop
import com.example.waruSmart_appmovil_android.sowingsManagement.domain.model.SowingDos
import com.example.waruSmart_appmovil_android.sowingsManagement.infrastructure.SowingsApi
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketException

/**
 * Service for handling sowings-related operations and API calls.
 */
class SowingsService(context: Context, private val bearerToken: String) {

    private val api: SowingsApi

    init {
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
            .baseUrl("https://waru-smart-fzg6c7htcadxb9g5.canadacentral-01.azurewebsites.net/api/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(SowingsApi::class.java)
        Log.d("SowingsService", "SowingsService initialized")
    }

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

    // Add a new sowing record
    suspend fun addSowingRemote(sowing: SowingDos) {
        api.addSowing(sowing)
    }
}
