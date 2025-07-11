package com.warusmart.sowings.application.services

import android.content.Context
import android.util.Log
import com.warusmart.sowings.domain.model.Device
import com.warusmart.sowings.domain.model.UpdateActuatorRequest
import com.warusmart.sowings.domain.model.UpdatedActuator
import com.warusmart.sowings.infrastructure.DeviceApi
import io.github.cdimascio.dotenv.dotenv
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketException

/**
 * Service for handling device-related API requests and operations.
 */
class DeviceService(context: Context, private val bearerToken: String) {

    private val dotenv = dotenv {
        directory = "/assets"
        filename = "env"
    }
    private val apiUrl = dotenv["API_URL"]
    private val api: DeviceApi

    init {
        if (bearerToken.isEmpty()) {
            throw IllegalArgumentException("Bearer token is missing or empty")
        }

        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder: Request.Builder = original.newBuilder()
                .header("Authorization", "Bearer $bearerToken")
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(DeviceApi::class.java)
    }

    /**
     * Gets all devices for a specific sowing.
     */
    suspend fun getDevicesBySowingId(sowingId: Int): List<Device> {
        Log.d("DeviceService", "Fetching devices for sowingId: $sowingId")
        return try {
            api.getDevicesBySowingId(sowingId)
        } catch (e: SocketException) {
            Log.e("DeviceService", "SocketException: ${e.message}")
            emptyList()
        } catch (e: Exception) {
            Log.e("DeviceService", "Error fetching devices: ${e.message}")
            emptyList()
        }
    }

    suspend fun updateActuatorState(sowingId: Int, deviceId: Int, data: UpdateActuatorRequest): UpdatedActuator {
        Log.d("DeviceService", "Updating actuator state for sowingId: $sowingId, deviceId: $deviceId")
        return try {
            api.updateActuatorState(sowingId, deviceId, data)
        } catch (e: SocketException) {
            Log.e("DeviceService", "SocketException: ${e.message}")
            throw e
        } catch (e: Exception) {
            Log.e("DeviceService", "Error updating actuator state: ${e.message}")
            throw e
        }
    }
}
