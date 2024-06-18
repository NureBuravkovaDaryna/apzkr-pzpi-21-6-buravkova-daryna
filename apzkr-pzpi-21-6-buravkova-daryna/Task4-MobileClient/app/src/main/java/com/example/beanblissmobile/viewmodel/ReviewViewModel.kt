package com.example.beanblissmobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beanblissmobile.api.ApiService
import com.example.beanblissmobile.RetrofitClient
import com.example.beanblissmobile.models.Review
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewViewModel : ViewModel() {
    private val _review = MutableLiveData<Review?>()
    val review: LiveData<Review?> = _review

}
