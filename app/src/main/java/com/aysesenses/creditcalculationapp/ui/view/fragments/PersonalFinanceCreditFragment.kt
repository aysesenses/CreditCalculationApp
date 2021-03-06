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
import com.aysesenses.creditcalculationapp.databinding.FragmentPersonalFinanceCreditBinding
import com.aysesenses.creditcalculationapp.ui.view.activities.personal.PersonalLoanOfferActivity
import com.aysesenses.creditcalculationapp.ui.viewmodel.personal.PersonalLoanViewModel
import com.aysesenses.creditcalculationapp.utils.ValidationUtils

class PersonalFinanceCreditFragment : Fragment() {

    private var viewModel: PersonalLoanViewModel? = null

    private var _binding: FragmentPersonalFinanceCreditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPersonalFinanceCreditBinding.inflate(inflater, container, false)
        val view = binding.root

        maturityDropdownList()
        loaninputType()

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        // Giving the binding access to the OverviewViewModel
        viewModel = ViewModelProvider(this).get(PersonalLoanViewModel::class.java)

        binding.maturity.doOnTextChanged { text, start, before, count ->
            viewModel?.maturity?.value = text?.toString()
        }
        binding.loanAmount.doOnTextChanged { text, start, before, count ->
            viewModel?.loanAmount?.value = text?.toString()
        }
        viewModel?.maturity?.observe(viewLifecycleOwner, Observer { newMaturity ->
            binding.maturityTextFieldLayout.helperText = when {
                newMaturity.isEmpty() -> "Vade se??iniz"
                else -> null
            }
            if(binding.maturity.requestFocus()) {
                hideSoftKeyboard()            }
        })
        viewModel?.loanAmount?.observe(viewLifecycleOwner, Observer { newLoan ->
            binding.loanEmountTextFieldLayout.helperText = when {
                newLoan.isEmpty() -> "Kredi tutar??n?? giriniz"
                newLoan.toInt() < 1000 -> "En az 1000??? giriniz"
                newLoan.toInt() > 1000000 -> "En ??ok 1.000.000??? giriniz"
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
            if (!(binding.loanEmountTextFieldLayout.helperText.isNullOrEmpty()) || (viewModel?.loanAmount?.value == null) || (viewModel?.loanAmount?.value.toString() == "")
            ) {
                ValidationUtils.validateLoanAmount(
                    binding.loanAmount, binding.loanEmountTextFieldLayout
                )
                hasError = true
            }
            if (hasError) {
                binding.loanAmount.clearFocus()
                Toast.makeText(inflater.context, "Hesaplama Yap??lamad??!", Toast.LENGTH_SHORT).show()
            } else {
                activity?.let {
                    val intent = Intent(it, PersonalLoanOfferActivity::class.java)
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
        val maturities = resources.getStringArray(R.array.personal_maturity_list)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, maturities)
        binding.maturity.setAdapter(arrayAdapter)
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