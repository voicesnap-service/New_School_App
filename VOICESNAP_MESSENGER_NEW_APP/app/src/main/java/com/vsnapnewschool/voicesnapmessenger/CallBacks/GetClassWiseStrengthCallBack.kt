package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetClassWiseStrength

interface GetClassWiseStrengthCallBack {
    fun callBackClassStrength(responseBody: GetClassWiseStrength)

}