package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StaffDetailData

interface checkSchoolListListener {
    fun add(schools: StaffDetailData?)
    fun remove(schools: StaffDetailData?)
}