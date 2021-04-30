package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class GetVoiceMessages(
    val `data`: List<GetVoiceData>,
    val message: String,
    val status: Int
)

data class GetVoiceData(
    val app_viewed: Int,
    val created_by: String,
    val created_by_short: String,
    val created_on: String,
    val description: String,
    val detail_id: String,
    val header_id: String,
    val is_archive: Int,
    val is_emergency: Int,
    val message_type: String,
    val voice_file: String
)