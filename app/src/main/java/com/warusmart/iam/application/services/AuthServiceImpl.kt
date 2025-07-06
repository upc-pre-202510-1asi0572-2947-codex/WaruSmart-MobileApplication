package com.warusmart.iam.application.services

import android.util.Log
import com.warusmart.iam.domain.model.SignInRequest
import com.warusmart.iam.domain.model.SignInResponse
import com.warusmart.iam.domain.model.SignUpRequest
import com.warusmart.iam.domain.model.SignUpResponse
import com.warusmart.iam.infrastructure.AuthService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthServiceImpl(private val authService: AuthService) {

    fun signUp(request: SignUpRequest, callback: (SignUpResponse?) -> Unit) {
        Log.d("Authentication service","Called signup")
        authService.signUp(request).enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.d("Authentication service","Failed signup")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                Log.d("Authentication service", "Error signin: ${t.message}")
                callback(null)
            }
        })
    }

    fun signIn(request: SignInRequest, callback: (SignInResponse?) -> Unit) {
        Log.d("Authentication service","Called signin")
        authService.signIn(request).enqueue(object : Callback<SignInResponse> {
            override fun onResponse(call: Call<SignInResponse>, response: Response<SignInResponse>) {
                if (response.isSuccessful) {
                    Log.d("Authentication service","FSuscesfull signin")
                    callback(response.body())
                } else {
                    Log.d("Authentication service","Failed signin")
                    callback(null)
                }
            }
            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                Log.d("Authentication service", "Error signin: ${t.message}")
                callback(null)
            }
        })
    }
}