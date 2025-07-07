package com.warusmart.iam.infrastructure

import com.warusmart.iam.domain.model.SignInRequest
import com.warusmart.iam.domain.model.SignInResponse
import com.warusmart.iam.domain.model.SignUpRequest
import com.warusmart.iam.domain.model.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Retrofit service interface for authentication endpoints.
 */
interface AuthService {

    /**
     * Creates a new user with the given username and password
     */
    @POST("authentication/sign-up")
    fun signUp(@Body request: SignUpRequest): Call<SignUpResponse>

    /**
     * Signs in if the user exists and the password is correct
     */
    @POST("authentication/sign-in")
    fun signIn(@Body request: SignInRequest): Call<SignInResponse>

}