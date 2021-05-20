package com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse

data class GetLeaveType(
    val `data`: List<LeaveTypeData>,
    val message: String,
    val status: Int
)

data class LeaveTypeData(
    val id: Int,
    val leave_type: String
)