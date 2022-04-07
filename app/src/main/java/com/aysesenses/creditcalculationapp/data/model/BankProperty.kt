package com.aysesenses.creditcalculationapp.data.model

import android.os.Parcel
import android.os.Parcelable

data class BankProperty (
	val bank_id : Int?,
	val bank_logo : String?,
	val bank_name : String?,
	val interest_transport : Double?,
	val interest_personal : Double?,
	val interest_house : Double?,
	val interest_deposit : Double?,
	val total_cost : Int?,
	val installment : Int?,
): Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString(),
		parcel.readString(),
		parcel.readValue(Double::class.java.classLoader) as? Double,
		parcel.readValue(Double::class.java.classLoader) as? Double,
		parcel.readValue(Double::class.java.classLoader) as? Double,
		parcel.readValue(Double::class.java.classLoader) as? Double,
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readValue(Int::class.java.classLoader) as? Int
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeValue(bank_id)
		parcel.writeString(bank_logo)
		parcel.writeString(bank_name)
		parcel.writeValue(interest_transport)
		parcel.writeValue(interest_personal)
		parcel.writeValue(interest_house)
		parcel.writeValue(interest_deposit)
		parcel.writeValue(total_cost)
		parcel.writeValue(installment)
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
