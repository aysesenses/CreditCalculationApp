package com.aysesenses.creditcalculationapp.ui.view.activities.transport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.aysesenses.creditcalculationapp.databinding.ActivityTransportPaymentPlanBinding
import com.aysesenses.creditcalculationapp.ui.adapter.PaymentPlanAdapter
import com.aysesenses.creditcalculationapp.ui.viewmodel.transport.TransportPaymentPlanViewModel

class TransportPaymentPlanActivity : AppCompatActivity() {
    private var _binding: ActivityTransportPaymentPlanBinding? = null
    private val binding get() = _binding!!

    private var viewModel: TransportPaymentPlanViewModel? = null
    private var adapter: PaymentPlanAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTransportPaymentPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this).get(TransportPaymentPlanViewModel::class.java)
        binding.viewmodel = viewModel

        bindAdapter()

        viewModel?.bankName?.value = intent.getStringExtra("bank_name")
        viewModel?.bankInterest?.value = intent.getStringExtra("bank_interest")
        viewModel?.loanAmount?.value = intent.getStringExtra("kredi_tutarı")
        viewModel?.maturityX?.value = intent.getStringExtra("vade")
        viewModel?.installment?.value = intent.getStringExtra("installment")
        viewModel?.bankLogo?.value = intent.getStringExtra("bank_logo")
        viewModel?.totalCost?.value = intent.getStringExtra("total_cost")?.toInt()

        supportActionBar?.title = viewModel?.bankName?.value

        viewModel?.loanAmount?.value?.let { loan ->
            viewModel?.maturityX?.value?.let { maturity ->
                viewModel?.bankInterest?.value?.let { interest ->
                    viewModel?.installment?.value?.let { installment ->
                        viewModel?.getPaymentPlan(
                            loan.toInt(),
                            maturity.toInt(),
                            interest.toDouble(),
                            installment.toDouble()
                        )
                    }
                }
            }
        }
    }

    fun bindAdapter() {
        adapter = viewModel?.paymentPlanList?.let { PaymentPlanAdapter(it) }
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        finish()
        return super.onSupportNavigateUp()
    }
}