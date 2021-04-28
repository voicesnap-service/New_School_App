package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class AllStaffsResponse(
    val `data`: List<AllStaffsData>,
    val message: String,
    val status: Int
)

data class AllStaffsData(
    val mobile_number: String,
    val staff_id: String,
    val staff_name: String,
    val staff_type: String
)