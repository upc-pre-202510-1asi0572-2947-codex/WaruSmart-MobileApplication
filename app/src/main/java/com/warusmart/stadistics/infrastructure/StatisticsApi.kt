package com.warusmart.stadistics.infrastructure;

import com.warusmart.stadistics.domain.model.Control
import com.warusmart.stadistics.domain.model.Crop;
import com.warusmart.stadistics.domain.model.ProductivityReport
import com.warusmart.stadistics.domain.model.Sowing

import retrofit2.http.GET;
import retrofit2.http.Path

/**
 * API for statistics endpoints
 */
public interface StatisticsApi {

    // Get all sowings controls
    @GET("crops-management/sowings/controls")
    suspend fun getSowingsControls(): List<Control>

    // Get the name of the crop by cropId
    @GET("crops-management/crops/{cropId}")
    suspend fun getCropName(@Path("cropId") cropId: Int): Crop

    // Get all sowings
    @GET("crops-management/sowings")
    suspend fun getSowings(): List<Sowing>

    // Get productivity reports for a user
    @GET("reports/productivity/{userId}")
    suspend fun getProductivityReports(@Path("userId") userId: Int) : List<ProductivityReport>
}
