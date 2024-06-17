package com.example.beanblissmobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beanblissmobile.models.User
import com.google.gson.Gson

class ProfileViewModel : ViewModel() {

    private val _profile = MutableLiveData<User>()
    val profile: LiveData<User> get() = _profile

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchProfile(userId: Int) {
        val profileJson = """
            {
                "id": 1,
                "firstName": "Наталія",
                "lastName": "Тернер",
                "email": "nata@gmail.com",
                "birthDate": "1997-04-18",
                "phone": "06952432178"
            }
        """.trimIndent()

        val profile = Gson().fromJson(profileJson, User::class.java)
        _profile.postValue(profile)
    }
}

