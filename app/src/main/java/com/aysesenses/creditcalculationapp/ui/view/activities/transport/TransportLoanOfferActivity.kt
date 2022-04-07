package com.aysesenses.creditcalculationapp.ui.view.activities.transport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aysesenses.creditcalculationapp.data.model.BankProperty
import com.aysesenses.creditcalculationapp.databinding.ActivityTransportLoanOfferBinding
import com.aysesenses.creditcalculationapp.ui.adapter.TransportAdapter
import com.aysesenses.creditcalculationapp.ui.adapter.bindRecyclerViewTransportLoan
import com.aysesenses.creditcalculationapp.ui.view.activities.personal.PersonalPaymentPlanActivity
import com.aysesenses.creditcalculationapp.ui.viewmodel.transport.TransportLoanOfferViewModel

class TransportLoanOfferActivity : AppCompatActivity() {
    private var viewModel: TransportLoanOfferViewModel? = null

    private var _binding: ActivityTransportLoanOfferBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTransportLoanOfferBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Taşıt Kredisi"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this).get(TransportLoanOfferViewModel::class.java)

        // Giving the binding access to the OverviewViewModel
        binding.viewmodel = viewModel
        viewModel?.getBankProperties()

        val loanAmount: String = intent.getStringExtra("loan_amount").toString()
        val maturity: String = intent.getStringExtra("maturity").toString()

        viewModel?.bankList?.observe(this, {
            val newData = mutableListOf<BankProperty>()
            it.forEach {
                val taksit = it.interest_transport?.let { interest ->
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
            binding.transportProgress.visibility = View.GONE
            bindRecyclerViewTransportLoan(binding.recyclerView, newData)
        })

        viewModel?.navigateToSelectedProperty?.observe(this, Observer {
            if (null != it) {
                // this.findNavController().navigate(PersonalFinanceCreditFragmentDirections.actionDetailBankFragment(it))
                Intent(this, PersonalPaymentPlanActivity::class.java).apply {
                    putExtra("bank_name", it.bank_name)
                    putExtra("bank_interest", it.interest_transport.toString())
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
        binding.recyclerView.adapter = TransportAdapter(TransportAdapter.OnClickListener {
            viewModel?.displayPropertyDetails(it)
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        finish()
        return super.onSupportNavigateUp()
    }
}