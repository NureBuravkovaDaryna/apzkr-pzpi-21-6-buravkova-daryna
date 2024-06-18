package com.example.beanblissmobile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.beanblissmobile.databinding.ItemCoffeeBinding
import com.example.beanblissmobile.models.Coffee

class CoffeeDetailAdapter(private val onItemClick: (Coffee) -> Unit) :
    ListAdapter<Coffee, CoffeeDetailAdapter.CoffeeDetailViewHolder>(CoffeeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeDetailViewHolder {
        val binding = ItemCoffeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoffeeDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoffeeDetailViewHolder, position: Int) {
        val coffee = getItem(position)
        holder.bind(coffee)
    }

    inner class CoffeeDetailViewHolder(private val binding: ItemCoffeeBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val coffee = getItem(position)
                    onItemClick(coffee)
                }
            }
        }

        fun bind(coffee: Coffee) {
            binding.coffeeName.text = coffee.name
            binding.coffeeType.text = coffee.type
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
}
