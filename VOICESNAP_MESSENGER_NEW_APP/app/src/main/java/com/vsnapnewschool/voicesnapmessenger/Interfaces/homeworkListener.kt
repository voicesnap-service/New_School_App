package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentHomeworkAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.Leave_Class
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetHomeWorkListResponse

interface homeworkListener {

    fun onhomeworkClick(holder: ParentHomeworkAdapter.MyViewHolder,item: GetHomeWorkListResponse.ParentHomeworklist)

}