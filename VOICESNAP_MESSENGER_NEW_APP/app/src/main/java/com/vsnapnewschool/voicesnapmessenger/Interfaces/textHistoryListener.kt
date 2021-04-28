package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.TextHistoryAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.Text_Class

interface textHistoryListener {

    fun textHistoryClick(holder: TextHistoryAdapter.MyViewHolder,item: Text_Class)

}