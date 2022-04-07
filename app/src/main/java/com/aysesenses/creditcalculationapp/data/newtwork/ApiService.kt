package com.aysesenses.creditcalculationapp.data.newtwork

import com.aysesenses.creditcalculationapp.data.model.BankProperty
import com.aysesenses.creditcalculationapp.data.model.DepositProperty
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://us-central1-loancalculate-4506a.cloudfunctions.net/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface BankApiService {
    @GET("banksDeposit")
    suspend  fun getBankDeposit(): Response<List<DepositProperty>>
    @GET("banksLoan")
    suspend  fun getBankLoan(): Response<List<BankProperty>>

}
object BankApi {
    val retrofitService : BankApiService by lazy {
        retrofit.create(BankApiService::class.java)
    }
}