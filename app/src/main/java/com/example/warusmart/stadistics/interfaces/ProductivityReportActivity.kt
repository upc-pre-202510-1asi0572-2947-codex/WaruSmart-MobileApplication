package com.example.warusmart.stadistics.interfaces

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.warusmart.shared.interfaces.BaseActivity
import com.example.warusmart.R
import com.example.warusmart.stadistics.application.services.StatisticsService
import com.example.warusmart.stadistics.application.adapters.ProductivityReportAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Activity for displaying productivity reports statistics.
 */
class ProductivityReportActivity : BaseActivity() {

    private lateinit var reportRecyclerView: RecyclerView
    private val statisticsService = StatisticsService(this)
    private lateinit var progressBar: ProgressBar
    private lateinit var reportAdapter: ProductivityReportAdapter

    // Activity setup and initial data loading
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_productivity_report, findViewById(R.id.container))

        // Configure BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.navigation_statistics

        reportRecyclerView = findViewById(R.id.productivityReportRecyclerView)
        reportRecyclerView.layoutManager = LinearLayoutManager(this)

        //TODO Pass id for fetch reports

        setupSpinner()
    }

    // Fetches productivity reports by user ID
    private fun fetchProductivityReportsByUserId(userId: Int){
        CoroutineScope(Dispatchers.IO).launch{
            try {
                val reports = statisticsService.getProductivityReportsById(userId)
                withContext(Dispatchers.Main){
                    reportAdapter = ProductivityReportAdapter(reports)
                    reportRecyclerView.adapter = reportAdapter
                }
            } catch (e: Exception){
                Log.e("DeviceAcivity", "Error fetching devices: ${e.message}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ProductivityReportActivity, "Error fetching reports: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Sets up the spinner for navigation between statistics screens
    private fun setupSpinner() {
        val spinner: Spinner = findViewById(R.id.dropdown_menu)
        ArrayAdapter.createFromResource(
            this,
            R.array.stadistics_options,
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
                    Log.d("ProductivityStadisticsActivity", "Spinner item selected")
                    when (position) {
                        0 -> startActivity(Intent(this@ProductivityReportActivity, WaterStatisticsActivity::class.java))
                        1 -> startActivity(Intent(this@ProductivityReportActivity, EnviromentReportActivity::class.java))
                        2 -> startActivity(Intent(this@ProductivityReportActivity, ProductivityReportActivity::class.java))
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No action needed
            }
        }

        val spinnerPosition = resources.getStringArray(R.array.stadistics_options).indexOf("Crop productivity")
        spinner.setSelection(spinnerPosition)
    }
}