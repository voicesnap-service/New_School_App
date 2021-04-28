package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.OtpData

interface GenerateOtpCallBack {
    fun callBackValue(otpData: OtpData?)
}