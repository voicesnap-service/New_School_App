package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.ParentVideoResponse

interface GetParentVideoCallBack {
    fun callbackvideo(responseBody: ParentVideoResponse)

}