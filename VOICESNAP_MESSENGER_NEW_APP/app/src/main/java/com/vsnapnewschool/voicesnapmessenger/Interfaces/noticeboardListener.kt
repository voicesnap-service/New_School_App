package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentNoticeBoardAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.Image_Class
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetNoticeBoardResponse

interface noticeboardListener {

    fun noticeboardClick(holder: ParentNoticeBoardAdapter.MyViewHolder,item: GetNoticeBoardResponse.NoticeData)

}