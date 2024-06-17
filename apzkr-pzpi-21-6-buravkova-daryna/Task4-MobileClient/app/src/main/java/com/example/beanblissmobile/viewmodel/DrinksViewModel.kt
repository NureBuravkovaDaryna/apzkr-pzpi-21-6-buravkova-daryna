package com.example.beanblissmobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beanblissmobile.models.Coffee
import com.google.gson.Gson

class DrinksViewModel : ViewModel() {

    private val _drinks = MutableLiveData<List<Coffee>>()
    val drinks: LiveData<List<Coffee>> get() = _drinks

    fun fetchDrinks(machineId: Int) {
        // Симуляція запиту
        val drinksJson = """
            [
              {
                "id": 1,
                "name": "Капучино",
                "bulk": 150,
                "price": 40
              },
              {
                "id": 2,
                "name": "Оранж",
                "bulk": 200,
                "price": 50
              },
              {
                "id": 3,
                "name": "Айс латте",
                "bulk": 200,
                "price": 50
              }
            ]
        """.trimIndent()

        val drinksList = Gson().fromJson(drinksJson, Array<Coffee>::class.java).toList()
        _drinks.postValue(drinksList)
    }
}
