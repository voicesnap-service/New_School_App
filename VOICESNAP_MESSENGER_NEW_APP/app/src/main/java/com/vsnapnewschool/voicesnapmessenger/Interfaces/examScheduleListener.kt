package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.ExamScheduleAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.ImageClass

interface examScheduleListener {

    fun onexamScheduleclick(holder: ExamScheduleAdapter.MyViewHolder,item: ImageClass)

}