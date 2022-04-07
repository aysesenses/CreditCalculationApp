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
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aysesenses.creditcalculationapp.HomeActivity
import com.aysesenses.creditcalculationapp.databinding.FragmentDepositBinding
import com.aysesenses.creditcalculationapp.ui.view.activities.deposit.DepositLoanOfferActivity
import com.aysesenses.creditcalculationapp.ui.viewmodel.deposit.DepositLoanViewModel
import com.aysesenses.creditcalculationapp.utils.ValidationUtils

class DepositFragment : Fragment() {
    private var viewModel : DepositLoanViewModel? = null

    private var _binding: FragmentDepositBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDepositBinding.inflate(inflater, container, false)
        val view = binding.root

        loaninputType()

        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(DepositLoanViewModel::class.java)

        binding.maturity.doOnTextChanged { text, start, before, count ->
            viewModel?.maturity?.value = text?.toString()
        }
        binding.loanAmount.doOnTextChanged { text, start, before, count ->
            viewModel?.principal?.value = text?.toString()
        }
        viewModel?.maturity?.observe(viewLifecycleOwner, Observer { newMaturity ->
            binding.maturityTextFieldLayout.helperText = when {
                newMaturity.isEmpty() -> "Vade seçiniz"
                else -> null
            }
        })
        viewModel?.principal?.observe(viewLifecycleOwner, Observer { newLoan ->
            binding.loanEmountTextFieldLayout.helperText = when {
                newLoan.isEmpty() -> "Kredi tutarını giriniz"
                newLoan.toInt() < 1000 -> "En az 1000₺ giriniz"
                newLoan.toInt() > 1000000 -> "En çok 1.000.000₺ giriniz"
                else -> null
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
            if (!(binding.loanEmountTextFieldLayout.helperText.isNullOrEmpty()) || (viewModel?.principal?.value == null) || (viewModel?.principal?.value.toString() == "")
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
                    val intent = Intent(it, DepositLoanOfferActivity::class.java)
                        .apply {
                            putExtra("loan_amount", viewModel?.principal?.value.toString())
                            putExtra("maturity", viewModel?.maturity?.value.toString())
                        }
                    it.startActivity(intent)
                }
            }
        }
        return view
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
        binding.maturity.inputType = InputType.TYPE_CLASS_NUMBER
    }
}