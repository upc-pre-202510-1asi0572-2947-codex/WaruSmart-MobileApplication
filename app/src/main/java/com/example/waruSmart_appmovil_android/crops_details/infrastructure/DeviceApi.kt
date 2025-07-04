package com.example.waruSmart_appmovil_android.crops_details.infrastructure

import com.example.waruSmart_appmovil_android.crops_details.domain.model.beans.Device
import retrofit2.http.GET
import retrofit2.http.Path

interface DeviceApi {
    @GET("crops-management/sowings/{sowingId}/devices")
    suspend fun getDevicesBySowingId(@Path("sowingId") sowingId: Int): List<Device>
}