package com.example.beanblissmobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beanblissmobile.models.Coffee
import com.google.gson.Gson


class CoffeeViewModel : ViewModel() {

    private val _coffees = MutableLiveData<List<Coffee>>()
    val coffees: LiveData<List<Coffee>> get() = _coffees

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchCoffees() {
        val coffeesJson = """
            [
                {
                    "id": 4,
                    "name": "Латте на мигдалевому",
                    "type": "Латте",
                    "bulk": 100,
                    "price": 60,
                    "description": "Для людей з лактозною непереносимістью"
                  },
                  {
                    "id": 6,
                    "name": "Капучино",
                    "type": "Капучино",
                    "bulk": 150,
                    "price": 40,
                    "description": "Звичайне капучино з вершками"
                  },
                  {
                    "id": 7,
                    "name": "Оранж",
                    "type": "Єспрессо",
                    "bulk": 200,
                    "price": 50,
                    "description": "Цитрусові нотки"
                  },
                  {
                    "id": 8,
                    "name": "Айс латте",
                    "type": "латте",
                    "bulk": 200,
                    "price": 50,
                    "description": "На спеку"
                  },
                  {
                    "id": 10,
                    "name": "Амерекано",
                    "type": "Амерекано",
                    "bulk": 100,
                    "price": 40,
                    "description": "Звичайна кава"
                  },
                  {
                    "id": 1008,
                    "name": "Мокіато",
                    "type": "латте",
                    "bulk": 200,
                    "price": 65,
                    "description": "Дуже смачно"
                  },
                  {
                    "id": 1009,
                    "name": "Флєтвайт",
                    "type": "Еспресо",
                    "bulk": 200,
                    "price": 65,
                    "description": "Вам точно сподобається"
                  },
                  {
                    "id": 1010,
                    "name": "Ристретто",
                    "type": "Еспресо",
                    "bulk": 150,
                    "price": 55,
                    "description": "Для гурманів"
                  }
            ]
        """.trimIndent()

        val coffeesList = Gson().fromJson(coffeesJson, Array<Coffee>::class.java).toList()
        _coffees.postValue(coffeesList)
    }
}

