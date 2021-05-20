package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.MessageFromManagementTextResponse

interface MsgFromManagementCallBack {
    fun callBackManagementText(responseBody: MessageFromManagementTextResponse)
    fun callBackManagementText_Archive(responseBody: MessageFromManagementTextResponse)
}