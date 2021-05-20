package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels


data class StaffChatClassResponse(
    val `data`: List<StaffChatClassDetail>,
    val message: String,
    val status: Int
)

data class StaffChatClassDetail(
    val isclassteacher: Int,
    val last_question: String,
    val section: String,
    val sectionid: String,
    val standard: String,
    val standardid: String,
    val subjectid: String,
    val subjectname: String
)

