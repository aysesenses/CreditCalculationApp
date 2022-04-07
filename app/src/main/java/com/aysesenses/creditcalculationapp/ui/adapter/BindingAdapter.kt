package com.aysesenses.creditcalculationapp.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aysesenses.creditcalculationapp.R
import com.aysesenses.creditcalculationapp.data.model.BankProperty
import com.aysesenses.creditcalculationapp.data.model.DepositProperty
import java.text.DecimalFormat

@BindingAdapter("listPersonal")
fun bindRecyclerViewPersonalLoan(recyclerView: RecyclerView, data: List<BankProperty>?) {
    val adapter = recyclerView.adapter as PersonalAdapter
    adapter.submitList(data)
}
@BindingAdapter("listHouse")
fun bindRecyclerViewHouseLoan(recyclerView: RecyclerView, data: List<BankProperty>?) {
    val adapter = recyclerView.adapter as HouseAdapter
    adapter.submitList(data)
}
@BindingAdapter("listTransport")
fun bindRecyclerViewTransportLoan(recyclerView: RecyclerView, data: List<BankProperty>?) {
    val adapter = recyclerView.adapter as TransportAdapter
    adapter.submitList(data)
}
@BindingAdapter("listDeposit")
fun bindRecyclerViewDeposit(recyclerView: RecyclerView, data: List<DepositProperty>?) {
    val adapter = recyclerView.adapter as DepositAdapter
    adapter.submitList(data)
}
@BindingAdapter("textPrice")
fun textPrice(view: TextView, text: String?) {
    val formatter = DecimalFormat("#,###,###")
    view.text = text?.let { newText ->
        formatter.format(newText.toInt()).toString()+ "₺"
    }
}
@BindingAdapter("textInterest")
fun textInterest(view: TextView, text: String?) {
    view.text = text?.let { newText ->
        "%" + newText
    }
}
@BindingAdapter("load_image")
fun setImageViewResource(imageView: ImageView, name: String?) {
    when(name){
        "akbank" ->  imageView.setImageResource(R.drawable.logoakbankdirekt)
        "ziraat_katilim" -> imageView.setImageResource(R.drawable.logoziraatlatilim)
        "ziraatbank" -> imageView.setImageResource(R.drawable.logoziraatbankasi)
        "halkbank" -> imageView.setImageResource(R.drawable.logohalkbank)
        "vakifbank" -> imageView.setImageResource(R.drawable.logovakifbank)
        "turkiye_is_bank" -> imageView.setImageResource(R.drawable.logoisbankasi)
        "denizbank" -> imageView.setImageResource(R.drawable.logodenizbank)
        "hsbc" -> imageView.setImageResource(R.drawable.logohsbc)
        "anadolubank" -> imageView.setImageResource(R.drawable.logoanadolubank)
        "burganbank" -> imageView.setImageResource(R.drawable.logoburganbank)
        "fibabanka" -> imageView.setImageResource(R.drawable.logofibabanka)
        "garanti_bbva" -> imageView.setImageResource(R.drawable.logogarantibankasi)
        "icbc_turkey" -> imageView.setImageResource(R.drawable.logoicbc)
        "ingbank" -> imageView.setImageResource(R.drawable.logoing)
        "odeabank" -> imageView.setImageResource(R.drawable.logoodeabank)
        "sekerbank" -> imageView.setImageResource(R.drawable.logosekerbank)
        "teb" -> imageView.setImageResource(R.drawable.logoteb)
        "yapikredi" -> imageView.setImageResource(R.drawable.logoyapikredi)
        "vakif_katilim" -> imageView.setImageResource(R.drawable.logovakifbank)
        "turkiye_finans" -> imageView.setImageResource(R.drawable.logoturkiyefinanskatilimbankasi)
        "qnb_finansbank" -> imageView.setImageResource(R.drawable.logofinansbank)
        "albarakaturk" -> imageView.setImageResource(R.drawable.logoalbarakaturk)
        "cepteteb" -> imageView.setImageResource(R.drawable.logocepteteb)
        "kuveytturk" -> imageView.setImageResource(R.drawable.logokuveytturk)
        "enparacom" -> imageView.setImageResource(R.drawable.logoenpara)
        "alternatif_bank" -> imageView.setImageResource(R.drawable.logoabank)
        "n_kolay" -> imageView.setImageResource(R.drawable.nkolay)
        else -> imageView.setImageResource(R.drawable.ic_broken_image)
    }
}

