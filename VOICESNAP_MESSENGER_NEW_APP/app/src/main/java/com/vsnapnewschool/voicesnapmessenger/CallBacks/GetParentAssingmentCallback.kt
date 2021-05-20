package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetParentAssignmentResponse


interface GetParentAssingmentCallback {

    fun callBackAssignmentDue(responseBody: GetParentAssignmentResponse)
    fun callBackAssignmentDueArchive(responseBody: GetParentAssignmentResponse)


}