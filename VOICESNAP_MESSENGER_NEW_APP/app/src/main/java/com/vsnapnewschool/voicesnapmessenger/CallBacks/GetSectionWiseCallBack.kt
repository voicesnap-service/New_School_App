package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetSectionWiseStrength

interface GetSectionWiseCallBack {

    fun callBackSectionStrength(responseBody: GetSectionWiseStrength)

}