package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.vsnapnewschool.voicesnapmessenger.Fragments.TextHistory
import com.vsnapnewschool.voicesnapmessenger.Fragments.TextPublish


class TextTabAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return TextPublish()
            }
            1 -> {
                return TextHistory()
            }

            else -> return TextPublish()
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }

}