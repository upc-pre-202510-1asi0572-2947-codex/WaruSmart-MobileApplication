// app/src/main/java/com/example/chaquitaclla_appmovil_android/crops_details/interfaces/ControlApi.kt
package com.warusmart.crops.infrastructure

import com.warusmart.shared.domain.model.Entities.Control
import retrofit2.http.*

/**
 * Retrofit API interface for control endpoints.
 */
interface ControlApi {
    @GET("crops-management/sowings/{sowingId}/controls")
    suspend fun getControlsBySowingId(@Path("sowingId") sowingId: Int): List<Control>

    @GET("crops-management/sowings/{sowingId}/controls/{controlId}")
    suspend fun getControlById(@Path("sowingId") sowingId: Int, @Path("controlId") controlId: Int): Control

    @DELETE("crops-management/sowings/{sowingId}/controls/{controlId}")
    suspend fun deleteControl(@Path("sowingId") sowingId: Int, @Path("controlId") controlId: Int)

    @GET("crops-management/sowings/controls")
    suspend fun getAllControls(): List<Control>

    @PUT("crops-management/sowings/{sowingId}/controls/{controlId}")
    suspend fun updateControl(@Path("sowingId") sowingId: Int, @Path("controlId") controlId: Int, @Body control: Control)

    @POST("crops-management/sowings/{sowingId}/controls")
    suspend fun createControl(@Path("sowingId") sowingId: Int, @Body control: Control): Control
}