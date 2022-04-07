package com.aysesenses.creditcalculationapp.ui.viewmodel.personal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aysesenses.creditcalculationapp.data.model.PaymentPlanProperty

class PersonalPaymentPlanViewModel : ViewModel() {

    val bsmv : Double = 0.05
    val kkdf : Double = 0.15

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
        var _kddf = _interest * kkdf
        var _bsmv = _interest * bsmv

        var _principal = installment - (_interest + _kddf + _bsmv)
        var _remainingMoney = loan_amount - _principal

        for (k in 1..maturity) {
            if (k == 1) {
                _paymentPlanList.add(PaymentPlanProperty(_remainingMoney.toString(), k.toString(),_principal.toString(),_interest.toString(), installment.toString()))
            } else{
                _interest = _remainingMoney * (interest / 100)
                _kddf = _interest * kkdf
                _bsmv = _interest * bsmv
                _principal = installment - (_interest + _kddf + _bsmv)
                _remainingMoney -= _principal

                if (k == (maturity)){
                    _remainingMoney = 0.00
                }
                _paymentPlanList.add(PaymentPlanProperty(_remainingMoney.toString(), k.toString(),_principal.toString(),_interest.toString(), installment.toString()))
            }
        }
    }
}