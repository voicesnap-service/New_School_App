package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentAssignmentDueAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.EventsImageClass
import com.vsnapnewschool.voicesnapmessenger.Models.Leave_Class

interface assignmentDueListener {

    fun onassignmentClick(holder: ParentAssignmentDueAdapter.MyViewHolder,item: EventsImageClass)

}