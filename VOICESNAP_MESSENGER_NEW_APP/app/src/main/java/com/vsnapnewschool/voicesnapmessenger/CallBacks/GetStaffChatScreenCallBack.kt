package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StaffChatScreenResponse

interface GetStaffChatScreenCallBack {
    fun callbackstafchatscreen(responseBody: StaffChatScreenResponse,currentoffset:Int)

}