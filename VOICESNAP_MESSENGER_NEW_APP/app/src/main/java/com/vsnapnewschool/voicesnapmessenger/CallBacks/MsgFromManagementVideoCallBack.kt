package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.MessageFromManagementVideoResponse

interface MsgFromManagementVideoCallBack {

    fun callBackManagementVideo(responseBody: MessageFromManagementVideoResponse)
    fun callBackManagementVideo_Archive(responseBody: MessageFromManagementVideoResponse)
}