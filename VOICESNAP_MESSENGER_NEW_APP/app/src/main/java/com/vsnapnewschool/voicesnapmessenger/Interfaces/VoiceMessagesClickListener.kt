package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentVoiceAdapter
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetVoiceData

interface VoiceMessagesClickListener {
    fun onVoiceClick(holder: ParentVoiceAdapter.MyViewHolder, item: GetVoiceData)

}