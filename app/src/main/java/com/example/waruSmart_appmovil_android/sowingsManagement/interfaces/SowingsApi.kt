// SowingsApi.kt
package com.example.waruSmart_appmovil_android.sowingsManagement.interfaces

import com.example.waruSmart_appmovil_android.sowingsManagement.beans.Crop
import com.example.waruSmart_appmovil_android.sowingsManagement.beans.SowingDos
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SowingsApi {
    @GET("crops-management/crops")
    suspend fun getAllCrops(): List<Crop>

    @GET("crops-management/crops/{id}")
    suspend fun getCropById(@Path("id") id: Int): Crop

    @POST("crops-management/sowings")
    suspend fun addSowing(@Body sowing: SowingDos)

    @GET("crops-management/sowings/{userId}/user")
    suspend fun getSowingsByUserId(@Path("userId") userId: Int): List<SowingDos>

}