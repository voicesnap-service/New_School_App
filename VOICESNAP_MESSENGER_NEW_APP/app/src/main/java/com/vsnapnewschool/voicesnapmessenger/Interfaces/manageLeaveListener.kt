package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.ApproveLeaveAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.ManageLeaveAdapter
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.ApproveLeaveData

interface manageLeaveListener {

    fun onapproveleveClick(holder: ManageLeaveAdapter.MyViewHolder, item: ApproveLeaveData)

}