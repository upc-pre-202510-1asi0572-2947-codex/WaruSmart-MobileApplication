package com.warusmart.crops.interfaces.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.warusmart.R
import com.warusmart.crops.domain.model.beans.Cares
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * RecyclerView Adapter for displaying crop cares in a list.
 */
class CropCareAdapter(private val caresList: List<Cares>) : RecyclerView.Adapter<CropCareAdapter.CropCareViewHolder>() {

    /**
     * Creates and returns a new CropCareViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CropCareViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_care, parent, false)
        return CropCareViewHolder(view)
    }

    /**
     * Binds the care data to the ViewHolder.
     */
    override fun onBindViewHolder(holder: CropCareViewHolder, position: Int) {
        val care = caresList[position]
        holder.bind(care)
    }

    /**
     * Returns the total number of cares in the list.
     */
    override fun getItemCount(): Int {
        return caresList.size
    }

    class CropCareViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val suggestionTextView: TextView = itemView.findViewById(R.id.suggesetionTextView)
        private val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        /**
         * Binds the care data to the UI elements.
         */
        fun bind(care: Cares) {
            suggestionTextView.text = care.suggestion
            dateTextView.text = dateFormat.format(care.date)
        }
    }
}