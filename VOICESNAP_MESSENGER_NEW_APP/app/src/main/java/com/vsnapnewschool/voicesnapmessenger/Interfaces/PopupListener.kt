package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.ChatData

interface PopupListener {
    fun click(selected: String?, teacherChat: ChatData?)
}