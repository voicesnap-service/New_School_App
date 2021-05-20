package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetAssingmentResponse


interface AssingmentHistoryCallback {

    fun callBackAssingmentHistory(responseBody: GetAssingmentResponse)

}