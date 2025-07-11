package com.warusmart.stadistics.infrastructure;

import com.warusmart.stadistics.domain.model.Control
import com.warusmart.stadistics.domain.model.Crop;
import com.warusmart.stadistics.domain.model.ProductivityReport
import com.warusmart.stadistics.domain.model.Report
import com.warusmart.stadistics.domain.model.Sowing

import retrofit2.http.GET;
import retrofit2.http.Path

/**
 * API interface for statistics endpoints.
 * Defines network operations for retrieving sowings, controls, crops, and productivity reports.
 */
public interface StatisticsApi {
    /**
     * Gets all sowings controls from the API.
     */
    @GET("crops-management/sowings/controls")
    suspend fun getSowingsControls(): List<Control>

    /**
     * Gets the name of the crop by cropId from the API.
     */
    @GET("crops-management/crops/{cropId}")
    suspend fun getCropName(@Path("cropId") cropId: Int): Crop

    /**
     * Gets all sowings from the API.
     */
    @GET("crops-management/sowings")
    suspend fun getSowings(): List<Sowing>

    /**
     * Gets productivity reports for a user from the API.
     */
    @GET("reports/productivity/{userId}")
    suspend fun getProductivityReports(@Path("userId") userId: Int) : List<ProductivityReport>

    /**
     * Gets reports for temperature, humidity and soil moisture
     */
    @GET("analytics/zone-averages")
    suspend fun getReports() : List <Report>
}
