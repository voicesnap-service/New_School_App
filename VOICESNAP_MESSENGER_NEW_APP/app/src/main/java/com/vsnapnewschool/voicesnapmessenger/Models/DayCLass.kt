package com.vsnapnewschool.voicesnapmessenger.Models

import java.io.Serializable

class DayCLass(var day: String?) : Serializable {

    var selectedstatus : Boolean? = true


    fun isSelectedstatus(): Boolean ? {
        return selectedstatus
    }

    fun setSelectedstatus(selectedstatus: Boolean) {
        this.selectedstatus = selectedstatus
    }
}