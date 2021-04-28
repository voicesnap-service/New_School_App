package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class GetVoiceHistory(
    val `data`: List<VoiceHistoryData>,
    val message: String,
    val status: Int
)

data class VoiceHistoryData(
    val created_on: String,
    val description: String,
    val header_id: String,
    val sub_or_file_name: String,
    val voice_file: String,
    val voice_file_path: String
)