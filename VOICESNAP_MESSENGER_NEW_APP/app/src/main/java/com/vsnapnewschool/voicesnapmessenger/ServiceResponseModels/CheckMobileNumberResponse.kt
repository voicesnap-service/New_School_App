package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class CheckMobileNumberResponse(
    val status: Int,
    val message: String,
    val `data`: List<MobileNumberData>
    )
data class MobileNumberData(
    val number_exists: Int,
    val is_password_updated: Int,
    val forget_password_requested: Int

)