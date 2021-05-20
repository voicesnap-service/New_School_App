package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class StaffChatAnswerResponse(
    val `data`: StaffChatAnswerData,
    val message: String,
    val status: Int
)

data class StaffChatAnswerData(
    val chat_count: String,
    val chat_data: List<StaffChatAnswerDetails>,
    val limit: String,
    val off_set: String
)

data class StaffChatAnswerDetails(
    val answer: String,
    val answered_on: String,
    val change_answer: Int,
    val created_on: String,
    val question_id: String,
    val reply_type: String,
    val student_id: String,
    val student_name: String
)