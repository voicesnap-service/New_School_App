package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetTimeTableList

interface TimeTableCallBack {

    fun callbackTimeTable(responseBody: GetTimeTableList)

}