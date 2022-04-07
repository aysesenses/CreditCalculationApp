package com.aysesenses.creditcalculationapp.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aysesenses.creditcalculationapp.R
import com.aysesenses.creditcalculationapp.data.model.PaymentPlanProperty
import com.aysesenses.creditcalculationapp.databinding.ItemPaymentPlanBinding
import com.aysesenses.creditcalculationapp.utils.ValidationUtils

class PaymentPlanAdapter(private val paymentList: ArrayList<PaymentPlanProperty>) :
    RecyclerView.Adapter<PaymentPlanAdapter.PaymentPlanHolder>() {

    inner class PaymentPlanHolder(val binding: ItemPaymentPlanBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: PaymentPlanProperty) {
            binding.apply {
                installmentText.text = ValidationUtils.validateRounding(model.installment)
                interestText.text =  ValidationUtils.validateRounding(model.interest)
                principalText.text = ValidationUtils.validateRounding(model.principal)
                remainingMoneyText.text = ValidationUtils.validateRounding(model.remaining_money)
                maturityText.text =  model.maturity
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentPlanHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPaymentPlanBinding.inflate(layoutInflater, parent, false)
        return PaymentPlanHolder(binding)
    }

    override fun onBindViewHolder(holder: PaymentPlanHolder, position: Int) {
        holder.bind(paymentList[position])
        when {
            position  % 2 != 0 -> holder.itemView.setBackgroundResource(R.color.grey)
            else -> holder.itemView.setBackgroundColor(Color.WHITE)
        }
    }

    override fun getItemCount() = paymentList.size
}