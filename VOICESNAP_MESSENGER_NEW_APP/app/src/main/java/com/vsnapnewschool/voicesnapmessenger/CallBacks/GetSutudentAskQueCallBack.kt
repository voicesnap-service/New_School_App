package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.StudentChatScreenResponse


interface GetSutudentAskQueCallBack {
    fun callbackstudentaskque(response: StudentChatScreenResponse)
}