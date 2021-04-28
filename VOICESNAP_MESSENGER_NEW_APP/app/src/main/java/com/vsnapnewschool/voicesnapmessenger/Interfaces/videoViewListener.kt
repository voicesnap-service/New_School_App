package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.VideoviewconAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.Text_Class

interface videoViewListener {

    fun videoViewClick(holder: VideoviewconAdapter.MyViewHolder,item: Text_Class)

}