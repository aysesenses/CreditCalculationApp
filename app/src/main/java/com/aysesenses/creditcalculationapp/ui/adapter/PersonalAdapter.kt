package com.aysesenses.creditcalculationapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aysesenses.creditcalculationapp.data.model.BankProperty
import com.aysesenses.creditcalculationapp.databinding.ItemPersonalBinding

class PersonalAdapter (private val onClickListener: OnClickListener) : ListAdapter<BankProperty, PersonalAdapter.BankPropertyViewHolder>(DiffCallback) {

    class BankPropertyViewHolder(private var binding: ItemPersonalBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bankProperty: BankProperty) {
            binding.property = bankProperty
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankPropertyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPersonalBinding.inflate(layoutInflater, parent, false)
        return BankPropertyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BankPropertyViewHolder, position: Int) {
        val bankProperty = getItem(position)
        holder.itemView.setOnClickListener { onClickListener.onClick(bankProperty) }
        holder.bind(bankProperty)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<BankProperty>() {
        override fun areItemsTheSame(oldItem: BankProperty, newItem: BankProperty): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: BankProperty, newItem: BankProperty): Boolean {
            return oldItem.bank_id == newItem.bank_id
        }
    }

    class OnClickListener(val clickListener: (bankProperty: BankProperty) -> Unit) {
        fun onClick(bankProperty: BankProperty) = clickListener(bankProperty)
    }
}
