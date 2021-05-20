package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetHomeWorkListResponse

interface GetHomeWorkCallBack {
    fun callBackHomework(responseBody: GetHomeWorkListResponse)

}