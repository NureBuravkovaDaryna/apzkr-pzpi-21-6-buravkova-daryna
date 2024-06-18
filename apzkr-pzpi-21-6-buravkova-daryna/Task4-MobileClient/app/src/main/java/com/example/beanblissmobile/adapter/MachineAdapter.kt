package com.example.beanblissmobile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.beanblissmobile.models.Machine
import com.example.beanblissmobile.R

class MachineAdapter(private val onItemClick: (Machine) -> Unit) :
    ListAdapter<Machine, MachineAdapter.MachineViewHolder>(MachineDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MachineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_machine, parent, false)
        return MachineViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: MachineViewHolder, position: Int) {
        val machine = getItem(position)
        holder.bind(machine)
    }

    class MachineViewHolder(itemView: View, val onItemClick: (Machine) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val addressTextView: TextView = itemView.findViewById(R.id.addressTextView)
        private val statusTextView: TextView = itemView.findViewById(R.id.statusTextView)

        fun bind(machine: Machine) {
            nameTextView.text = machine.name
            addressTextView.text = machine.address
            statusTextView.text = if (machine.isWorking) "Працює" else "Не працює"

            itemView.setOnClickListener {
                onItemClick(machine)
            }
        }
    }

    private class MachineDiffCallback : DiffUtil.ItemCallback<Machine>() {
        override fun areItemsTheSame(oldItem: Machine, newItem: Machine): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Machine, newItem: Machine): Boolean {
            return oldItem == newItem
        }
    }
}
