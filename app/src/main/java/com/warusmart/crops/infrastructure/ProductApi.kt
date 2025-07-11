// app/src/main/java/com/example/chaquitaclla_appmovil_android/crops_details/interfaces/ProductApi.kt
package com.warusmart.crops.infrastructure

import com.warusmart.shared.domain.model.Entities.Product
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit API interface for product endpoints.
 */
interface ProductApi {
    @GET("crops-management/crops/{cropId}/products")
    suspend fun getProductsByCropId(@Path("cropId") cropId: Int): List<Product>
}