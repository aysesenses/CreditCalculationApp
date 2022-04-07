package com.aysesenses.creditcalculationapp.ui.viewmodel.deposit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DepositLoanViewModel : ViewModel() {
    val maturity = MutableLiveData<String>() //vade
    val principal = MutableLiveData<String>() //kredi
}