package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.vsnapnewschool.voicesnapmessenger.Fragments.ParentAssignmentCompleted
import com.vsnapnewschool.voicesnapmessenger.Fragments.ParentAssignmentDue


class ParentAssignmentTabAdapter(private val myContext: Context,
                                 fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return ParentAssignmentDue()
            }
            1 -> {
                return ParentAssignmentCompleted()
            }

            else -> return ParentAssignmentDue()
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }

}


