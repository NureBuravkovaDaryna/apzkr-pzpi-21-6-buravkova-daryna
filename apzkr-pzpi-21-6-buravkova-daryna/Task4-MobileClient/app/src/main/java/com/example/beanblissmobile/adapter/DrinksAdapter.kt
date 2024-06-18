package com.example.beanblissmobile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.beanblissmobile.models.Coffee
import com.example.beanblissmobile.R

class DrinksAdapter(private val onOrderClick: (Coffee) -> Unit) :
    ListAdapter<Coffee, DrinksAdapter.DrinkViewHolder>(DrinkDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_drink, parent, false)
        return DrinkViewHolder(view)
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val coffee = getItem(position)
        holder.bind(coffee, onOrderClick)
    }

    class DrinkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.drinkNameTextView)
        private val priceTextView: TextView = itemView.findViewById(R.id.drinkPriceTextView)
        private val volumeTextView: TextView = itemView.findViewById(R.id.drinkVolumeTextView)
        private val orderButton: Button = itemView.findViewById(R.id.orderButton)

        fun bind(coffee: Coffee, onOrderClick: (Coffee) -> Unit) {
            nameTextView.text = coffee.name
            volumeTextView.text = "Об'єм: ${coffee.bulk} мл"
            priceTextView.text = "${coffee.price} грн."
            orderButton.setOnClickListener { onOrderClick(coffee) }
        }
    }

    private class DrinkDiffCallback : DiffUtil.ItemCallback<Coffee>() {
        override fun areItemsTheSame(oldItem: Coffee, newItem: Coffee): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Coffee, newItem: Coffee): Boolean {
            return oldItem == newItem
        }
    }
}
