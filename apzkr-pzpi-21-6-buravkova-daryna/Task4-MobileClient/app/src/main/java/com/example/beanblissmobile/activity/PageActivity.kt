package com.example.beanblissmobile.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.beanblissmobile.R
import com.example.beanblissmobile.fragment.CoffeeFragment
import com.example.beanblissmobile.fragment.MachinesFragment
import com.example.beanblissmobile.fragment.ProfileFragment
import com.example.beanblissmobile.databinding.ActivityPageBinding


class PageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            handleBottomNavigation(
                it.itemId
            )
        }
        binding.bottomNavigationView.selectedItemId = R.id.nav_coffee
    }

    private fun handleBottomNavigation(
        menuItemId: Int
    ): Boolean = when(menuItemId) {
        R.id.nav_coffee -> {
            swapFragments(CoffeeFragment())
            true
        }
        R.id.nav_machines -> {
            swapFragments(MachinesFragment())
            true
        }
        R.id.nav_profile -> {
            swapFragments(ProfileFragment())
            true
        }
        else -> false
    }

    private fun swapFragments(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }
}