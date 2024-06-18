// MachinesFragment.kt
package com.example.beanblissmobile.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beanblissmobile.R
import com.example.beanblissmobile.activity.DrinksActivity
import com.example.beanblissmobile.adapter.MachineAdapter
import com.example.beanblissmobile.viewmodel.MachineViewModel

class MachinesFragment : Fragment() {

    private lateinit var machineViewModel: MachineViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var locationSpinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_machines, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        locationSpinner = view.findViewById(R.id.regionSpinner)

        machineViewModel = ViewModelProvider(this).get(MachineViewModel::class.java)

        machineViewModel.fetchMachines()
        machineViewModel.fetchLocations()

        val adapter = MachineAdapter { machine ->
            val intent = Intent(requireContext(), DrinksActivity::class.java)
            intent.putExtra("machineId", machine.id)
            intent.putExtra("machineName", machine.name)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        machineViewModel.machines.observe(viewLifecycleOwner, Observer { machines ->
            machines?.let {
                adapter.submitList(it)
            }
        })

        machineViewModel.locations.observe(viewLifecycleOwner, Observer { locations ->
            locations?.let {
                val locationAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, it.map { location ->
                    "${location.city}, ${location.country}"
                })
                locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                locationSpinner.adapter = locationAdapter
            }
        })

        locationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedLocation = machineViewModel.locations.value?.get(position)?.id.toString()
                machineViewModel.filterByLocation(selectedLocation)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Нічого не робити при відсутності вибору
            }
        }

        machineViewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
