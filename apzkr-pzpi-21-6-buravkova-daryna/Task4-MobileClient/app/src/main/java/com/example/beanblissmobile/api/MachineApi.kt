package com.example.beanblissmobile.api

import com.example.beanblissmobile.models.Location
import com.example.beanblissmobile.models.Machine
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MachineApi {
    @GET("api/locations")
    fun getLocations(): Call<List<Location>>

    @GET("api/machines")
    fun getMachines(): Call<List<Machine>>

    @GET("api/machines/{locationId}")
    fun getMachinesByLocation(@Path("locationId") locationId: String): Call<List<Machine>>
}