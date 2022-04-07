package com.aysesenses.creditcalculationapp.ui.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.aysesenses.creditcalculationapp.R
import com.aysesenses.creditcalculationapp.ui.view.fragments.DepositFragment
import com.aysesenses.creditcalculationapp.ui.view.fragments.HouseLoanFragment
import com.aysesenses.creditcalculationapp.ui.view.fragments.PersonalFinanceCreditFragment
import com.aysesenses.creditcalculationapp.ui.view.fragments.TransportLoanFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3,
    R.string.tab_text_4,
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> PersonalFinanceCreditFragment()
            1 -> HouseLoanFragment()
            2 -> TransportLoanFragment()
            else -> DepositFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 4
    }
}