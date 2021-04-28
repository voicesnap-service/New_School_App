package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentHomeworkAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.Leave_Class

interface homeworkListener {

    fun onhomeworkClick(holder: ParentHomeworkAdapter.MyViewHolder,item: Leave_Class)

}