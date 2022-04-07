package com.aysesenses.creditcalculationapp.ui.viewmodel.personal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aysesenses.creditcalculationapp.data.model.BankProperty
import com.aysesenses.creditcalculationapp.data.newtwork.BankApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PersonalLoanOfferViewModel : ViewModel() {

    private val _bankList = MutableLiveData<List<BankProperty>>()
    val bankList: LiveData<List<BankProperty>>
        get() = _bankList

    private val _navigateToSelectedProperty = MutableLiveData<BankProperty?>()
    val navigateToSelectedProperty: LiveData<BankProperty?>
        get() = _navigateToSelectedProperty

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    fun getInstallment(loan_amount: Int, maturity: Int, interest: Double): Double {
        val bsmv = 0.05
        val kkdf = 0.15
        val tax_interest = (interest / 100) * (1 + bsmv + kkdf)

        val k1 = tax_interest * Math.pow((1 + tax_interest), maturity.toDouble())
        val k2 = Math.pow((1 + tax_interest), maturity.toDouble()) - 1
        val installment = loan_amount * k1 / k2

        return installment
    }

    fun getBankProperties() {
        coroutineScope.launch {
            val getPropertiesDeferred = BankApi.retrofitService.getBankLoan()
            try {
                _bankList.postValue(getPropertiesDeferred.body()?.shuffled())
            } catch (e: Exception) {
                _bankList.postValue(ArrayList())
            }
        }
    }

    fun displayPropertyDetails(bankProperty: BankProperty) {
        _navigateToSelectedProperty.value = bankProperty
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}