package com.aysesenses.creditcalculationapp.ui.view.activities.house

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aysesenses.creditcalculationapp.data.model.BankProperty
import com.aysesenses.creditcalculationapp.databinding.ActivityHouseLoanOfferBinding
import com.aysesenses.creditcalculationapp.ui.adapter.HouseAdapter
import com.aysesenses.creditcalculationapp.ui.adapter.bindRecyclerViewHouseLoan
import com.aysesenses.creditcalculationapp.ui.viewmodel.house.HouseLoanOfferViewModel

class HouseLoanOfferActivity : AppCompatActivity() {

    private var viewModel : HouseLoanOfferViewModel? = null

    private var _binding: ActivityHouseLoanOfferBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHouseLoanOfferBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Konut Kredisi"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this).get(HouseLoanOfferViewModel::class.java)

        binding.viewmodel = viewModel
        viewModel?.getBankProperties()

        val loanAmount: String = intent.getStringExtra("loan_amount").toString()
        val maturity: String = intent.getStringExtra("maturity").toString()

        viewModel?.bankList?.observe(this, {
            val newData = mutableListOf<BankProperty>()
            it.forEach {
                val taksit = it.interest_house?.let { interest ->
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
            binding.houseProgress.visibility = View.GONE
            bindRecyclerViewHouseLoan(binding.recyclerView, newData)
        })

        viewModel?.navigateToSelectedProperty?.observe(this, Observer {
            if (null != it) {
                // this.findNavController().navigate(PersonalFinanceCreditFragmentDirections.actionDetailBankFragment(it))
                Intent(this, HousePaymentPlanActivity::class.java).apply {
                    putExtra("bank_name", it.bank_name)
                    putExtra("bank_interest", it.interest_house.toString())
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
        binding.recyclerView.adapter = HouseAdapter(HouseAdapter.OnClickListener {
            viewModel?.displayPropertyDetails(it)
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        finish()
        return super.onSupportNavigateUp()
    }
}