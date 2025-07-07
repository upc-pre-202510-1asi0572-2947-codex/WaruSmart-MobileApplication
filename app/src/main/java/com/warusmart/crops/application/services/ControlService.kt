// app/src/main/java/com/example/chaquitaclla_appmovil_android/crops_details/ControlService.kt
package com.warusmart.crops.application.services

import com.warusmart.shared.domain.model.Entities.Control
import com.warusmart.crops.infrastructure.ControlApi
import com.warusmart.iam.infrastructure.RetrofitClient
import io.github.cdimascio.dotenv.dotenv
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ControlService {
    private val api: ControlApi

    val dotenv = dotenv() {
        directory = "/assets"
        filename = "env"
    }
    private val BASE_URL = RetrofitClient.dotenv["API_URL"]

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(ControlApi::class.java)
    }

    suspend fun getControlsBySowingId(sowingId: Int): List<Control> {
        return api.getControlsBySowingId(sowingId)
    }

    suspend fun getControlById(sowingId: Int, controlId: Int): Control {
        return api.getControlById(sowingId, controlId)
    }

    suspend fun deleteControl(sowingId: Int, controlId: Int) {
        api.deleteControl(sowingId, controlId)
    }

    suspend fun getAllControls(): List<Control> {
        return api.getAllControls()
    }

    suspend fun updateControl(sowingId: Int, controlId: Int, control: Control) {
        api.updateControl(sowingId, controlId, control)
    }

    suspend fun createControl(sowingId: Int, control: Control): Control {
        return api.createControl(sowingId, control)
    }
}