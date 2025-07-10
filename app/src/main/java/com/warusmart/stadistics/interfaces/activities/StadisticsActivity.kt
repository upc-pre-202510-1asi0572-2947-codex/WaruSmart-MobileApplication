package com.warusmart.stadistics.interfaces.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.warusmart.R
import com.warusmart.shared.interfaces.BaseActivity

/**
 * Activity for displaying statistics related to various metrics.
 * Handles UI setup and initial data loading.
 */
class StadisticsActivity : BaseActivity() {

    /**
     * Activity setup and initial data loading
     */
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_stadistics, findViewById(R.id.container))

        //Here we are starting the bottom navigation view with the statistics item selected
        //once the activity is created
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.navigation_statistics

        setupChart1()
    }

    /**
     * Sets up the first bar chart with sample data.
     * Configures the chart's appearance and behavior.
     */
    private fun setupChart1() {
        val barChart = findViewById<BarChart>(R.id.stadisticsChart1)

        //Defines data
        val entries = listOf(
            BarEntry(1f, 10f),
            BarEntry(2f, 15f),
            BarEntry(3f, 16f),
            BarEntry(4f, 24f),
            BarEntry(5f, 20f),
        )

        val dataset = BarDataSet(entries, "Sample Data")
        dataset.color = 1

        val data = BarData(dataset)
        data.barWidth = 0.9f // Set the width of the bars

        //Defines labels
        val labels = listOf("A", "B", "C", "D", "E")

        //BarChart configuration
        barChart.data = data
        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        barChart.xAxis.granularity = 1f
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM

        barChart.description.isEnabled = false
        barChart.setFitBars(true)
        barChart.animateY(1000)
        barChart.invalidate()
    }
}