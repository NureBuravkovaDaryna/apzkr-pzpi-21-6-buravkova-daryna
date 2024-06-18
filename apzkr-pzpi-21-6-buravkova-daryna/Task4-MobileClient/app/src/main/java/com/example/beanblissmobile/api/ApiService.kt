package com.example.beanblissmobile.api

import com.example.beanblissmobile.models.Coffee
import com.example.beanblissmobile.models.Location
import com.example.beanblissmobile.models.Machine
import com.example.beanblissmobile.models.Order
import com.example.beanblissmobile.models.Review
import com.example.beanblissmobile.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val userId: Int, val role: String)
data class RegisterRequest(val email: String, val password: String, val confirmPassword: String, val role: String)

interface ApiService {
    @POST("api/Auth/Login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("api/Auth/Register")
    fun register(@Body request: RegisterRequest): Call<Void>

    @GET("api/Coffee")
    fun getCoffees(): Call<List<Coffee>>

    @GET("api/Region")
    fun getLocations(): Call<List<Location>>

    @GET("api/Machine")
    fun getMachines(): Call<List<Machine>>

    @GET("api/Machine/Region/{locationId}")
    fun getMachinesByLocation(@Path("locationId") locationId: String): Call<List<Machine>>

    @GET("api/User/{userId}")
    fun getProfile(@Path("userId") userId: Int): Call<User>

    @PUT("api/User")
    fun updateProfile(@Body user: User): Call<Void>

    @DELETE("api/User/{userId}")
    fun deleteProfile(@Path("userId") userId: Int): Call<Void>

    @GET("api/Coffee/CofeeMachine/{machineId}")
    fun getCoffeesByMachine(@Path("machineId") machineId: Int): Call<List<Coffee>>

    @GET("api/Order/user/{userId}")
    fun getUserOrders(@Path("userId") userId: Int): Call<List<Order>>

    @GET("api/Coffee/{id}")
    fun getCoffee(@Path("id") coffeeId: Int): Call<Coffee>

    @GET("api/Review/AverageRating/{coffeeId}")
    fun getAverageRating(@Path("coffeeId") coffeeId: Int): Call<Double>
}