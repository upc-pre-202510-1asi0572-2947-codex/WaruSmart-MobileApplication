package com.example.chaquitaclla_appmovil_android.crops_details.interfaces

import com.example.chaquitaclla_appmovil_android.crops_details.beans.Disease
import retrofit2.http.GET
import retrofit2.http.Path

interface DiseaseApi {
    @GET("crops-management/crops/diseases/{id}")
    suspend fun getDiseaseById(@Path("id") id: Int): Disease

    @GET("crops-management/crops/{cropId}/diseases")
    suspend fun getDiseasesByCropId(@Path("cropId") cropId: Int): List<Disease>
}