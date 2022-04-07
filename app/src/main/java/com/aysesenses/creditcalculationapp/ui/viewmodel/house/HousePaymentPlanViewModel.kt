package com.aysesenses.creditcalculationapp.ui.viewmodel.house

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aysesenses.creditcalculationapp.data.model.PaymentPlanProperty

class HousePaymentPlanViewModel : ViewModel() {
    private val _paymentPlanList: ArrayList<PaymentPlanProperty> = ArrayList()
    val paymentPlanList: ArrayList<PaymentPlanProperty>
        get() = _paymentPlanList

    val bankName = MutableLiveData<String>()
    val bankLogo = MutableLiveData<String>()
    val bankInterest = MutableLiveData<String>()
    val loanAmount = MutableLiveData<String>()
    val maturityX = MutableLiveData<String>()
    val installment = MutableLiveData<String>()
    val totalCost  = MutableLiveData<Int>()

    fun getPaymentPlan(loan_amount: Int, maturity : Int, interest: Double, installment: Double){
        var _interest = loan_amount * (interest / 100)
        var _principal = installment - _interest
        var _remainingMoney = loan_amount - _principal

        val newMaturity = maturity * 12

        for (k in 1..newMaturity) {
            if (k == 1) {
                _paymentPlanList.add(PaymentPlanProperty(_remainingMoney.toString(), k.toString(),_principal.toString(),_interest.toString(), installment.toString()))
            } else{
                _interest = _remainingMoney * (interest / 100)
                _principal = installment - _interest
                _remainingMoney -= _principal

                if (k == (newMaturity)){
                    _remainingMoney = 0.00
                }
                _paymentPlanList.add(PaymentPlanProperty(_remainingMoney.toString(), k.toString(),_principal.toString(),_interest.toString(), installment.toString()))
            }
        }
    }
}