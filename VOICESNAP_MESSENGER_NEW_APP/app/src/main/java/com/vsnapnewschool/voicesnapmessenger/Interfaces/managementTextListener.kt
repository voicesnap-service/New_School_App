package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.ManagementTextAdapter
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.MessageFromManagementTextResponse


interface managementTextListener {

    fun onManagementTextClick(holder: ManagementTextAdapter.MyViewHolder, item: MessageFromManagementTextResponse.TextData)

}