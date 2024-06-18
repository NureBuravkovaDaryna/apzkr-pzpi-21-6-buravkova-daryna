package com.example.beanblissmobile.activity

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.beanblissmobile.R
import com.example.beanblissmobile.api.ApiService
import com.example.beanblissmobile.models.Coffee
import com.example.beanblissmobile.RetrofitClient
import com.example.beanblissmobile.models.Review
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoffeeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coffee_detail)

        val coffeeId = intent.getIntExtra("coffeeId", -1)
        if (coffeeId == -1) {
            Toast.makeText(this, "Coffee ID not found", Toast.LENGTH_SHORT).show()
            finish()
        }

        val apiService = RetrofitClient.insecure?.create(ApiService::class.java)
        val call = apiService?.getCoffee(coffeeId)

        call?.enqueue(object : Callback<Coffee> {
            override fun onResponse(call: Call<Coffee>, response: Response<Coffee>) {
                if (response.isSuccessful) {
                    val coffee = response.body()
                    coffee?.let {
                        displayCoffeeDetails(it)
                    } ?: run {
                        Toast.makeText(this@CoffeeDetailActivity, "Coffee details not found", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                } else {
                    Toast.makeText(this@CoffeeDetailActivity, "Failed to fetch coffee details", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<Coffee>, t: Throwable) {
                Toast.makeText(this@CoffeeDetailActivity, "Network error", Toast.LENGTH_SHORT).show()
                finish()
            }
        })

        val ratingCall = apiService?.getAverageRating(coffeeId)

        ratingCall?.enqueue(object : Callback<Double> {
            override fun onResponse(call: Call<Double>, response: Response<Double>) {
                if (response.isSuccessful) {
                    val rating = response.body()
                    rating?.let {
                        displayRating(it)
                    } ?: run {
                        Toast.makeText(this@CoffeeDetailActivity, "Rating not found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@CoffeeDetailActivity, "Failed to fetch rating", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Double>, t: Throwable) {
                Toast.makeText(this@CoffeeDetailActivity, "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displayCoffeeDetails(coffee: Coffee) {
        findViewById<TextView>(R.id.coffeeNameTextView).text = "Назва: ${coffee.name}"
        findViewById<TextView>(R.id.coffeeTypeTextView).text = "Тип: ${coffee.type}"
        findViewById<TextView>(R.id.coffeeBulkTextView).text = "Об'єм: ${coffee.bulk} мл"
        findViewById<TextView>(R.id.coffeePriceTextView).text = "Ціна: ${coffee.price} грн"
        findViewById<TextView>(R.id.coffeeDescriptionTextView).text = "Опис: ${coffee.description}"
    }

    private fun displayRating(rating: Double) {
        findViewById<TextView>(R.id.coffeeRatingTextView).text = "Рейтинг: $rating"
    }
}
