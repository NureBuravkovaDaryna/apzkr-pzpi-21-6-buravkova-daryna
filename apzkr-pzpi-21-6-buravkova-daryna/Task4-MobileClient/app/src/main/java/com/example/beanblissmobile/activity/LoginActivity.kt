package com.example.beanblissmobile.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.beanblissmobile.R
import com.example.beanblissmobile.api.AuthApi

class LoginActivity : AppCompatActivity() {

    private lateinit var authApi: AuthApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        buttonLogin.setOnClickListener {
            login()
        }

        val textViewRegisterLink = findViewById<TextView>(R.id.textViewRegisterLink)
        textViewRegisterLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        val intent = Intent(this, PageActivity::class.java)
        startActivity(intent)
    }
}