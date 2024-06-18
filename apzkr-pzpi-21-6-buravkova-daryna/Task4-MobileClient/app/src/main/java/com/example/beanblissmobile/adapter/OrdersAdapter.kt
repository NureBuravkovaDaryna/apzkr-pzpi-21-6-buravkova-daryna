// OrderAdapter.kt
package com.example.beanblissmobile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beanblissmobile.R
import com.example.beanblissmobile.models.Order
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class OrderAdapter(private val orders: List<Order>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.bind(order)
    }

    override fun getItemCount(): Int = orders.size

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val orderIdTextView: TextView = itemView.findViewById(R.id.orderIdTextView)
        private val coffeeNameTextView: TextView = itemView.findViewById(R.id.coffeeNameTextView)
        private val orderDateTextView: TextView = itemView.findViewById(R.id.orderDateTextView)

        private fun formatDate(dateString: String): String {
            return try {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
                val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale("uk", "UA"))
                val date = inputFormat.parse(dateString)
                outputFormat.format(date)
            } catch (e: ParseException) {
                // Якщо дата не може бути розпізнана, повертаємо вхідний рядок
                dateString
            }
        }

        fun bind(order: Order) {
            orderIdTextView.text = "Номер замовлення: ${order.id}"
            coffeeNameTextView.text = "Напій: ${order.coffeeName ?: "Невідомо"}"
            orderDateTextView.text = "Дата: ${formatDate(order.orderDate)}"
        }
    }
}

