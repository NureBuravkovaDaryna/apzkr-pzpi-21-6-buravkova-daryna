// MachineViewModel.kt
package com.example.beanblissmobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beanblissmobile.RetrofitClient
import com.example.beanblissmobile.api.ApiService
import com.example.beanblissmobile.models.Location
import com.example.beanblissmobile.models.Machine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MachineViewModel : ViewModel() {

    private val apiService = RetrofitClient.insecure!!.create(ApiService::class.java)

    private val _originalMachines = MutableLiveData<List<Machine>>()
    private val _machines = MutableLiveData<List<Machine>?>()
    val machines: LiveData<List<Machine>?> get() = _machines

    private val _locations = MutableLiveData<List<Location>>()
    val locations: LiveData<List<Location>> get() = _locations

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchMachines() {
        apiService.getMachines().enqueue(object : Callback<List<Machine>> {
            override fun onResponse(call: Call<List<Machine>>, response: Response<List<Machine>>) {
                if (response.isSuccessful) {
                    val machinesList = response.body()
                    machinesList?.let {
                        _originalMachines.postValue(it)
                        _machines.postValue(it)
                    } ?: run {
                        _error.postValue("Received empty machines list")
                    }
                } else {
                    _error.postValue("Failed to fetch machines: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Machine>>, t: Throwable) {
                _error.postValue("Failed to fetch machines: ${t.message}")
            }
        })
    }

    fun fetchLocations() {
        apiService.getLocations().enqueue(object : Callback<List<Location>> {
            override fun onResponse(call: Call<List<Location>>, response: Response<List<Location>>) {
                if (response.isSuccessful) {
                    val locationsList = response.body()
                    locationsList?.let {
                        _locations.postValue(it)
                    } ?: run {
                        _error.postValue("Received empty locations list")
                    }
                } else {
                    _error.postValue("Failed to fetch locations: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Location>>, t: Throwable) {
                _error.postValue("Failed to fetch locations: ${t.message}")
            }
        })
    }

    fun filterByLocation(locationId: String) {
        val originalList = _originalMachines.value
        val filteredList = originalList?.filter { machine ->
            machine.regionId == locationId.toIntOrNull()
        }
        _machines.postValue(filteredList)
    }
}

