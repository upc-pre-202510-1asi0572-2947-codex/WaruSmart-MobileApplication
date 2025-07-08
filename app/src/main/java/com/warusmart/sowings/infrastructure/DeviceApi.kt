package com.warusmart.sowings.infrastructure

import com.warusmart.sowings.domain.model.Device
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit API interface for device endpoints.
 */
interface DeviceApi {
    @GET("crops-management/sowings/{sowingId}/devices")
    suspend fun getDevicesBySowingId(@Path("sowingId") sowingId: Int): List<Device>
}