package com.vsnapnewschool.voicesnapmessenger.Models

import java.io.Serializable

class ImageClass(val image : Int, var Day: String?, var Month: String?, var Content: String?, var description: String?) :
    Serializable {
    var count: Int = 0

    private var selectStatus = false
    private var recipients: ArrayList<String>? = null
    fun isSelectStatus(): Boolean {
        return selectStatus
    }

    fun setSelectStatus(selectStatus: Boolean) {
        this.selectStatus = selectStatus
    }
    }
