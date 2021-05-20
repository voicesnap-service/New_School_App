package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StaffChatAnswerResponse

interface GetStaffAnsChatCallBack {

    fun callbackstaffanschat(response: StaffChatAnswerResponse)

}