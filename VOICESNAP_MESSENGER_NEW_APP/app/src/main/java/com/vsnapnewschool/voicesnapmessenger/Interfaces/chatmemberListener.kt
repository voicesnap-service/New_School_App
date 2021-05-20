package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.ChatMembersAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.class_chat
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StaffChatClassDetail

interface chatmemberListener {

    fun onchatclickListener(holder: ChatMembersAdapter.MyViewHolder,item: StaffChatClassDetail)

}