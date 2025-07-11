package com.warusmart.crops.interfaces.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.warusmart.R
import com.warusmart.shared.domain.model.Entities.Control
import android.text.Html.fromHtml

/**
 * RecyclerView Adapter for displaying controls in a list.
 */
class ControlAdapter(
    private val controls: List<Control>,
    private val onEditClick: (Control) -> Unit,
    private val onDeleteClick: (Control) -> Unit
) : RecyclerView.Adapter<ControlAdapter.ControlViewHolder>() {

    /**
     * Creates and returns a new ControlViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ControlViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_control, parent, false)
        return ControlViewHolder(view)
    }

    /**
     * Returns the total number of controls in the list.
     */
    override fun getItemCount(): Int {
        return controls.size
    }

    /**
     * Binds the control data to the ViewHolder.
     */
    override fun onBindViewHolder(holder: ControlViewHolder, position: Int) {
        val control = controls[position]
        holder.bind(control)
    }

    inner class ControlViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sowingConditionTextView: TextView = itemView.findViewById(R.id.sowingConditionTextView)
        private val stemConditionTextView: TextView = itemView.findViewById(R.id.stemConditionTextView)
        private val soilMoistureTextView: TextView = itemView.findViewById(R.id.soilMoistureTextView)
        private val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        private val editButton: ImageView = itemView.findViewById(R.id.editButton)
        private val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)

        /**
         * Binds the control data to the UI elements.
         */
        fun bind(control: Control) {
            sowingConditionTextView.text = fromHtml("<b>Leaves: </b> ${control.sowingCondition}")
            stemConditionTextView.text = fromHtml("<b>Stem: </b> ${control.stemCondition}")
            soilMoistureTextView.text = fromHtml("<b>Soil Moisture: </b> ${control.sowingSoilMoisture}")
            dateTextView.text = fromHtml("<b>Date: </b> ${control.date}")

            editButton.setOnClickListener { onEditClick(control) }
            deleteButton.setOnClickListener { onDeleteClick(control) }
        }
    }
}