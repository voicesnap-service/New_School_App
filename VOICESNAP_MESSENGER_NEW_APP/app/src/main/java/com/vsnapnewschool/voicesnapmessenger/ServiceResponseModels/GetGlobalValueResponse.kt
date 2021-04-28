package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class GetGlobalValueResponse(
    val `data`: List<GetGlobalData>,
    val message: String,
    val status: Int
)

data class GetGlobalData(
    val value: String
)