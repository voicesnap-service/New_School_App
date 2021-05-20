package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.vsnapnewschool.voicesnapmessenger.Fragments.ParentPastEvent
import com.vsnapnewschool.voicesnapmessenger.Fragments.ParentUpcomingEvent

class ParentEventTabAdapter (private val myContext: Context,
                             fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm)
{
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return ParentUpcomingEvent()
            }
            1 -> {
                return ParentPastEvent()
            }

            else -> return ParentUpcomingEvent()
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }

}