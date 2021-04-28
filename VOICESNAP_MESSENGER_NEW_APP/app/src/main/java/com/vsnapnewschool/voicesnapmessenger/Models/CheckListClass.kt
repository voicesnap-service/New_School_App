package com.vsnapnewschool.voicesnapmessenger.Models

class CheckListClass {
    private var selectStatus = false
    private var recipients: ArrayList<String>? = null

    fun isSelectStatus(): Boolean {
        return selectStatus
    }

    fun setSelectStatus(selectStatus: Boolean) {
        this.selectStatus = selectStatus
    }
}