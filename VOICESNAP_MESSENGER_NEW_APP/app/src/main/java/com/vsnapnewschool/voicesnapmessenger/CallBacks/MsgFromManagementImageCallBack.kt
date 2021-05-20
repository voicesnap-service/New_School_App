package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.MessageFromMangementImageResponse

interface MsgFromManagementImageCallBack {

    fun callBackManagementImage(responseBody: MessageFromMangementImageResponse)
    fun callBackManagementImage_Archive(responseBody: MessageFromMangementImageResponse)
}