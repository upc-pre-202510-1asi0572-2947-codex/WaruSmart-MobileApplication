package com.example.waruSmart_appmovil_android.stadistics.infrastructure;

import com.example.waruSmart_appmovil_android.stadistics.domain.model.Control
import com.example.waruSmart_appmovil_android.stadistics.domain.model.Crop;
import com.example.waruSmart_appmovil_android.stadistics.domain.model.ProductivityReport
import com.example.waruSmart_appmovil_android.stadistics.domain.model.Sowing

import retrofit2.http.GET;
import retrofit2.http.Path

public interface StatisticsApi {

    @GET("crops-management/sowings/controls")
    suspend fun getSowingsControls(): List<Control>

    //Function used to get the name of the crop by the cropId
    @GET("crops-management/crops/{cropId}")
    suspend fun getCropName(@Path("cropId") cropId: Int): Crop

    //Function used to get all sowings
    @GET("crops-management/sowings")
    suspend fun getSowings(): List<Sowing>

    //Function to get productivity report
    @GET("reports/productivity/{userId}")
    suspend fun getProductivityReports(@Path("userId") userId: Int) : List<ProductivityReport>
}
