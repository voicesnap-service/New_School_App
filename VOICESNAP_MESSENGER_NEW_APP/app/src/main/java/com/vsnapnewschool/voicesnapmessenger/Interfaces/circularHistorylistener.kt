package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.CircularHistoryAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.Text_Class

interface circularHistorylistener {

    fun oncircularclickListener(holder: CircularHistoryAdapter.MyViewHolder,item: Text_Class)

}