package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class LoginResponse(
    val `data`: List<LoginData>,
    val message: String,
    val status: Int
)

data class LoginData(
    val child_details: List<ChildDetailData>,
    val is_admin: Int,
    val is_grouphead: Int,
    val is_parent: Int,
    val is_principal: Int,
    val is_staff: Int,
    val max_emergency_voice_duration: Int,
    val max_general_sms_count: Int,
    val max_general_voice_duration: Int,
    val max_homework_sms_count: Int,
    val max_hw_voiceduration: Int,
    val staff_details: List<StaffDetailData>
)

data class ChildDetailData(
    val child_id: String,
    val child_name: String,
    val display_message: String,
    val is_books_enabled: Int,
    val is_not_Allow: String,
    val role: String,
    val roll_number: String,
    val school_city: String,
    val school_id: String,
    val school_logo_url: String,
    val school_name: String,
    val section_name: String,
    val standard_name: String
)

data class StaffDetailData(
    val city: String,
    val is_books_enabled: Int,
    val role: String,
    val school_address: String,
    val school_id: String,
    val school_logo: String,
    val school_name: String,
    val staff_id: String,
    val staff_name: String
)