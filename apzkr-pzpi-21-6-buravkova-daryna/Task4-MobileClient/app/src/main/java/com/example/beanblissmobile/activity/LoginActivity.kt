package com.example.beanblissmobile.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.beanblissmobile.R
import com.example.beanblissmobile.api.ApiService
import com.example.beanblissmobile.api.LoginRequest
import com.example.beanblissmobile.api.LoginResponse
import com.example.beanblissmobile.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var authApi: ApiService // Виправлено орфографічну помилку в назві

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        authApi = RetrofitClient.insecure!!.create(ApiService::class.java) // Використання RetrofitClient

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
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val email = editTextEmail.text.toString()

        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val password = editTextPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            val loginRequest = LoginRequest(email, password)

            authApi.login(loginRequest).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        if (loginResponse != null) {
                            val userId = loginResponse.userId
                            val role = loginResponse.role
                            getSharedPreferences("app_prefs", MODE_PRIVATE).edit().putInt("userId", userId).apply()
                            Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()
                            when (role) {
                                "User" -> {
                                    val intent = Intent(this@LoginActivity, PageActivity::class.java)
                                    startActivity(intent)
                                }
                                else -> {
                                    Toast.makeText(this@LoginActivity, "Unknown role", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
        }
    }
}
