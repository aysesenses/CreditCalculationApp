package com.aysesenses.creditcalculationapp.ui.view.activities.house

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.aysesenses.creditcalculationapp.databinding.ActivityHousePaymentPlanBinding
import com.aysesenses.creditcalculationapp.ui.adapter.PaymentPlanAdapter
import com.aysesenses.creditcalculationapp.ui.viewmodel.house.HousePaymentPlanViewModel

class HousePaymentPlanActivity : AppCompatActivity() {

    private var _binding: ActivityHousePaymentPlanBinding? = null
    private val binding get() = _binding!!

    private var viewModel: HousePaymentPlanViewModel? = null
    private var adapter: PaymentPlanAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHousePaymentPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this).get(HousePaymentPlanViewModel::class.java)
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