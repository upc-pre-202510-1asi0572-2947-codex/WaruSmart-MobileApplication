package com.example.chaquitaclla_appmovil_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chaquitaclla_appmovil_android.statistics.StatisticsService

class ProductivityReportActivity : BaseActivity() {

    private lateinit var statisticsService: StatisticsService
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_water_statistics, findViewById(R.id.container))

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