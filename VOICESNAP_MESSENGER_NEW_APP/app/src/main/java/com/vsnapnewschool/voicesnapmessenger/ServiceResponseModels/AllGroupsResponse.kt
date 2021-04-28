package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class AllGroupsResponse(
    val `data`: List<AllGroupsData>,
    val message: String,
    val status: Int
)

data class AllGroupsData(
    val group_id: String,
    val group_name: String,
    val group_type: String
)