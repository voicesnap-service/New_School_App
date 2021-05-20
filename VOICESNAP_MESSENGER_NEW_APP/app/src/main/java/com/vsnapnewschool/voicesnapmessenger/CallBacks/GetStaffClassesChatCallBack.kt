package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StaffChatClassResponse

interface GetStaffClassesChatCallBack {
    fun callbackstaffclasseschat(responseBody: StaffChatClassResponse)

}