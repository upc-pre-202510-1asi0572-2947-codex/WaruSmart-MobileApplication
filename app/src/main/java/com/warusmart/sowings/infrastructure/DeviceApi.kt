package com.warusmart.sowings.infrastructure

import com.warusmart.sowings.domain.model.Device
import com.warusmart.sowings.domain.model.UpdateActuatorRequest
import com.warusmart.sowings.domain.model.UpdatedActuator
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Retrofit API interface for device endpoints.
 */
interface DeviceApi {

    /**
     * Gets all the devices associated with a specific sowing ID.
     */
    @GET("crops-management/sowings/{sowingId}/devices")
    suspend fun getDevicesBySowingId(@Path("sowingId") sowingId: Int): List<Device>

    /**
     * Updates the state of an actuator device for a specific sowing ID.
     */
    @PUT("crops-management/monitoring-devices/{sowingId}/devices/{deviceId}")
    suspend fun updateActuatorState(@Path ("sowingId") sowingId: Int, @Path("deviceId") deviceId: Int, @Body data: UpdateActuatorRequest): UpdatedActuator
}