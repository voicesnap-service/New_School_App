package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.AllStaffsData

interface checkAllStaffsListener {
    fun addStaff(groups: AllStaffsData?)
    fun removeStaff(groups: AllStaffsData?)
}