package com.aysesenses.creditcalculationapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aysesenses.creditcalculationapp.data.model.DepositProperty
import com.aysesenses.creditcalculationapp.databinding.ItemDepositBinding

class DepositAdapter( private val onClickListener: OnClickListener) : ListAdapter<DepositProperty, DepositAdapter.DepositViewHolder>(DiffCallback) {
    class DepositViewHolder(private var binding: ItemDepositBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(depositProperty: DepositProperty) {
            binding.property = depositProperty
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepositViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemDepositBinding.inflate(layoutInflater, parent, false)
        return DepositViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DepositViewHolder, position: Int) {
        val depositProperty = getItem(position)
        holder.itemView.setOnClickListener { onClickListener.onClick(depositProperty) }
        holder.bind(depositProperty)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DepositProperty>() {
        override fun areItemsTheSame(oldItem: DepositProperty, newItem: DepositProperty): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DepositProperty, newItem: DepositProperty): Boolean {
            return oldItem.bank_id == newItem.bank_id
        }
    }

    class OnClickListener(val clickListener: (depositProperty: DepositProperty) -> Unit) {
        fun onClick(depositProperty: DepositProperty) = clickListener(depositProperty)
    }
}