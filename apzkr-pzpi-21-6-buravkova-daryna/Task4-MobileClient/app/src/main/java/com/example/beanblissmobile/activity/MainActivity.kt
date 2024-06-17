package com.example.beanblissmobile.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.beanblissmobile.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Отримуємо посилання на кнопку авторизації
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)

        // Встановлюємо обробник події натискання на кнопку
        buttonLogin.setOnClickListener {
            onLoginButtonClicked()
        }
    }

    private fun onLoginButtonClicked() {
        // Створіть інтент для переходу на LoginActivity
        val intent = Intent(this, LoginActivity::class.java)
        // Здійсніть перехід на LoginActivity
        startActivity(intent)
    }
}