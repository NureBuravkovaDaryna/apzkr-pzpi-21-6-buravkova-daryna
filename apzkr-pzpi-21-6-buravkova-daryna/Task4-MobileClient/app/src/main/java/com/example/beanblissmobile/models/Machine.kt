package com.example.beanblissmobile.models

data class Machine(
    val id: Int,
    val name: String,
    val address: String,
    val regionId: Int,
    val isWorking: Boolean
)
