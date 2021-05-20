package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetPdfFilesResponse

interface GetPdfListCallBack {

    fun callBackPdfFiles(responseBody: GetPdfFilesResponse)
    fun callBackPdfFilesArchive(responseBody: GetPdfFilesResponse)
}