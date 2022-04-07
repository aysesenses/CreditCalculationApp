package com.aysesenses.creditcalculationapp.data.model

import android.os.Parcel
import android.os.Parcelable

data class DepositProperty(
    val bank_id: Int?,
    val bank_logo: String?,
    val bank_name: String?,
    val total_income: Int?,
    val net_income: Int?,
    val bank_interest: Double?,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Double::class.java.classLoader) as? Double
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(bank_id)
        parcel.writeString(bank_logo)
        parcel.writeString(bank_name)
        parcel.writeValue(total_income)
        parcel.writeValue(net_income)
        parcel.writeValue(bank_interest)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BankProperty> {
        override fun createFromParcel(parcel: Parcel): BankProperty {
            return BankProperty(parcel)
        }

        override fun newArray(size: Int): Array<BankProperty?> {
            return arrayOfNulls(size)
        }
    }
}