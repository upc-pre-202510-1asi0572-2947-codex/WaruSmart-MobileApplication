package com.warusmart.stadistics.interfaces.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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
import com.warusmart.stadistics.application.services.StatisticsService
import com.warusmart.stadistics.domain.model.Report
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Activity for displaying statistics related to various metrics.
 * Handles UI setup and initial data loading.
 */
class StadisticsActivity : BaseActivity() {

    private lateinit var reports: List<Report>
    private lateinit var barChartTemperature: BarChart
    private lateinit var barChartHumidity: BarChart
    private lateinit var barChartSoilMoisture: BarChart
    private val reportsService = StatisticsService(this)

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

        reports = listOf(
            Report("A", 22.5f, 55.0f, 30.0f),
            Report("B", 20.0f, 60.0f, 35.0f),
            Report("C", 18.5f, 65.0f, 40.0f),
            Report("D", 18.5f, 65.0f, 74.0f),
            Report("E", 19.5f, 65.0f, 48.0f),
            Report("F", 48.5f, 65.6f, 40.9f),
            Report("G", 58.5f, 60.0f, 47.1f),
            Report("H", 18.5f, 68.3f, 46.5f),
            Report("I", 18.8f, 65.9f, 40.0f)
        )
        fetchReports()
    }

    override fun onResume(){
        super.onResume()
        fetchReports()
    }

    private fun fetchReports() {
        CoroutineScope(Dispatchers.IO).launch{
            try {
                reports = reportsService.getReports()
                withContext(Dispatchers.Main){
                    setupChartTemperature(reports)
                    setupChartHumidity(reports)
                    setupChartSoilMoisture(reports)
                }
            } catch (e: Exception) {
                Log.e("StadisticsAcivity", "Error fetching reports: ${e.message}")
            }
        }
    }

    /**
     * Sets up the temperature bar chart with reports's data.
     */
    private fun setupChartTemperature(reports: List<Report>) {
        barChartTemperature = findViewById<BarChart>(R.id.stadisticsChartTemperature)

        //Defines data
        val entries = reports.mapIndexed { index, report ->
            BarEntry(index.toFloat(), report.avgTemperature)
        }

        val dataset = BarDataSet(entries, "Temperature by zones")
        dataset.color = 1

        val data = BarData(dataset)
        data.barWidth = 0.9f // Set the width of the bars

        //Defines labels
        val labels = reports.mapIndexed { index, report ->
            report.zone
        }

        //BarChart configuration
        barChartTemperature.data = data
        barChartTemperature.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        barChartTemperature.xAxis.granularity = 1f
        barChartTemperature.xAxis.position = XAxis.XAxisPosition.BOTTOM

        barChartTemperature.description.isEnabled = false
        barChartTemperature.setFitBars(true)
        barChartTemperature.animateY(1000)
        barChartTemperature.invalidate()
    }

    /**
     * Sets up the humidity bar chart with reports's data.
     */
    private fun setupChartHumidity(reports: List<Report>) {
        barChartHumidity = findViewById<BarChart>(R.id.stadisticsChartHumidity)

        //Defines data
        val entries = reports.mapIndexed { index, report ->
            BarEntry(index.toFloat(), report.avgHumidity)
        }

        val dataset = BarDataSet(entries, "Humidity by zones")
        dataset.color = 2

        val data = BarData(dataset)
        data.barWidth = 0.9f // Set the width of the bars

        //Defines labels
        val labels = reports.mapIndexed { index, report ->
            report.zone
        }

        //BarChart configuration
        barChartHumidity.data = data
        barChartHumidity.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        barChartHumidity.xAxis.granularity = 1f
        barChartHumidity.xAxis.position = XAxis.XAxisPosition.BOTTOM

        barChartHumidity.description.isEnabled = false
        barChartHumidity.setFitBars(true)
        barChartHumidity.animateY(1000)
        barChartHumidity.invalidate()
    }

    /**
     * Sets up the soil moisture bar chart with reports's data.
     */
    private fun setupChartSoilMoisture(reports: List<Report>) {
        barChartSoilMoisture = findViewById<BarChart>(R.id.stadisticsChartSoilMosture)

        //Defines data
        val entries = reports.mapIndexed { index, report ->
            BarEntry(index.toFloat(), report.avgSoilMoisture)
        }

        val dataset = BarDataSet(entries, "Soil Moisture by zones")
        dataset.color = 3

        val data = BarData(dataset)
        data.barWidth = 0.9f // Set the width of the bars

        //Defines labels
        val labels = reports.mapIndexed { index, report ->
            report.zone
        }

        //BarChart configuration
        barChartSoilMoisture.data = data
        barChartSoilMoisture.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        barChartSoilMoisture.xAxis.granularity = 1f
        barChartSoilMoisture.xAxis.position = XAxis.XAxisPosition.BOTTOM

        barChartSoilMoisture.description.isEnabled = false
        barChartSoilMoisture.setFitBars(true)
        barChartSoilMoisture.animateY(1000)
        barChartSoilMoisture.invalidate()
    }
}