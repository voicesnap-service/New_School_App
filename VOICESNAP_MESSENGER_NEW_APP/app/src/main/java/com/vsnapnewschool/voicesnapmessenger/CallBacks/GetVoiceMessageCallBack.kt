package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetVoiceMessages

interface GetVoiceMessageCallBack {
    fun callBackVoiceMessages(responseBody: GetVoiceMessages)
    fun callBackVoiceMessages_Archive(responseBody: GetVoiceMessages)
}