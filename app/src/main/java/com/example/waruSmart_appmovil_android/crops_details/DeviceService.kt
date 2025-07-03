package com.example.waruSmart_appmovil_android.crops_details

import android.content.Context
import android.util.Log
import com.example.waruSmart_appmovil_android.crops_details.beans.Device
import com.example.waruSmart_appmovil_android.crops_details.interfaces.DeviceApi
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketException

class DeviceService(context: Context, private val bearerToken: String) {

    private val apiUrl = "https://waru-smart-fzg6c7htcadxb9g5.canadacentral-01.azurewebsites.net/api/v1/"
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

    suspend fun getDevicesBySowingId(sowingId: Int): List<Device> {
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
}
