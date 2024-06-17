package com.example.beanblissmobile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.beanblissmobile.models.Coffee
import com.example.beanblissmobile.databinding.ItemCoffeeBinding

class CoffeeAdapter : ListAdapter<Coffee, CoffeeAdapter.CoffeeViewHolder>(CoffeeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeViewHolder {
        val binding = ItemCoffeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoffeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoffeeViewHolder, position: Int) {
        val coffee = getItem(position)
        holder.bind(coffee)
    }

    class CoffeeViewHolder(private val binding: ItemCoffeeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(coffee: Coffee) {
            binding.coffeeName.text = coffee.name
            binding.coffeeType.text = coffee.type
        }
    }
}

class CoffeeDiffCallback : DiffUtil.ItemCallback<Coffee>() {
    override fun areItemsTheSame(oldItem: Coffee, newItem: Coffee): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Coffee, newItem: Coffee): Boolean {
        return oldItem == newItem
    }
}