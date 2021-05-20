package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetAssingmentMemberSubmissions

interface GetAssingmentMemberSubmissionCallback {
    fun callbackSubmissions(responseBody: GetAssingmentMemberSubmissions)

}