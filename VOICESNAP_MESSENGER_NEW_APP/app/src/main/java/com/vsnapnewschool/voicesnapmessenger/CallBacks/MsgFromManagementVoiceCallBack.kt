package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.MessageFromManagementVoiceResponse

interface MsgFromManagementVoiceCallBack {
    fun callBackManagementVoice(responseBody: MessageFromManagementVoiceResponse)
    fun callBackManagementVoice_Archive(responseBody: MessageFromManagementVoiceResponse)
}