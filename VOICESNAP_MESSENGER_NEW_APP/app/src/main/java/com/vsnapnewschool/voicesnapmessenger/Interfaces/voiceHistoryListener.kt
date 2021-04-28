package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.VoiceHistoryAdapter

import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.VoiceHistoryData

interface voiceHistoryListener {
    fun voiceHistoryClick(holder: VoiceHistoryAdapter.MyViewHolder, item: VoiceHistoryData)
}