// DrinksViewModel.kt
package com.example.beanblissmobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beanblissmobile.RetrofitClient
import com.example.beanblissmobile.api.ApiService
import com.example.beanblissmobile.models.Coffee
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrinksViewModel : ViewModel() {

    private val _drinks = MutableLiveData<List<Coffee>>()
    val drinks: LiveData<List<Coffee>> get() = _drinks

    private val apiService: ApiService = RetrofitClient.insecure!!.create(ApiService::class.java)

    fun fetchDrinks(machineId: Int) {
        apiService.getCoffeesByMachine(machineId).enqueue(object : Callback<List<Coffee>> {
            override fun onResponse(call: Call<List<Coffee>>, response: Response<List<Coffee>>) {
                if (response.isSuccessful) {
                    _drinks.postValue(response.body())
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<List<Coffee>>, t: Throwable) {
                // Handle failure
            }
        })
    }
}

