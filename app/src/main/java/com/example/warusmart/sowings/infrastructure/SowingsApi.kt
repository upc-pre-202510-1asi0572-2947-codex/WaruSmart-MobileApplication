// SowingsApi.kt
package com.example.warusmart.sowings.infrastructure

import com.example.warusmart.sowings.domain.model.Crop
import com.example.warusmart.sowings.domain.model.SowingDos
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// API for sowings and crops
interface SowingsApi {
    // Get all crops
    @GET("crops-management/crops")
    suspend fun getAllCrops(): List<Crop>

    // Get a crop by its ID
    @GET("crops-management/crops/{id}")
    suspend fun getCropById(@Path("id") id: Int): Crop

    // Add a new sowing
    @POST("crops-management/sowings")
    suspend fun addSowing(@Body sowing: SowingDos)

    // Get all sowings for a user
    @GET("crops-management/sowings/{userId}/user")
    suspend fun getSowingsByUserId(@Path("userId") userId: Int): List<SowingDos>

}