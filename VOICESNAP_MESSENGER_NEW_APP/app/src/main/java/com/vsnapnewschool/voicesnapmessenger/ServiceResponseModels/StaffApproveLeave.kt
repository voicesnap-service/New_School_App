package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class StaffApproveLeave(
    val `data`: List<ApproveLeaveData>,
    val message: String,
    val status: Int
)

data class ApproveLeaveData(
    val approved_on: String,
    val `class`: String,
    val leave_applied_on: String,
    val leave_file: List<Any>,
    val leave_from: String,
    val leave_id: String,
    val leave_to: String,
    val leave_type: String,
    val no_of_days: String,
    val other_description: String,
    val reason: String,
    val section: String,
    val status: String,
    val student_name: String
)