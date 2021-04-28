@file:Suppress("DEPRECATION")

package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.vsnapnewschool.voicesnapmessenger.Fragments.Assignment_HistoryFragment
import com.vsnapnewschool.voicesnapmessenger.Fragments.Assignment_PublishFragment
class AssignmentTabAdapter(
    private val myContext: Context,
    fm: FragmentManager,
    internal var totalTabs: Int
) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return Assignment_PublishFragment()
            }
            1 -> {
                return Assignment_HistoryFragment()
            }

            else -> return Assignment_PublishFragment()
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }

}