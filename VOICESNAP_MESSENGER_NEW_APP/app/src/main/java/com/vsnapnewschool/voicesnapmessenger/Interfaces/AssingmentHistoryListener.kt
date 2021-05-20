package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.AssignmentHistoryAdapter
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetAssingmentResponse

interface AssingmentHistoryListener {

    fun onAssingmentHistoryClick(holder:AssignmentHistoryAdapter.MyViewHolder, item: GetAssingmentResponse.AssingmentData)

}