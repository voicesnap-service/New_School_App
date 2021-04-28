package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.TextHistoryAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.VoiceHistoryAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.Text_Class

interface voiceHistoryListener {
    fun voiceHistoryClick(holder: VoiceHistoryAdapter.MyViewHolder, item: Text_Class)
}