package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentNoticeBoardAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.Image_Class

interface noticeboardListener {

    fun noticeboardClick(holder: ParentNoticeBoardAdapter.MyViewHolder,item: Image_Class)

}