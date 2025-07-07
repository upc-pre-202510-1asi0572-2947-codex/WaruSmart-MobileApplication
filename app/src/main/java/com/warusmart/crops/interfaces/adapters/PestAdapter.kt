package com.warusmart.crops.interfaces.adapters

import android.text.Html.fromHtml
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.warusmart.R
import com.warusmart.crops.domain.model.beans.Pest

/**
 * RecyclerView Adapter for displaying pests in a list.
 */
class PestAdapter(private val pests: List<Pest>) : RecyclerView.Adapter<PestAdapter.ViewHolder>() {

    private var filteredPests: List<Pest> = pests
    private var expandedPosition = -1

    /**
     * Creates and returns a new ViewHolder for pests.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pest, parent, false)
        return ViewHolder(view)
    }

    /**
     * Binds the pest data to the ViewHolder.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pest = filteredPests[position]
        holder.bind(pest, position)
    }

    /**
     * Returns the total number of pests in the list.
     */
    override fun getItemCount(): Int {
        return filteredPests.size
    }

    /**
     * Filters the pests list by a query string.
     */
    fun filter(query: String) {
        filteredPests = if (query.isEmpty()) {
            pests
        } else {
            pests.filter { it.name.contains(query, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.pestNameTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.pestDescriptionTextView)
        private val solutionTextView: TextView = itemView.findViewById(R.id.pestSolutionTextView)

        /**
         * Binds the pest data to the UI elements.
         */
        fun bind(pest: Pest, position: Int) {
            nameTextView.text = pest.name
            descriptionTextView.text = fromHtml("<b>Description:</b> ${pest.description}")
            solutionTextView.text = fromHtml("<b>Solution:</b> ${pest.solution}")

            val isExpanded = position == expandedPosition
            descriptionTextView.visibility = if (isExpanded) View.VISIBLE else View.GONE
            solutionTextView.visibility = if (isExpanded) View.VISIBLE else View.GONE

            itemView.setOnClickListener {
                expandedPosition = if (isExpanded) -1 else position
                notifyDataSetChanged()
            }
        }
    }
}