package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.vsnapnewschool.voicesnapmessenger.Fragments.ParentFeeDue
import com.vsnapnewschool.voicesnapmessenger.Fragments.ParentPaidFee
import com.vsnapnewschool.voicesnapmessenger.Fragments.ParentUpcomingFees


class ParentFeesTabAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return ParentFeeDue()
            }
            1 -> {
                return ParentUpcomingFees()
            }
            2 ->{
                return ParentPaidFee()

            }

            else -> return ParentFeeDue()
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }

}