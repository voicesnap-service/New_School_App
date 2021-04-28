package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.ApproveLeaveAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.EventsImageClass

interface ApproveLeaveListener {
    fun onapproveleveClick(holder: ApproveLeaveAdapter.MyViewHolder,item: EventsImageClass)

}