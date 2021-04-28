package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetTextMessages

interface GetTextMessagesCallBack {
    fun callBackTextMessages(responseBody: GetTextMessages)
    fun callBackTextMessages_Archive(responseBody: GetTextMessages)
}