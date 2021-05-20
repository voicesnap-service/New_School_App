package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class StaffChatScreenResponse(
    val `data`: StaffChatScreenDetails,
    val message: String,
    val status: Int
)

data class StaffChatScreenDetails(
    val chat_count: Int,
    val chat_data: List<ChatData>,
    val limit: String,
    val off_set: String
)

data class ChatData(
    var answer: String,
    var answered_on: String,
    var change_answer: String,
    val created_on: String,
    val question: String,
    var question_id: String,
    var reply_type: Any,
    val student_id: String,
    val student_name: String
)