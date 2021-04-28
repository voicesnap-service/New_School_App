package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.AllGroupsData

interface checkAllGroupsListener {
    fun addGroup(groups: AllGroupsData?)
    fun removeGroup(groups: AllGroupsData?)
}