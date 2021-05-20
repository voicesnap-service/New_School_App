package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetVoiceHistory

interface TeacherVoiceHistoryCallBack {

    fun callBackHistoryList(responseBody: GetVoiceHistory)

}