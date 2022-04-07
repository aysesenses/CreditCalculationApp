package com.aysesenses.creditcalculationapp.ui.view.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aysesenses.creditcalculationapp.HomeActivity
import com.aysesenses.creditcalculationapp.R
import com.aysesenses.creditcalculationapp.databinding.FragmentTransportLoanBinding
import com.aysesenses.creditcalculationapp.ui.view.activities.transport.TransportLoanOfferActivity
import com.aysesenses.creditcalculationapp.ui.viewmodel.transport.TransportLoanViewModel
import com.aysesenses.creditcalculationapp.utils.ValidationUtils

class TransportLoanFragment : Fragment() {

    private var viewModel: TransportLoanViewModel? = null

    private var _binding: FragmentTransportLoanBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransportLoanBinding.inflate(inflater, container, false)
        val view = binding.root
        maturityDropdownList()
        loaninputType()

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        // Giving the binding access to the OverviewViewModel
        viewModel = ViewModelProvider(this).get(TransportLoanViewModel::class.java)

        binding.maturity.doOnTextChanged { text, start, before, count ->
            viewModel?.maturity?.value = text?.toString()
        }
        binding.transportStatus.doOnTextChanged { text, start, before, count ->
            viewModel?.transportStatus?.value = text?.toString()
        }
        binding.loanAmount.doOnTextChanged { text, start, before, count ->
            viewModel?.loanAmount?.value = text?.toString()
        }
        viewModel?.maturity?.observe(viewLifecycleOwner, Observer { newMaturity ->
            binding.maturityTextFieldLayout.helperText = when {
                newMaturity.isEmpty() -> "Vade seçiniz"
                else -> null
            }
            if(binding.maturity.requestFocus()) {
                hideSoftKeyboard()            }
        })
        viewModel?.transportStatus?.observe(viewLifecycleOwner, Observer { newStatus ->
            binding.transportStatusTextFieldLayout.helperText = when {
                newStatus.isEmpty() -> "Araç durumunu seçiniz"
                else -> null
            }
            if(binding.transportStatus.requestFocus()) {
                hideSoftKeyboard()            }
        })
        viewModel?.loanAmount?.observe(viewLifecycleOwner, Observer { newLoan ->
            binding.loanEmountTextFieldLayout.helperText = when {
                newLoan.isEmpty() -> "Kredi tutarını giriniz"
                newLoan.toInt() < 1000 -> "En az 1000₺ giriniz"
                newLoan.toInt() > 1000000 -> "En çok 1.000.000₺ giriniz"
                else -> null
            }
            if (!binding.loanAmount.isFocused){
                hideSoftKeyboard()
            }
        })

        binding.offerButton.setOnClickListener {
            var hasError = false

            if (!binding.maturityTextFieldLayout.helperText.isNullOrEmpty()
                || viewModel?.maturity?.value == null
            ) {
                ValidationUtils.validateTextField(binding.maturityTextFieldLayout)
                hasError = true
            }
            if (!binding.transportStatusTextFieldLayout.helperText.isNullOrEmpty()
                || (viewModel?.transportStatus?.value == null)
            ) {
                ValidationUtils.validateTextField(binding.transportStatusTextFieldLayout)
                hasError = true
            }
            if (!(binding.loanEmountTextFieldLayout.helperText.isNullOrEmpty()) || (viewModel?.loanAmount?.value == null) || (viewModel?.loanAmount?.value.toString() == "")
            ) {
                ValidationUtils.validateLoanAmount(
                    binding.loanAmount, binding.loanEmountTextFieldLayout
                )
                hasError = true
            }
            if (hasError) {
                binding.loanAmount.clearFocus()
                Toast.makeText(inflater.context, "Hesaplama Yapılamadı!", Toast.LENGTH_SHORT).show()
            } else {
                activity?.let {
                    val intent = Intent(it, TransportLoanOfferActivity::class.java)
                        .apply {
                            putExtra("loan_amount", viewModel?.loanAmount?.value.toString())
                            putExtra("maturity", viewModel?.maturity?.value.toString())
                        }
                    it.startActivity(intent)
                }
            }
        }
        return view
    }

    fun maturityDropdownList() {
        val maturities = resources.getStringArray(R.array.transport_maturity_list)
        val transportStatus = resources.getStringArray(R.array.transport_status)
        val arrayAdapterMaturity = ArrayAdapter(requireContext(), R.layout.item_dropdown, maturities)
        val arrayAdapterTransport = ArrayAdapter(requireContext(), R.layout.item_dropdown, transportStatus)
        binding.maturity.setAdapter(arrayAdapterMaturity)
        binding.transportStatus.setAdapter(arrayAdapterTransport)
    }
    override fun onResume() {
        super.onResume()
        maturityDropdownList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun hideSoftKeyboard() {
        val activity = activity as HomeActivity
        val view = activity.currentFocus
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.hideSoftInputFromWindow(view!!.getWindowToken(), 0)
        }
    }
    fun loaninputType() {
        binding.loanAmount.inputType = InputType.TYPE_CLASS_NUMBER
    }
}