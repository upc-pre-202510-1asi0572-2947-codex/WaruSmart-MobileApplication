package com.warusmart.crops.application.services

import com.warusmart.crops.interfaces.adapters.CustomDateTypeAdapter
import android.util.Log
import com.warusmart.iam.domain.model.SessionManager
import com.warusmart.crops.domain.model.beans.Cares
import com.warusmart.crops.infrastructure.CropCaresApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.github.cdimascio.dotenv.dotenv
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketException
import java.util.Date

/**
 * Service for handling crop cares API requests.
 */
class CropCaresService {
    val dotenv = dotenv {
        directory = "/assets"
        filename = "env"
    }
    private val api: CropCaresApi
    private val token = SessionManager.token

    init {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder: Request.Builder = original.newBuilder()
                .header("Authorization", "Bearer $token")
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }.build()

        val gson: Gson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, CustomDateTypeAdapter())
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(dotenv["API_URL"])
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        api = retrofit.create(CropCaresApi::class.java)
    }

    // Gets a care by its ID
    suspend fun getCareById(id: Int): Cares? {
        return try {
            api.getCareById(id)
        } catch (e: SocketException) {
            Log.e("CropCaresService", "SocketException: ${e.message}")
            null
        }
    }

    // Gets all cares for a specific crop
    suspend fun getCaresByCropId(cropId: Int): List<Cares> {
        return try {
            api.getCaresByCropId(cropId)
        } catch (e: SocketException) {
            Log.e("CropCaresService", "SocketException: ${e.message}")
            emptyList()
        }
    }
}