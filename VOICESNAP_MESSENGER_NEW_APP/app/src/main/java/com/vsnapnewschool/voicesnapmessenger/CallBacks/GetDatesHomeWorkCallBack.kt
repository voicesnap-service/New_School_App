package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetDatesHomeWorkListResponse

interface GetDatesHomeWorkCallBack {
    fun callBackDatesHomework(responseBody: GetDatesHomeWorkListResponse)

}