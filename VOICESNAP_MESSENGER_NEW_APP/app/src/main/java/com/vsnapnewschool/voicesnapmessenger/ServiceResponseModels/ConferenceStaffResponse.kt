package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class ConferenceStaffResponse(
    val `data`: List<ConferenceData>,
    val message: String, // List Received Successfully
    val status: Int // 1
) {
    data class ConferenceData(
        val designation: String, // VP
        val primary_mobile: String, // 8825458916
        val staff_id: String, // 10004935
        val staff_name: String, // Gopinath
        val staff_type: String // teaching_staff
    )
}