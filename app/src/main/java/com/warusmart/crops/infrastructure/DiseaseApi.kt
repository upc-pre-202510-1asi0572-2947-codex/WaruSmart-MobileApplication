package com.warusmart.crops.infrastructure

import com.warusmart.crops.domain.model.beans.Disease
import retrofit2.http.GET
import retrofit2.http.Path

// API for crop diseases
interface DiseaseApi {
    // Get a disease by its ID
    @GET("crops-management/crops/diseases/{id}")
    suspend fun getDiseaseById(@Path("id") id: Int): Disease

    // Get all diseases for a crop by crop ID
    @GET("crops-management/crops/{cropId}/diseases")
    suspend fun getDiseasesByCropId(@Path("cropId") cropId: Int): List<Disease>
}