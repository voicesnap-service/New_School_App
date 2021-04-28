package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentImageAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.EventsImageClass

interface imageListener {

    fun onimageClick(holder: ParentImageAdapter.MyViewHolder,item: String)

}