package com.example.beanblissmobile.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val userId: String, val role: String)
data class RegisterRequest(val email: String, val password: String, val confirmPassword: String, val role: String)

interface AuthApi {
    @POST("api/Auth/Login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("api/Auth/Register")
    fun register(@Body request: RegisterRequest): Call<Void>
}


