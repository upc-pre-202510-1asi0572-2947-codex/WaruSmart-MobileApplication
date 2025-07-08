package com.warusmart.sowings.interfaces.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.warusmart.R
import com.warusmart.sowings.application.services.DeviceService
import com.warusmart.sowings.domain.model.Device
import com.warusmart.sowings.domain.model.UpdateActuatorRequest
import com.warusmart.sowings.domain.model.UpdatedActuator
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * RecyclerView Adapter for displaying devices in a list.
 */
class DeviceAdapter(
    private val devices: List<Device>,
    private val service: DeviceService
) : RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

    /**
     * Creates and returns a new DeviceViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_device, parent, false)
        return DeviceViewHolder(view)
    }

    /**
     * Returns the total number of devices in the list.
     */
    override fun getItemCount(): Int {
        return devices.size
    }

    /**
     * Binds the device data to the ViewHolder.
     */
    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val device = devices[position]
        holder.bid(device)
    }

    /**
     * ViewHolder class for device items.
     */
    inner class DeviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val deviceNameTextView: TextView = itemView.findViewById(R.id.deviceNameTextView)
        private val deviceTypeTextView: TextView = itemView.findViewById(R.id.deviceTypeTextView)
        private val deviceLastSyncTextView: TextView = itemView.findViewById(R.id.decieLastSyncTextView)
        private val deviceLocationTextView: TextView = itemView.findViewById(R.id.deviceLocationTextView)
        private val deviceHumidityTextView: TextView = itemView.findViewById(R.id.deviceHumidityTextView)
        private val deviceTemperatureTextView: TextView = itemView.findViewById(R.id.deviceTemperatureTextView)
        private val deviceSoilMoistureTextView: TextView = itemView.findViewById(R.id.deviceSoilMoistureTextView)
        private val buttonActivate:Button = itemView.findViewById(R.id.button_activate_actuator)
        private val buttonDeactivate:Button = itemView.findViewById(R.id.button_deactivate_actuator)

        /**
         * Binds the device data to the UI elements.
         */
        fun bid(device: Device){

            val name = device.name.ifEmpty { "Unknown Device" }
            val id = device.deviceId.ifEmpty { "Unknown ID" }
            val sowingId = device.sowingId.takeIf { it < 0 }?.toString() ?: "Unknown Sowing ID"
            val type = device.deviceType.ifEmpty { "Unknown Type" }
            val lastSync = device.lastSyncDate.ifEmpty { "Never" }
            val location = device.location.ifEmpty { "Unknown Location" }
            val humidity = device.humidity.takeIf { it >= 0 }?.toString() ?: "N/A"
            val temperature = device.temperature.takeIf { it >= 0 }?.toString() ?: "N/A"
            val soilMoisture = device.soilMoisture.takeIf { it >= 0 }?.toString() ?: "N/A"

            deviceNameTextView.text = name + id
            deviceTypeTextView.text = "Sensor Type: ${type}"
            /*deviceStatusTextView.text = "Status ${when (device.status) {
                0 -> "Inactive"
                1 -> "Active"
                else -> "Unknown"
            }}"*/
            deviceLastSyncTextView.text = "Last Sync: ${lastSync}"
            deviceLocationTextView.text = "Location: ${location}"
            deviceHumidityTextView.text = "Humidity: ${humidity}"
            deviceTemperatureTextView.text = "Temperature: ${temperature}"
            deviceSoilMoistureTextView.text = "Soil Moisture: ${soilMoisture}"

            buttonActivate.setOnClickListener(){
                val data = UpdateActuatorRequest("Actice")
                GlobalScope.launch {
                    val result:UpdatedActuator = service.updateActuatorState(device.sowingId, device.id, data)
                    Log.d("DeviceAdapter", "Actuator activated: ${result.toString()}")
                }
            }

            buttonDeactivate.setOnClickListener(){
                val data = UpdateActuatorRequest("Disactivate")
                GlobalScope.launch {
                    val result:UpdatedActuator = service.updateActuatorState(device.sowingId, device.id, data)
                    Log.d("DeviceAdapter", "Actuator deactivated: ${result.toString()}")
                }
            }
        }
    }
}