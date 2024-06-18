package com.example.beanblissmobile.models

data class Order(
    val id: Int,
    val orderDate: String,
    val orderStatus: String,
    val isPaid: Boolean,
    val price: Int,
    val paymentDetail: String,
    val coffeeId: Int,
    var coffeeName: String? = null // Додаємо змінну для назви кави
)
