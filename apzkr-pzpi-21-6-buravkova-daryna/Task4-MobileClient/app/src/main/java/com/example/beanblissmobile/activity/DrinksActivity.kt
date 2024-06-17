package com.example.beanblissmobile.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.beanblissmobile.R
import com.example.beanblissmobile.models.Coffee
import com.example.beanblissmobile.viewmodel.DrinksViewModel

class DrinksActivity : AppCompatActivity() {

    private lateinit var drinksViewModel: DrinksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drinks)

        val machineId = intent.getIntExtra("machineId", -1)
        val machineName = intent.getStringExtra("machineName")

        val titleTextView: TextView = findViewById(R.id.machineTitleTextView)
        val drinksContainer: LinearLayout = findViewById(R.id.drinksContainer)

        titleTextView.text = "Напої для машини $machineName"

        drinksViewModel = ViewModelProvider(this).get(DrinksViewModel::class.java)

        // Спостереження за списком напоїв
        drinksViewModel.drinks.observe(this, Observer { drinks ->
            drinks?.let {
                displayDrinks(drinksContainer, drinks)
            }
        })

        // Отримання списку напоїв для вибраної машини
        drinksViewModel.fetchDrinks(machineId)
    }

    private fun displayDrinks(drinksContainer: LinearLayout, drinks: List<Coffee>) {
        for (drink in drinks) {
            val drinkView = LayoutInflater.from(this).inflate(R.layout.item_drink, drinksContainer, false)

            val drinkNameTextView: TextView = drinkView.findViewById(R.id.drinkNameTextView)
            val drinkVolumeTextView: TextView = drinkView.findViewById(R.id.drinkVolumeTextView)
            val drinkPriceTextView: TextView = drinkView.findViewById(R.id.drinkPriceTextView)
            val orderButton: Button = drinkView.findViewById(R.id.orderButton)

            drinkNameTextView.text = drink.name
            drinkVolumeTextView.text = "${drink.bulk} мл"
            drinkPriceTextView.text = "${drink.price} грн"
            orderButton.setOnClickListener {
                // Handle order action
                orderDrink(drink.id)
            }

            drinksContainer.addView(drinkView)
        }
    }

    private fun orderDrink(drinkId: Int) {
        // Handle the order logic
        // For now, just show a toast or a log message
        Toast.makeText(this, "Замовлення оброблюється", Toast.LENGTH_SHORT).show()
    }
}
