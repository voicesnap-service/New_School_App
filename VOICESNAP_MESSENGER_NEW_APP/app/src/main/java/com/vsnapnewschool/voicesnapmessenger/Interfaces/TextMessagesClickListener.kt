package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentTextMessageAdapter
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetTextData

interface TextMessagesClickListener {
    fun onTextClick(holder: ParentTextMessageAdapter.MyViewHolder, item: GetTextData)
    fun callBackReadStatus(updateStatus: Boolean?)

}