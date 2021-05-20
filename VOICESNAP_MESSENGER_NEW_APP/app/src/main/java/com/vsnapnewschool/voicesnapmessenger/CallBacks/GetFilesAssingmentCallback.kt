package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetParentAssingmentFiles

interface GetFilesAssingmentCallback {

    fun callFilesAssignment(responseBody: GetParentAssingmentFiles)

}