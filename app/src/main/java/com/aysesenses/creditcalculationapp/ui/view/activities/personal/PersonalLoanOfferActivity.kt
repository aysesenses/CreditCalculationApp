package com.aysesenses.creditcalculationapp.ui.view.activities.personal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aysesenses.creditcalculationapp.data.model.BankProperty
import com.aysesenses.creditcalculationapp.databinding.ActivityPersonalLoanOfferBinding
import com.aysesenses.creditcalculationapp.ui.adapter.PersonalAdapter
import com.aysesenses.creditcalculationapp.ui.adapter.bindRecyclerViewPersonalLoan
import com.aysesenses.creditcalculationapp.ui.viewmodel.personal.PersonalLoanOfferViewModel

class PersonalLoanOfferActivity : AppCompatActivity() {

    private var viewModel: PersonalLoanOfferViewModel? = null

    private var _binding: ActivityPersonalLoanOfferBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPersonalLoanOfferBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "İhtiyaç Kredisi"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this).get(PersonalLoanOfferViewModel::class.java)

        // Giving the binding access to the OverviewViewModel
        binding.personalCreditViewModel = viewModel
        viewModel?.getBankProperties()

        val loanAmount: String = intent.getStringExtra("loan_amount").toString()
        val maturity: String = intent.getStringExtra("maturity").toString()

            viewModel?.bankList?.observe(this, {
                val newData = mutableListOf<BankProperty>()
                it.forEach {
                    val taksit = it.interest_personal?.let { interest ->
                        viewModel?.getInstallment(
                             loanAmount.toInt(),
                             maturity.toInt(),
                             interest
                        )
                    }
                    val totalCost = taksit?.toInt()?.times(maturity.toInt())
                    newData.add(
                        BankProperty(
                            it.bank_id,
                            it.bank_logo,
                            it.bank_name,
                            it.interest_transport,
                            it.interest_personal,
                            it.interest_house,
                            it.interest_deposit,
                            totalCost,
                           taksit?.toInt()
                        )
                    )
                }
                binding.personalProgress.visibility = View.GONE
                bindRecyclerViewPersonalLoan(binding.recyclerView, newData)
            })

            viewModel?.navigateToSelectedProperty?.observe(this, Observer {
                if (null != it) {
                    // this.findNavController().navigate(PersonalFinanceCreditFragmentDirections.actionDetailBankFragment(it))
                    Intent(this, PersonalPaymentPlanActivity::class.java).apply {
                        putExtra("bank_name", it.bank_name)
                        putExtra("bank_interest", it.interest_personal.toString())
                        putExtra("kredi_tutarı", loanAmount)
                        putExtra("vade",maturity)
                        putExtra("installment", it.installment.toString())
                        putExtra("bank_logo", it.bank_logo.toString())
                        putExtra("total_cost", it.total_cost.toString())
                        startActivity(this)
                    }
                    viewModel?.displayPropertyDetailsComplete()
                }
            })
            binding.recyclerView.adapter = PersonalAdapter(PersonalAdapter.OnClickListener {
                viewModel?.displayPropertyDetails(it)
            })
        }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        finish()
        return super.onSupportNavigateUp()
    }
}

