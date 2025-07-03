package com.example.waruSmart_appmovil_android.statistics.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.waruSmart_appmovil_android.R
import com.example.waruSmart_appmovil_android.statistics.beans.ProductivityReport

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

    inner class ProductivityReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val cropNameTextView: TextView = itemView.findViewById(R.id.cropName)
        private val productivityTextView: TextView = itemView.findViewById(R.id.productivityValue)
        private val areaTextView: TextView = itemView.findViewById(R.id.areaValue)
        private val productionTextView: TextView = itemView.findViewById(R.id.productionValue)

        fun bid(report: ProductivityReport){
            cropNameTextView.text = report.name
            productionTextView.text = report.production.toString()
            areaTextView.text = report.area.toString()
            val productivity: Float = report.production/report.area
            productivityTextView.text = productivity.toString()
        }

    }
}