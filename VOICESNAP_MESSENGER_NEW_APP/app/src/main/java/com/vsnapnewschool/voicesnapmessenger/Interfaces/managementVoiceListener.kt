package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.ManagementVoiceAdapter
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.MessageFromManagementVoiceResponse

interface managementVoiceListener {

    fun onManagementVoiceClick(holder: ManagementVoiceAdapter.MyViewHolder, item: MessageFromManagementVoiceResponse.VoiceData)

}