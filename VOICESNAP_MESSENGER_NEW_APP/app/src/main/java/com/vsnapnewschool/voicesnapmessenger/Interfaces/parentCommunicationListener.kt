package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentCommunicationAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.Text_Class

interface parentCommunicationListener {
    fun oncommunicationClick(holder: ParentCommunicationAdapter.MyViewHolder,item: Text_Class)
}