// app/src/main/java/com/example/chaquitaclla_appmovil_android/crops_details/interfaces/CropCaresApi.kt
package com.example.waruSmart_appmovil_android.crops_details.infrastructure

import com.example.waruSmart_appmovil_android.crops_details.domain.model.beans.Cares
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit API interface for crop cares endpoints.
 */
interface CropCaresApi {
    // Gets a care by its ID
    @GET("crops-management/crops/cares/{id}")
    suspend fun getCareById(@Path("id") id: Int): Cares

    // Gets all cares for a specific crop
    @GET("crops-management/crops/{cropId}/cares")
    suspend fun getCaresByCropId(@Path("cropId") cropId: Int): List<Cares>
}