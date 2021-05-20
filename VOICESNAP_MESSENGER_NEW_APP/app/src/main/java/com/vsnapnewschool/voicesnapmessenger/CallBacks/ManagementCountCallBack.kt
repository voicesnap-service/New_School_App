package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetCountForManagement

interface ManagementCountCallBack {
    fun callBackCount(responseBody: GetCountForManagement)

}