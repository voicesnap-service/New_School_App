package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class MessageFromManagementTextResponse(
    val `data`: List<TextData>,
    val message: String, // List Received Successfully
    val status: Int // 1
) {
    data class TextData(
        val app_viewed: Int, // 0
        val content: String, // ff
        val created_by: String, // SWATHI
        val created_by_short: String, // SW
        val created_on: String, // 03/05/2021 05:45
        val description: String, // ff
        val detail_id: String, // 11518683
        val header_id: String, // 104672
        val is_archive: Int, // 0
        val message_type: String // Management message
    )


}