package com.aysesenses.creditcalculationapp.ui.viewmodel.deposit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aysesenses.creditcalculationapp.data.model.DepositProperty
import com.aysesenses.creditcalculationapp.data.newtwork.BankApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DepositLoanOfferViewModel : ViewModel() {
    private val _bankList = MutableLiveData<List<DepositProperty>>()
    val bankList: LiveData<List<DepositProperty>>
        get() = _bankList

    private val _navigateToSelectedProperty = MutableLiveData<DepositProperty?>()
    val navigateToSelectedProperty: LiveData<DepositProperty?>
        get() = _navigateToSelectedProperty

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO )

    fun netIncome(principal: Int, maturity : Int, interest: Double): Double{ // net kazanç
        val grossIncome : Double = principal * interest * (30/36500.0)
        var netIncome = 0.0
        when{
            maturity <= 180 -> netIncome = grossIncome - (grossIncome * 0.05)
            180 <= maturity || maturity <= 365 -> netIncome = grossIncome - (grossIncome * 0.03)
            maturity > 365 -> netIncome = grossIncome - (grossIncome * 0.00)
        }
        return netIncome
    }

    fun getDepositProperties() {
        coroutineScope.launch {
            val getPropertiesDeferred = BankApi.retrofitService.getBankDeposit()
            try {
                _bankList.postValue(getPropertiesDeferred.body()?.shuffled())
            } catch (e: Exception) {
                _bankList.postValue(ArrayList())
            }
        }
    }

    fun displayPropertyDetails(depositProperty: DepositProperty) {
        _navigateToSelectedProperty.value = depositProperty
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}