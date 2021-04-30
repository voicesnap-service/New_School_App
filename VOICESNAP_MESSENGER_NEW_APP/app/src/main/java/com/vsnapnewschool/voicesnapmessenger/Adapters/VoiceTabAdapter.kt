package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.vsnapnewschool.voicesnapmessenger.Fragments.VoiceHistory
import com.vsnapnewschool.voicesnapmessenger.Fragments.VoiceRecord


class VoiceTabAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return VoiceRecord()
            }
            1 -> {
                return VoiceHistory()
            }

            else -> return VoiceRecord()
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }

}