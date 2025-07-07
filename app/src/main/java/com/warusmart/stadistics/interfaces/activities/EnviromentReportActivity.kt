package com.warusmart.stadistics.interfaces.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import com.warusmart.shared.interfaces.BaseActivity
import com.warusmart.R
import com.warusmart.stadistics.application.services.StatisticsService
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Activity for displaying environmental report statistics.
 */
class EnviromentReportActivity : BaseActivity() {

    private val statisticsService = StatisticsService(this)
    private lateinit var progressBar: ProgressBar

    // Activity setup and initial data loading
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_water_statistics, findViewById(R.id.container))

        // Configure the BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.navigation_statistics

        setupSpinner()
    }

    // Sets up the spinner for navigation between statistics screens
    private fun setupSpinner(){
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
                    Log.d("EnviromentReportActivity", "Spinner item selected")
                    when (position) {
                        0 -> startActivity(Intent(this@EnviromentReportActivity, WaterStatisticsActivity::class.java))
                        1 -> startActivity(Intent(this@EnviromentReportActivity, EnviromentReportActivity::class.java))
                        2 -> startActivity(Intent(this@EnviromentReportActivity, ProductivityReportActivity::class.java))
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No action needed
            }
        }

        val spinnerPosition = resources.getStringArray(R.array.stadistics_options).indexOf("Environment report")
        spinner.setSelection(spinnerPosition)
    }
}