package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentExamResultAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.DayCLass

interface examResultListener {

    fun onexamResultClick(holder: ParentExamResultAdapter.MyViewHolder,item: DayCLass)

}