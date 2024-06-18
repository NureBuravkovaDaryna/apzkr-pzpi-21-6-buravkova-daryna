// ProfileViewModel.kt
package com.example.beanblissmobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beanblissmobile.RetrofitClient
import com.example.beanblissmobile.api.ApiService
import com.example.beanblissmobile.models.Coffee
import com.example.beanblissmobile.models.Order
import com.example.beanblissmobile.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {

    private val apiService: ApiService = RetrofitClient.insecure!!.create(ApiService::class.java)

    private val _profile = MutableLiveData<Result<User>>()
    val profile: LiveData<Result<User>> get() = _profile

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> get() = _orders

    fun fetchProfile(userId: Int) {
        apiService.getProfile(userId).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    _profile.postValue(Result.Success(response.body()!!))
                } else {
                    _profile.postValue(Result.Error("Failed to fetch profile: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                _profile.postValue(Result.Error("Failed to fetch profile: ${t.message}"))
            }
        })
    }

    fun fetchOrders(userId: Int) {
        apiService.getUserOrders(userId).enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    val orders = response.body()!!
                    fetchCoffeesForOrders(orders)
                } else {
                    _error.postValue("Failed to fetch orders: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                _error.postValue("Failed to fetch orders: ${t.message}")
            }
        })
    }

    private fun fetchCoffeesForOrders(orders: List<Order>) {
        val updatedOrders = orders.toMutableList()
        val coffeeFetchCount = orders.size
        var completedFetches = 0

        for (order in orders) {
            apiService.getCoffee(order.coffeeId).enqueue(object : Callback<Coffee> {
                override fun onResponse(call: Call<Coffee>, response: Response<Coffee>) {
                    if (response.isSuccessful) {
                        val coffee = response.body()!!
                        order.coffeeName = coffee.name
                    }
                    completedFetches++
                    if (completedFetches == coffeeFetchCount) {
                        _orders.postValue(updatedOrders)
                    }
                }

                override fun onFailure(call: Call<Coffee>, t: Throwable) {
                    completedFetches++
                    if (completedFetches == coffeeFetchCount) {
                        _orders.postValue(updatedOrders)
                    }
                }
            })
        }
    }

    fun updateProfile(user: User) {
        apiService.updateProfile(user).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    _profile.postValue(Result.Success(user))
                } else {
                    _profile.postValue(Result.Error("Failed to update profile: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                _profile.postValue(Result.Error("Failed to update profile: ${t.message}"))
            }
        })
    }

    fun deleteProfile(userId: Int) {
        apiService.deleteProfile(userId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    _profile.postValue(Result.Deleted)
                } else {
                    _profile.postValue(Result.Error("Failed to delete profile: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                _profile.postValue(Result.Error("Failed to delete profile: ${t.message}"))
            }
        })
    }

    sealed class Result<out T> {
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val message: String) : Result<Nothing>()
        object Deleted : Result<Nothing>()
    }
}
