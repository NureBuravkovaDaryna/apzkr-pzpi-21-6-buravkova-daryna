package com.example.beanblissmobile.api

import com.example.beanblissmobile.models.User
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    // Отримання даних користувача за його ідентифікатором
    @GET("User/{userId}")
    fun getProfile(@Path("userId") userId: Int): Call<User>

    // Видалення профілю користувача за його ідентифікатором
    @DELETE("User/{userId}")
    fun deleteProfile(@Path("userId") userId: String): Call<Void>
}