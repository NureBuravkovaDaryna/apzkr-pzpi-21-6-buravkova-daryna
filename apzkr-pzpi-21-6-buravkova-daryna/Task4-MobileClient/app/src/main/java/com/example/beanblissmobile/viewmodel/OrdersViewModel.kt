package com.example.beanblissmobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beanblissmobile.models.Order
import com.example.beanblissmobile.api.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrdersViewModel(private val apiService: ApiService) : ViewModel() {

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> get() = _orders

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchOrders(userId: Int) {
        apiService.getUserOrders(userId).enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    _orders.postValue(response.body())
                } else {
                    _error.postValue("Failed to fetch orders: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                _error.postValue("Failed to fetch orders: ${t.message}")
            }
        })
    }
}
