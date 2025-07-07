package com.warusmart.crops.interfaces.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.warusmart.sowings.interfaces.activities.GeneralCropInfo
import com.warusmart.R
import com.warusmart.iam.domain.model.SessionManager
import com.warusmart.crops.application.services.DeviceService
import com.warusmart.crops.interfaces.adapters.DeviceAdapter
import com.warusmart.iam.interfaces.activities.SignInActivity
import com.warusmart.shared.interfaces.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Activity for showing IOT Devices in a sowing.
 */
class DeviceActivity : BaseActivity() {
    private lateinit var deviceRecyclerView: RecyclerView
    private lateinit var deviceService: DeviceService
    private lateinit var deviceAdapter: DeviceAdapter

    /**
     * Activity setup and initial data loading.
     */
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_device, findViewById(R.id.container))
        enableEdgeToEdge()

        val token = SessionManager.token
        if (token != null) {
            deviceService = DeviceService(this, token)
        } else {
            Toast.makeText(this, "Token not found. Please log in again.", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
            return
        }

        // Configurar el BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.navigation_home

        deviceRecyclerView = findViewById(R.id.deviceRecyclerView)
        deviceRecyclerView.layoutManager = LinearLayoutManager(this)

        val sowingId = intent.getIntExtra("SOWING_ID", 1)
        Log.d("DevicesActivity", "Received sowingId: $sowingId")
        if (sowingId != -1) {
            fetchDevicesBySowingId(sowingId)
        } else {
            Log.e("DevicesActivity", "Invalid sowing ID")
            Toast.makeText(this, "Invalid sowing ID", Toast.LENGTH_SHORT).show()
            finish()
        }

        setupSpinner()
    }

    /**
     * Fetches devices by sowing ID using the service.
     */
    private fun fetchDevicesBySowingId(sowingId: Int){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val devices = deviceService.getDevicesBySowingId(sowingId)
                withContext(Dispatchers.Main){
                    deviceAdapter = DeviceAdapter(devices)
                    deviceRecyclerView.adapter = deviceAdapter
                }
            } catch (e: Exception){
                Log.e("DeviceAcivity", "Error fetching devices: ${e.message}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@DeviceActivity, "Error fetching devices: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * Sets up the spinner for navigation between crop info screens.
     */
    private fun setupSpinner() {
        val spinner: Spinner = findViewById(R.id.dropdown_menu)
        ArrayAdapter.createFromResource(
            this,
            R.array.crop_info_options,
            R.layout.spinner_item_white_text
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_item_white_text)
            spinner.adapter = adapter
        }

        var isFirstSelection = true

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                if (isFirstSelection) {
                    isFirstSelection = false
                    return
                }
                view?.let {
                    val cropId = intent.getIntExtra("CROP_ID", -1)
                    val sowingId = intent.getIntExtra("SOWING_ID", -1)
                    when (position) {
                        0 -> startActivity(Intent(this@DeviceActivity, GeneralCropInfo::class.java).apply {
                            putExtra("SOWING_ID", sowingId)
                        })
                        1 -> startActivity(Intent(this@DeviceActivity, DeviceActivity::class.java).apply {
                            putExtra("SOWING_ID", sowingId)
                        })
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No action needed
            }
        }
        val productPosition = resources.getStringArray(R.array.crop_info_options).indexOf("Products Used")
        spinner.setSelection(productPosition)
    }
}