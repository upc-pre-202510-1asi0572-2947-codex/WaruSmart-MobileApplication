package com.warusmart.stadistics.application.services

import com.warusmart.shared.application.DB.AppDataBase
import android.content.Context
import android.util.Log
import com.warusmart.iam.domain.model.SessionManager
import com.warusmart.stadistics.domain.model.ProductivityReport
import com.warusmart.stadistics.domain.model.StatisticBar
import com.warusmart.stadistics.infrastructure.StatisticsApi
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketException
import io.github.cdimascio.dotenv.dotenv
import com.github.mikephil.charting.data.PieEntry
import com.warusmart.stadistics.domain.model.Report
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * Service class for handling statistics-related operations.
 * Provides methods to fetch and process statistics data from API and local database.
 */
class StatisticsService(context:Context) {
    val dotenv = dotenv() {
        directory = "/assets"
        filename = "env"
    }
    private val api: StatisticsApi
    private val token = SessionManager.token

    private val db = AppDataBase.getDatabase(context)

    init {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder: Request.Builder = original.newBuilder()
                .header("Authorization", "Bearer $token")
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(dotenv["API_URL"])
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(StatisticsApi::class.java)
    }


    /**
     * Calls the API to get all sowings and their related crops.
     * Returns a list of StatisticBar with the quantity of crops.
     */
    suspend fun getQuantityOfCrops(): List<StatisticBar> {
        return try {
            val sowings = api.getSowings()
            Log.d("StatisticsService", "Raw JSON response: $sowings")

            val quantityOfCrops = mutableMapOf<String, Int>()

            for(sowing in sowings) {
                val cropId = sowing.cropId
                val crop = api.getCropName(cropId)
                quantityOfCrops[crop.name] = quantityOfCrops.getOrDefault(crop.name, 0) + 1
            }

            val statisticBars = quantityOfCrops.map { StatisticBar(it.key, it.value.toFloat()) }
            Log.d("StatisticsService", "Converted data: $statisticBars")
            statisticBars
        } catch (e: SocketException) {
            Log.e("StatisticsService", "SocketException: ${e.message}")
            emptyList()
        }
    }

    /**
     * Calls the API to get all controls and their related sowings.
     * Returns a list of PieEntries with the quantity of controls by sowingId.
     */
    suspend fun getQuantityOfControlsBySowingId(): List<PieEntry> {
        return try {
            val sowingsControls = api.getSowingsControls()
            Log.d("StatisticsService", "Raw JSON response: $sowingsControls")

            val quantityOfControls = mutableMapOf<Int, Int>()
            for (control in sowingsControls) {
                val sowingId = control.sowingId
                quantityOfControls[sowingId] = quantityOfControls.getOrDefault(sowingId, 0) + 1
            }

            val pieEntries = quantityOfControls.map { PieEntry(it.value.toFloat(), it.key.toString()) }
            Log.d("StatisticsService", "Converted data: $pieEntries")
            pieEntries
        } catch (e: SocketException) {
            Log.e("StatisticsService", "SocketException: ${e.message}")
            emptyList()
        }
    }

    /**
     * Gets the quantity of controls by sowingId from the local database.
     * Returns a list of PieEntries with the quantity of controls by crop name.
     */
    suspend fun getQuantityOfControlsBySowingIdByDB(): List<PieEntry> {
        return withContext(Dispatchers.IO) {
            val controls = db.controlDAO().getAllControls()
            Log.d("StatisticsService", "Controls from DB: $controls")
            val quantityOfControls = mutableMapOf<Int, Int>()

            for (control in controls) {
                val sowingId = control.sowingId
                quantityOfControls[sowingId] = quantityOfControls.getOrDefault(sowingId, 0) + 1
            }

            val pieEntries = mutableListOf<PieEntry>()
            for ((sowingId, count) in quantityOfControls) {
                val sowing = db.sowingDAO().getSowingById(sowingId)
                if (sowing != null) {
                    val crop = api.getCropName(sowing.cropId)
                    pieEntries.add(PieEntry(count.toFloat(), crop.name))
                }
            }

            pieEntries
        }
    }

    /**
     * Gets the quantity of crops from the local database.
     * Returns a list of StatisticBar with the quantity of crops by crop name.
     */
    suspend fun getQuantityOfCropsByDB(): List<StatisticBar> {
        return withContext(Dispatchers.IO) {
            val sowings = db.sowingDAO().getAllSowings()
            val quantityOfCrops = mutableMapOf<Int, Int>()

            // Count the number of sowings for each cropId
            for (sowing in sowings) {
                val cropId = sowing.cropId
                quantityOfCrops[cropId] = quantityOfCrops.getOrDefault(cropId, 0) + 1
            }

            // Call the API to get the crop name for each cropId
            val statisticBars = mutableListOf<StatisticBar>()
            for ((cropId, count) in quantityOfCrops) {
                val crop = api.getCropName(cropId) // API call to get the crop name
                statisticBars.add(StatisticBar(crop.name, count.toFloat()))
            }

            statisticBars
        }
    }


    /**
     * This method call at the reports api, in order to obtain reports
     * for the productivity
     */
    suspend fun getProductivityReportsById(userId: Int): List<ProductivityReport>{
        return try{
            api.getProductivityReports(userId);
        } catch (e: Exception) {
            Log.e("Stadistics Service", "Socking productivity reports: ${e.message}")
            emptyList()
        }
    }

    /**
     * This method call at the reports api, in order to obtain reports
     * for temperature, humidity and soil moisture
     */
    suspend fun getReports() : List<Report>{
        return try {
            api.getReports()
        } catch (e: Exception) {
            Log.e("Stadistics Service", "Error socking reports: ${e.message}")
            emptyList()
        }
    }

}