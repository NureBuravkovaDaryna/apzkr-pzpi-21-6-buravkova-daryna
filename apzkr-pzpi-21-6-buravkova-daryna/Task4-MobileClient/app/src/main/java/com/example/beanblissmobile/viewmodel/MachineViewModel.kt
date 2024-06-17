package com.example.beanblissmobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beanblissmobile.models.Location
import com.example.beanblissmobile.models.Machine
import com.google.gson.Gson

class MachineViewModel : ViewModel() {

    private val _originalMachines = MutableLiveData<List<Machine>>()
    private val _machines = MutableLiveData<List<Machine>?>()
    val machines: LiveData<List<Machine>?> get() = _machines

    private val _locations = MutableLiveData<List<Location>>()
    val locations: LiveData<List<Location>> get() = _locations

    fun fetchMachines() {
        val machinesJson = """
            [
              {
                "id": 5,
                "name": "Автомат #68",
                "address": "вул. Козацька 73",
                "isWorking": true,
                "regionId": 2
              },
              {
                "id": 1004,
                "name": "Автомат #47",
                "address": "вул. Шевченка 42",
                "isWorking": true,
                "regionId": 1002
              },
              {
                "id": 2014,
                "name": "Автомат #103",
                "address": "вул. Надпільна 23",
                "isWorking": true,
                "regionId": 2
              },
              {
                "id": 2015,
                "name": "Автомат #609",
                "address": "вул. Центральна 46",
                "isWorking": true,
                "regionId": 1010
              },
              {
                "id": 2016,
                "name": "Автомат 200",
                "address": "вул. Косова 67",
                "isWorking": true,
                "regionId": 1
              },
              {
                "id": 2017,
                "name": "Автомат 99",
                "address": "вул. Чорновола 32",
                "isWorking": true,
                "regionId": 2012
              }
            ]
        """.trimIndent()

        val machinesList = Gson().fromJson(machinesJson, Array<Machine>::class.java).toList()
        _originalMachines.postValue(machinesList)
        _machines.postValue(machinesList)
    }

    fun fetchLocations() {
        // Заглушкові дані для локацій
        val locationsJson = """
            [
              {
                "id": 1,
                "city": "Київ",
                "country": "Україна"
              },
              {
                "id": 2,
                "city": "Полтава",
                "country": "Україна"
              },
              {
                "id": 1002,
                "city": "Черкаси",
                "country": "Україна"
              },
              {
                "id": 1010,
                "city": "Харків",
                "country": "Україна"
              },
              {
                "id": 2012,
                "city": "Львів",
                "country": "Україна"
              }
            ]
        """.trimIndent()

        val locationsList = Gson().fromJson(locationsJson, Array<Location>::class.java).toList()
        _locations.postValue(locationsList)
    }

    fun filterByLocation(locationId: String) {
        val originalList = _originalMachines.value
        val filteredList = originalList?.filter { machine ->
            machine.regionId == locationId.toIntOrNull()
        }
        _machines.postValue(filteredList)
    }
}


