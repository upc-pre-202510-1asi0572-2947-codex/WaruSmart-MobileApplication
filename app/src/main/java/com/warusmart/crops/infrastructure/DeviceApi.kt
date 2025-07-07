package com.warusmart.crops.infrastructure

import com.warusmart.crops.domain.model.beans.Device
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit API interface for device endpoints.
 */
interface DeviceApi {
    @GET("crops-management/sowings/{sowingId}/devices")
    suspend fun getDevicesBySowingId(@Path("sowingId") sowingId: Int): List<Device>
}