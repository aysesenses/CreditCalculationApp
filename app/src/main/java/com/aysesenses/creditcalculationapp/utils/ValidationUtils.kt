package com.aysesenses.creditcalculationapp.utils

import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import java.math.RoundingMode
import java.text.DecimalFormat

object ValidationUtils {
    fun validateTextField(textInputLayout: TextInputLayout){
        textInputLayout.helperText = "Zorunlu Alan"
    }
    fun validateLoanAmount(editText: EditText, layout: TextInputLayout){
        when {
            (editText.text.toString().isEmpty() || editText.text == null)->  layout.helperText = "Zorunlu Alan"
            editText.text.toString().toInt() < 500 -> layout.helperText = "En az 1000₺ girilmeli"
        }
    }
    fun validateInstallment(number: Double?): Int{
        val df = DecimalFormat("#")
        df.roundingMode = RoundingMode.HALF_UP
        return df.format(number).toInt()
    }
    fun validateRounding(number: String?): String{
        val df = DecimalFormat("#,###,###")
        df.roundingMode = RoundingMode.HALF_UP
        return df.format(number?.toDoubleOrNull()) + "₺"
    }
}