package com.aysesenses.creditcalculationapp.ui.viewmodel.personal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PersonalLoanViewModel : ViewModel() {
    val maturity = MutableLiveData<String>()
    val loanAmount = MutableLiveData<String>()
}