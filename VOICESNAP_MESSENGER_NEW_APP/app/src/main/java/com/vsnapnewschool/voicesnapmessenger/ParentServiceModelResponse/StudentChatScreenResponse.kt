package com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse

data class StudentChatScreenResponse(
    val `data`: Data,
    val message: String,
    val status: Int
)

data class Data(
    val chat_count: Int,
    val chat_data: List<StudentChatData>,
    val limit: String,
    val off_set: String
)

data class StudentChatData(
    val answer: String,
    val answered_on: String,
    val change_answer: String,
    val created_on: String,
    val my_question: String,
    val question: String,
    val question_id: String,
    val reply_type: String,
    val student_id: String,
    val student_name: String
)