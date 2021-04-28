package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.ChatMembersAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.class_chat

interface chatmemberListener {

    fun onchatclickListener(holder: ChatMembersAdapter.MyViewHolder,item: class_chat)

}