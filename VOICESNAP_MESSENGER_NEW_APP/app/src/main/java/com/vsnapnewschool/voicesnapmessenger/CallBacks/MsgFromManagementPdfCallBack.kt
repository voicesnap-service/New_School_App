package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.MessageFromManagementPdfResponse

interface MsgFromManagementPdfCallBack {
    fun callBackManagementPdf(responseBody: MessageFromManagementPdfResponse)
    fun callBackManagementPdf_Archive(responseBody: MessageFromManagementPdfResponse)
}