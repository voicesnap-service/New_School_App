package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.SchoolListAdapter
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StaffDetailData

interface schoolClickListener {
    fun onclickListener(holder: SchoolListAdapter.MyViewHolder, item: StaffDetailData)
}