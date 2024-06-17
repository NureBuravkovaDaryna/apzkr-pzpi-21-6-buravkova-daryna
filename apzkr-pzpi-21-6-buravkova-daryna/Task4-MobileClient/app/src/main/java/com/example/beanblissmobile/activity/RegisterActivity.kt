package com.example.beanblissmobile.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.beanblissmobile.R
import com.example.beanblissmobile.api.AuthApi
import com.example.beanblissmobile.api.RegisterRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var authApi: AuthApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://localhost:7256/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        authApi = retrofit.create(AuthApi::class.java)

        val buttonRegister = findViewById<Button>(R.id.buttonRegister)
        buttonRegister.setOnClickListener {
            register()
        }

        val textViewLoginLink = findViewById<TextView>(R.id.textViewLoginLink)
        textViewLoginLink.setOnClickListener {
            // Перехід на сторінку авторизації
            onBackPressed()
        }
    }

    private fun register() {
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val email = editTextEmail.text.toString()

        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val password = editTextPassword.text.toString()

        val editTextConfirmPassword = findViewById<EditText>(R.id.editTextConfirmPassword)
        val confirmPassword = editTextConfirmPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
            if (password == confirmPassword) {
                val registerRequest = RegisterRequest(email, password, confirmPassword, "User")

                authApi.register(registerRequest).enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@RegisterActivity, "Registration successful", Toast.LENGTH_SHORT).show()
                            // Очищення полів вводу
                            editTextEmail.setText("")
                            editTextPassword.setText("")
                            editTextConfirmPassword.setText("")
                        } else {
                            Toast.makeText(this@RegisterActivity, "Registration failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(this@RegisterActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }
}