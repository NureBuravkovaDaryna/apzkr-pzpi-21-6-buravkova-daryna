package com.example.beanblissmobile.models

data class Order(
    val id: Int,
    val coffeeName: String,
    val orderDate: String,
    val price: Double?
)
