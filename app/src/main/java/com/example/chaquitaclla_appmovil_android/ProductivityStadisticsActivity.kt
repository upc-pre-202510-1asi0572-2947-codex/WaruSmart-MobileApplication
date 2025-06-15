package com.example.chaquitaclla_appmovil_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import com.example.chaquitaclla_appmovil_android.statistics.StatisticsService
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart

class ProductivityStadisticsActivity : BaseActivity() {

    private lateinit var statisticsService: StatisticsService
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupSpinner()
    }

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
                        0 -> startActivity(Intent(this@ProductivityStadisticsActivity, WaterStatisticsActivity::class.java))
                        1 -> startActivity(Intent(this@ProductivityStadisticsActivity, EnviormentStadisticsActivity::class.java))
                        2 -> startActivity(Intent(this@ProductivityStadisticsActivity, ProductivityStadisticsActivity::class.java))
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