package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.ConferenceStaffResponse

interface checkConferenceListener {
    fun addStaff(data: ConferenceStaffResponse.ConferenceData?)
    fun removeStaff(data: ConferenceStaffResponse.ConferenceData?)
}