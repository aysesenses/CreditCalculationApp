package com.aysesenses.creditcalculationapp.ui.viewmodel.transport

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TransportLoanViewModel : ViewModel() {
    val maturity = MutableLiveData<String>()
    val transportStatus = MutableLiveData<String>()
    val loanAmount = MutableLiveData<String>()
}