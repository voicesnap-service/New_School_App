package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetNoticeBoardResponse

interface GetNoticeBoardCallBack {

    fun callBackNoticeBoard(responseBody: GetNoticeBoardResponse)

}