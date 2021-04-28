package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.AttendanceMainAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.DayCLass

interface markattendanceListener {

    fun onmarkAttendanceClick(holder: AttendanceMainAdapter.MyViewHolder,item: DayCLass)

}