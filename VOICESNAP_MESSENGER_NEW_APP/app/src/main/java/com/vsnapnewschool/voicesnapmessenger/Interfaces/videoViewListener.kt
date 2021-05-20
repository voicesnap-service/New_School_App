package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.VideoviewallAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.VideoviewconAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.Text_Class
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.ParentVideoDetail

interface videoViewListener {

    fun videoViewClick(holder: VideoviewallAdapter.MyViewHolder, item: ParentVideoDetail)

}