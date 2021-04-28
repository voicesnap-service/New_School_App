package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class GetTextMessages(
    val `data`: List<GetTextData>,
    val message: String,
    val status: Int
)

data class GetTextData(
    val app_viewed: Int,
    val content: String,
    val created_by: String,
    val created_by_short: String,
    val created_on: String,
    val description: String,
    val detail_id: String,
    val header_id: String,
    val is_archive: Int,
    val message_type: String
)