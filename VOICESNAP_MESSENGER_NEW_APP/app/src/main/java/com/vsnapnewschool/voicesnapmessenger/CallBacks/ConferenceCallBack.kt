package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.ConferenceStaffResponse

interface ConferenceCallBack {

    fun callBackConference(responseBody: ConferenceStaffResponse)

}