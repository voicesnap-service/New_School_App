package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.vsnapnewschool.voicesnapmessenger.Fragments.ParentLeaveRequest
import com.vsnapnewschool.voicesnapmessenger.Fragments.ParentManageLeave
class ParentApplyLeaveTabAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return ParentLeaveRequest()
            }
            1 -> {
                return ParentManageLeave()
            }
            else -> return ParentLeaveRequest()
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }

}