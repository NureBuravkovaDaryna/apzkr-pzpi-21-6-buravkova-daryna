package com.example.beanblissmobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beanblissmobile.models.Coffee
import com.example.beanblissmobile.api.ApiService
import com.example.beanblissmobile.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoffeeViewModel : ViewModel() {

    private val apiService: ApiService = RetrofitClient.insecure!!.create(ApiService::class.java)

    private val _coffees = MutableLiveData<List<Coffee>>()
    val coffees: LiveData<List<Coffee>> get() = _coffees

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchCoffees() {
        apiService.getCoffees().enqueue(object : Callback<List<Coffee>> {
            override fun onResponse(call: Call<List<Coffee>>, response: Response<List<Coffee>>) {
                if (response.isSuccessful) {
                    _coffees.postValue(response.body())
                } else {
                    _error.postValue("Failed to fetch coffees")
                }
            }

            override fun onFailure(call: Call<List<Coffee>>, t: Throwable) {
                _error.postValue("Error: ${t.message}")
            }
        })
    }
}
