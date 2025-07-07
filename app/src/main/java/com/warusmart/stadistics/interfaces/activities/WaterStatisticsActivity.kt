package com.warusmart.stadistics.interfaces.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import androidx.lifecycle.lifecycleScope
import com.warusmart.shared.interfaces.BaseActivity
import com.warusmart.R
import com.warusmart.stadistics.application.services.StatisticsService
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

/**
 * Activity for displaying water usage statistics.
 */
class WaterStatisticsActivity : BaseActivity() {
    private lateinit var statisticsService: StatisticsService
    private lateinit var progressBar: ProgressBar

    // Activity setup and initial data loading
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_water_statistics, findViewById(R.id.container))

        //Here we are starting the bottom navigation view with the statistics item selected
        //once the activity is created
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.navigation_statistics

        statisticsService = StatisticsService(this)
        progressBar = findViewById(R.id.progressBar)

        fetchData()
        setupSpinner()
    }

    // Refreshes data when activity resumes
    override fun onResume() {
        super.onResume()
        fetchData()
    }

    // Fetches water statistics data
    private fun fetchData() {
        showLoading(true)
        lifecycleScope.launch {
            try {

            } catch (e: Exception) {
                Log.e("com.example.chaquitaclla_appmovil_android.StaticsActivity", "Error loading data", e)
            } finally {
                showLoading(false)
            }
        }
    }

    // Shows or hides the loading indicator
    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
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
                    Log.d("WaterReportActivity", "Spinner item selected")
                    when (position) {
                        0 -> startActivity(Intent(this@WaterStatisticsActivity, WaterStatisticsActivity::class.java))
                        1 -> startActivity(Intent(this@WaterStatisticsActivity, EnviromentReportActivity::class.java))
                        2 -> startActivity(Intent(this@WaterStatisticsActivity, ProductivityReportActivity::class.java))
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No action needed
            }
        }

        val spinnerPosition = resources.getStringArray(R.array.stadistics_options).indexOf("Water consumption")
        spinner.setSelection(spinnerPosition)
    }

}