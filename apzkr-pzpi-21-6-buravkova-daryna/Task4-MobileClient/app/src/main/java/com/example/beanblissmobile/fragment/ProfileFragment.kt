package com.example.beanblissmobile.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.beanblissmobile.models.User
import com.example.beanblissmobile.viewmodel.ProfileViewModel
import com.example.beanblissmobile.R

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        profileViewModel.profile.observe(viewLifecycleOwner, Observer { profile ->
            profile?.let {
                displayProfile(it)
            }
        })

        val userId = 1 // Отримайте або встановіть id користувача
        profileViewModel.fetchProfile(userId)

    }

    private fun displayProfile(profile: User) {
        view?.findViewById<TextView>(R.id.usernameTextView)?.text = "Ім'я: ${profile.firstName} ${profile.lastName}"
        view?.findViewById<TextView>(R.id.lastNameTextView)?.text = "Прізвище: ${profile.lastName}"
        view?.findViewById<TextView>(R.id.emailTextView)?.text = "Email: ${profile.email}"
        view?.findViewById<TextView>(R.id.birthDateTextView)?.text = "Дата народження: ${profile.birthDate}"
        view?.findViewById<TextView>(R.id.phoneTextView)?.text = "Номер телефону: ${profile.phone}"
    }
}
