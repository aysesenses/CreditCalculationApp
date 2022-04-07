package com.aysesenses.creditcalculationapp.ui.view.activities.deposit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.aysesenses.creditcalculationapp.data.model.DepositProperty
import com.aysesenses.creditcalculationapp.databinding.ActivityDepositLoanOfferBinding
import com.aysesenses.creditcalculationapp.ui.adapter.DepositAdapter
import com.aysesenses.creditcalculationapp.ui.adapter.bindRecyclerViewDeposit
import com.aysesenses.creditcalculationapp.ui.viewmodel.deposit.DepositLoanOfferViewModel

class DepositLoanOfferActivity : AppCompatActivity() {
    private var viewModel: DepositLoanOfferViewModel? = null

    private var _binding: ActivityDepositLoanOfferBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDepositLoanOfferBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Mevduat"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this).get(DepositLoanOfferViewModel::class.java)

        // Giving the binding access to the OverviewViewModel
        binding.viewmodel = viewModel
        viewModel?.getDepositProperties()

        val loanAmount: String = intent.getStringExtra("loan_amount").toString()
        val maturity: String = intent.getStringExtra("maturity").toString()

        viewModel?.bankList?.observe(this, {
            val newData = mutableListOf<DepositProperty>()
            it.forEach {
                val income = it.bank_interest?.let { interest ->
                    viewModel?.netIncome(
                        loanAmount.toInt(),
                        maturity.toInt(),
                        interest
                    )?.toInt()
                }

                val totalIncome = income?.plus(loanAmount.toInt())
                newData.add(
                    DepositProperty(
                        it.bank_id,
                        it.bank_logo,
                        it.bank_name,
                        totalIncome,
                        income,
                        it.bank_interest
                    )
                )
            }
            binding.depositProgress.visibility = View.GONE
            bindRecyclerViewDeposit(binding.recyclerView, newData)
        })

        binding.recyclerView.adapter = DepositAdapter(DepositAdapter.OnClickListener {
            viewModel?.displayPropertyDetails(it)
        })
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        finish()
        return super.onSupportNavigateUp()
    }
}