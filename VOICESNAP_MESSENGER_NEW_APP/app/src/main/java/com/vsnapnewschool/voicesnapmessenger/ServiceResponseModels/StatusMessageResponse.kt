package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class StatusMessageResponse(
    val `data`: List<Any>,
    val message: String,
    val status: Int
)