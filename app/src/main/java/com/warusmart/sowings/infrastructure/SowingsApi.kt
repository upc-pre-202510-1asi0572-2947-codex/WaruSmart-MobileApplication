// SowingsApi.kt
package com.warusmart.sowings.infrastructure

import com.warusmart.sowings.domain.model.Crop
import com.warusmart.sowings.domain.model.SowingDos
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * API interface for sowings and crops endpoints.
 * Defines network operations for retrieving and adding sowings and crops.
 */
interface SowingsApi {
    /**
     * Gets all crops from the API.
     */
    @GET("crops-management/crops")
    suspend fun getAllCrops(): List<Crop>

    /**
     * Gets a crop by its ID from the API.
     */
    @GET("crops-management/crops/{id}")
    suspend fun getCropById(@Path("id") id: Int): Crop

    /**
     * Adds a new sowing record to the API.
     */
    @POST("crops-management/sowings")
    suspend fun addSowing(@Body sowing: SowingDos)

    /**
     * Gets all sowings for a user from the API.
     */
    @GET("crops-management/sowings/user/{userId}")
    suspend fun getSowingsByUserId(@Path("userId") userId: Int): List<SowingDos>

    /**
     * Gets a sowing by its ID from the API.
     */
    @GET("crops-management/sowings/{id}")
    suspend fun getSowingById(@Path("id") id: Int): SowingDos
}