package com.example.warusmart.iam.application.services

import com.example.warusmart.iam.domain.model.SignInRequest
import com.example.warusmart.iam.domain.model.SignInResponse
import com.example.warusmart.iam.domain.model.SignUpRequest
import com.example.warusmart.iam.domain.model.SignUpResponse
import com.example.warusmart.iam.infrastructure.AuthService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthServiceImpl(private val authService: AuthService) {

    fun signUp(request: SignUpRequest, callback: (SignUpResponse?) -> Unit) {
        authService.signUp(request).enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun signIn(request: SignInRequest, callback: (SignInResponse?) -> Unit) {
        authService.signIn(request).enqueue(object : Callback<SignInResponse> {
            override fun onResponse(call: Call<SignInResponse>, response: Response<SignInResponse>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                callback(null)
            }
        })
    }
}