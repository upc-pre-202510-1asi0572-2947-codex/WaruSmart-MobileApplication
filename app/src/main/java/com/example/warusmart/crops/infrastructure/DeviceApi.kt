package com.example.warusmart.crops.infrastructure

import com.example.warusmart.crops.domain.model.beans.Device
import retrofit2.http.GET
import retrofit2.http.Path

interface DeviceApi {
    @GET("crops-management/sowings/{sowingId}/devices")
    suspend fun getDevicesBySowingId(@Path("sowingId") sowingId: Int): List<Device>
}