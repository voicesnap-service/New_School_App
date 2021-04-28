package com.vsnapnewschool.voicesnapmessenger.Models

import java.io.Serializable

class AttendanceClass(var day: String?, var attendancetype:Boolean) : Serializable {

    var selectedstatus : Boolean? = true

    fun isSelectedstatus(): Boolean ? {
        return selectedstatus
    }

    fun setSelectedstatus(selectedstatus: Boolean) {
        this.selectedstatus = selectedstatus
    }
}