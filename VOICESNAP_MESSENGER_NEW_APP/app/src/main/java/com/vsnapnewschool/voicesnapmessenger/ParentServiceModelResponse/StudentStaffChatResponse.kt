package com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse

data class StudentStaffChatResponse(
    val `data`: List<StaffChatDetails>,
    val message: String,
    val status: Int
)

data class StaffChatDetails(
    val staff_id: String,
    val staff_name: String,
    val subject_id: String,
    val subject_name: String,
    val is_class_teacher: Int
)