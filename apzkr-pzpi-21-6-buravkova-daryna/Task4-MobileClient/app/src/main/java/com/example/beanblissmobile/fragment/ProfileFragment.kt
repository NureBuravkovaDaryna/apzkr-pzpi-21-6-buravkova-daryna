// ProfileFragment.kt
package com.example.beanblissmobile.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beanblissmobile.R
import com.example.beanblissmobile.adapter.OrderAdapter
import androidx.navigation.fragment.findNavController
import com.example.beanblissmobile.models.User
import com.example.beanblissmobile.viewmodel.ProfileViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var ordersRecyclerView: RecyclerView
    private lateinit var orderAdapter: OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        profileViewModel.profile.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ProfileViewModel.Result.Success -> {
                    displayProfile(result.data)
                }
                is ProfileViewModel.Result.Error -> {
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG).show()
                }
                is ProfileViewModel.Result.Deleted -> {
                    Toast.makeText(requireContext(), "Profile deleted", Toast.LENGTH_LONG).show()
                }
            }
        })

        val userId = getUserIdFromSharedPrefs()
        profileViewModel.fetchProfile(userId)
        profileViewModel.fetchOrders(userId)

        ordersRecyclerView = view.findViewById(R.id.ordersRecyclerView)
        ordersRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        profileViewModel.orders.observe(viewLifecycleOwner, Observer { orders ->
            orderAdapter = OrderAdapter(orders)
            ordersRecyclerView.adapter = orderAdapter
        })

        view.findViewById<Button>(R.id.updateProfileButton).setOnClickListener {
            updateProfile()
        }

        view.findViewById<Button>(R.id.deleteProfileButton).setOnClickListener {
            profileViewModel.deleteProfile(userId)
        }

        view.findViewById<Button>(R.id.exitProfileButton).setOnClickListener {
            exitProfile()
        }
    }

    private fun getUserIdFromSharedPrefs(): Int {
        val sharedPreferences = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("userId", -1)
    }

    private fun displayProfile(profile: User) {
        val birthDate = profile.birthDate.substring(0, 10)
        view?.findViewById<EditText>(R.id.firstNameEditText)?.setText(profile.firstName)
        view?.findViewById<EditText>(R.id.lastNameEditText)?.setText(profile.lastName)
        view?.findViewById<EditText>(R.id.emailEditText)?.setText(profile.email)
        view?.findViewById<EditText>(R.id.birthDateEditText)?.setText(birthDate)
        view?.findViewById<EditText>(R.id.phoneEditText)?.setText(profile.phone)
    }

    private fun updateProfile() {
        val userId = getUserIdFromSharedPrefs()
        val firstName = view?.findViewById<EditText>(R.id.firstNameEditText)?.text.toString()
        val lastName = view?.findViewById<EditText>(R.id.lastNameEditText)?.text.toString()
        val email = view?.findViewById<EditText>(R.id.emailEditText)?.text.toString()
        val birthDate = view?.findViewById<EditText>(R.id.birthDateEditText)?.text.toString()
        val phone = view?.findViewById<EditText>(R.id.phoneEditText)?.text.toString()

        val updatedUser = User(id = userId, firstName = firstName, lastName = lastName, email = email, birthDate = birthDate, phone = phone)
        profileViewModel.updateProfile(updatedUser)
    }

    private fun exitProfile() {
        // Перехід на головну сторінку
        findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
    }
}
