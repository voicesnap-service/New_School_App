package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class AllStandardResponse(
    val `data`: List<AllStandardData>,
    val message: String,
    val status: Int
)

data class AllStandardData(
    val standard_id: Int,
    val standard_level: Int,
    val standard_name: String
)