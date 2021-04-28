package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentEventsAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.EventsImageClass

interface eventsparentListener {
    fun oneventClick(holder: ParentEventsAdapter.MyViewHolder,item: EventsImageClass)

}