package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.StudentChatScreenResponse


interface GetStudentChatScreenCallBack {
    fun callbackstudentchatscreen(responseBody: StudentChatScreenResponse, currentoffset:Int)

}