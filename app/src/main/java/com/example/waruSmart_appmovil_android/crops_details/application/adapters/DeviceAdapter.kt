package com.example.waruSmart_appmovil_android.crops_details.application.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.waruSmart_appmovil_android.R
import com.example.waruSmart_appmovil_android.crops_details.domain.model.beans.Device

class DeviceAdapter(
    private val devices: List<Device>,
) : RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_device, parent, false)
        return DeviceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return devices.size
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val device = devices[position]
        holder.bid(device)
    }

    inner class DeviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val deviceNameTextView: TextView = itemView.findViewById(R.id.deviceNameTextView)
        private val deviceTypeTextView: TextView = itemView.findViewById(R.id.deviceTypeTextView)
        private val deviceStatusTextView: TextView = itemView.findViewById(R.id.deviceStatusTextView)
        private val deviceLastSyncTextView: TextView = itemView.findViewById(R.id.decieLastSyncTextView)
        private val deviceLocationTextView: TextView = itemView.findViewById(R.id.deviceLocationTextView)

        fun bid(device: Device){
            deviceNameTextView.text = device.title
            deviceTypeTextView.text = "Sensor Type: ${device.type}"
            deviceStatusTextView.text = "Status ${when (device.status) {
                0 -> "Inactive"
                1 -> "Active"
                else -> "Unknown"
            }}"
            deviceLastSyncTextView.text = "Last Sync: ${device.additionalProp1}"
            deviceLocationTextView.text = "Location: ${device.additionalProp2}"
        }
    }
}