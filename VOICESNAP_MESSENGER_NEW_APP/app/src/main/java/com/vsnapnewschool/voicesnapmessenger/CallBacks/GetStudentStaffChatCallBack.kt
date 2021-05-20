package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.StudentStaffChatResponse


interface GetStudentStaffChatCallBack {
    fun callbackstudentstaffchat(responseBody: StudentStaffChatResponse)

}