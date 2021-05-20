package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetAssingmentSubmittedFiles


interface GetViewSubmittedAssignmentFiles {

    fun callBackViewSubmittedFiles(responseBody: GetAssingmentSubmittedFiles)

}