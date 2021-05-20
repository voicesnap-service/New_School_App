package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetDaysTimeTable


interface GetDateForTimeTableCallBack {

    fun callBackDatesTimeTable(responseBody: GetDaysTimeTable)

}