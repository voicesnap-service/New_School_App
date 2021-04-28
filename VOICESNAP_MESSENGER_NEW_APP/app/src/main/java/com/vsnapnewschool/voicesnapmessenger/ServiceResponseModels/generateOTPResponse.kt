package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class generateOTPResponse(
    val `data`: List<OtpData>,
    val message: String,
    val status: Int
)

data class OtpData(
    val dial_numbers: String,
    val forget_otp_mesage: String,
    val more_info: String,
    val otp_message: String
)