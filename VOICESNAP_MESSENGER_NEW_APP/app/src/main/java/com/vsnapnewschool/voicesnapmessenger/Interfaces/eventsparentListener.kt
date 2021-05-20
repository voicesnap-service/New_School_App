package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentEventsAdapter
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.EventsData

interface eventsparentListener {
    fun oneventClick(holder: ParentEventsAdapter.MyViewHolder,item: EventsData)
}