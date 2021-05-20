package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetImageFilesResponse


interface GetImageListCallBack {

    fun callBackImageFiles(responseBody: GetImageFilesResponse)
    fun callBackImageFiles_Archive(responseBody: GetImageFilesResponse)
}