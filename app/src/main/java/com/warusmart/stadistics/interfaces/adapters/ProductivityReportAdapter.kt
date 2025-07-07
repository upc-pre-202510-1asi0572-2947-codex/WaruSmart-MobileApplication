package com.warusmart.stadistics.interfaces.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.warusmart.R
import com.warusmart.stadistics.domain.model.ProductivityReport

/**
 * Adapter for displaying a list of productivity reports in a RecyclerView.
 * Handles the creation and binding of view holders for each report item.
 */
class ProductivityReportAdapter(
    private val reports: List<ProductivityReport>
) : RecyclerView.Adapter<ProductivityReportAdapter.ProductivityReportViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductivityReportViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_productivity_report, parent, false)
        return ProductivityReportViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reports.size
    }

    override fun onBindViewHolder(holder: ProductivityReportViewHolder, position: Int) {
        val report = reports[position]
        holder.bid(report)
    }

    /**
     * ViewHolder for a productivity report item.
     * Holds references to the views and binds data to them.
     */
    inner class ProductivityReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val cropNameTextView: TextView = itemView.findViewById(R.id.cropName)
        private val productivityTextView: TextView = itemView.findViewById(R.id.productivityValue)
        private val areaTextView: TextView = itemView.findViewById(R.id.areaValue)
        private val productionTextView: TextView = itemView.findViewById(R.id.productionValue)

        /**
         * Binds a ProductivityReport object to the item views.
         */
        fun bid(report: ProductivityReport){
            cropNameTextView.text = report.name
            productionTextView.text = report.production.toString()
            areaTextView.text = report.area.toString()
            val productivity: Float = report.production/report.area
            productivityTextView.text = productivity.toString()
        }

    }
}