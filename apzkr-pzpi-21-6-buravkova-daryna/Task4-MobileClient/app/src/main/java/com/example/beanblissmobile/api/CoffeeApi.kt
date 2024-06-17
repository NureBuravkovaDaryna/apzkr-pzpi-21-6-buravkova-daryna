package com.example.beanblissmobile.api

import com.example.beanblissmobile.models.Coffee
import retrofit2.Call
import retrofit2.http.GET

interface CoffeeApi {
    @GET("api/Coffee")
    fun getCoffees(): Call<List<Coffee>>
}