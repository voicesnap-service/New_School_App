package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.AllStandardData

interface checkAllStandardListener {
    fun addStandard(standards: AllStandardData?)
    fun removeStandard(standards: AllStandardData?)
}