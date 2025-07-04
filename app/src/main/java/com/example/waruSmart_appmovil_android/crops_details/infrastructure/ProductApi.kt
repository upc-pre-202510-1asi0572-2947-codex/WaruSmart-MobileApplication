// app/src/main/java/com/example/chaquitaclla_appmovil_android/crops_details/interfaces/ProductApi.kt
package com.example.waruSmart_appmovil_android.crops_details.infrastructure

import Entities.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {
    @GET("crops-management/crops/{cropId}/products")
    suspend fun getProductsByCropId(@Path("cropId") cropId: Int): List<Product>
}