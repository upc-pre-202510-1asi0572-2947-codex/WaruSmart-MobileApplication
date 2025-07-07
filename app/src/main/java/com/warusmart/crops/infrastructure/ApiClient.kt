// ApiClient.kt
package com.warusmart.crops.infrastructure

import com.warusmart.crops.interfaces.adapters.CustomDateTypeAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.github.cdimascio.dotenv.dotenv
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date

/**
 * Singleton object for creating Retrofit API clients.
 */
object ApiClient {

    val dotenv = dotenv() {
        directory = "/assets"
        filename = "env"
    }
    private val BASE_URL = dotenv["API_URL"]

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(Date::class.java, CustomDateTypeAdapter())
        .create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }
}