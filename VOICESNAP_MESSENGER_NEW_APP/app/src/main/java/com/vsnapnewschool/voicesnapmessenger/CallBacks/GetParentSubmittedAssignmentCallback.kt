package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetParentAssingmentFiles

interface GetParentSubmittedAssignmentCallback {

    fun callBackViewSubmittedFiles(responseBody: GetParentAssingmentFiles)

}