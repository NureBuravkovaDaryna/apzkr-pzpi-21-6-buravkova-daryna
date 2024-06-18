package com.example.beanblissmobile.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beanblissmobile.activity.CoffeeDetailActivity
import com.example.beanblissmobile.adapter.CoffeeDetailAdapter
import com.example.beanblissmobile.databinding.FragmentCoffeeBinding
import com.example.beanblissmobile.viewmodel.CoffeeViewModel

class CoffeeFragment : Fragment() {

    private var _binding: FragmentCoffeeBinding? = null
    private val binding get() = _binding!!

    private val coffeeViewModel: CoffeeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCoffeeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CoffeeDetailAdapter { coffee ->
            val intent = Intent(requireContext(), CoffeeDetailActivity::class.java)
            intent.putExtra("coffeeId", coffee.id)
            startActivity(intent)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }

        coffeeViewModel.coffees.observe(viewLifecycleOwner, Observer { coffees ->
            coffees?.let {
                adapter.submitList(it)
            }
        })

        coffeeViewModel.error.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        })

        // Викликаємо метод для отримання кави з API
        coffeeViewModel.fetchCoffees()

        // Додаємо прослуховувач для поля пошуку
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed
            }

            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString().lowercase()
                val filteredList = coffeeViewModel.coffees.value?.filter {
                    it.name.lowercase().contains(searchText)
                }
                adapter.submitList(filteredList)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
