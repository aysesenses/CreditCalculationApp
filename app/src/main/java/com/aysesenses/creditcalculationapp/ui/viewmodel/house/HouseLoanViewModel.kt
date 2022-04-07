package com.aysesenses.creditcalculationapp.ui.viewmodel.house

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HouseLoanViewModel : ViewModel() {
    val maturity = MutableLiveData<String>() //vade
    val loanAmount = MutableLiveData<String>() //kredi
}