// app/src/main/java/com/example/chaquitaclla_appmovil_android/crops_details/interfaces/PestApi.kt
package com.example.warusmart.crops.infrastructure

import com.example.warusmart.crops.domain.model.beans.Pest
import retrofit2.http.GET
import retrofit2.http.Path

interface PestApi {
    @GET("crops-management/crops/{cropId}/pests")
    suspend fun getPestsByCropId(@Path("cropId") cropId: Int): List<Pest>
}