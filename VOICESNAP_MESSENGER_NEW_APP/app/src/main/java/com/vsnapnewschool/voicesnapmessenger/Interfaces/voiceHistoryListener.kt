package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.TextHistoryAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.VoiceHistoryAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.Text_Class
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.Data

interface voiceHistoryListener {
    fun voiceHistoryClick(holder: VoiceHistoryAdapter.MyViewHolder, item: Data)
}