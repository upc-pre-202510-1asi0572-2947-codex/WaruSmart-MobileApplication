package com.example.warusmart.iam.infrastructure

import com.example.warusmart.iam.domain.model.SignInRequest
import com.example.warusmart.iam.domain.model.SignInResponse
import com.example.warusmart.iam.domain.model.SignUpRequest
import com.example.warusmart.iam.domain.model.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("authentication/sign-up")
    fun signUp(@Body request: SignUpRequest): Call<SignUpResponse>

    @POST("authentication/sign-in")
    fun signIn(@Body request: SignInRequest): Call<SignInResponse>

}