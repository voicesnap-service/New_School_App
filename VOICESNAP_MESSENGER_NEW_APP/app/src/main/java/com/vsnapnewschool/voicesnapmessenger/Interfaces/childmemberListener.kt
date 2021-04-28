package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.ChildRoleAdapter
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.ChildDetailData

interface childmemberListener {
    fun onchildmemberclick(holder: ChildRoleAdapter.MyViewHolder,item: ChildDetailData)
}