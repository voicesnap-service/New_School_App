package com.vsnapnewschool.voicesnapmessenger.Models

import java.io.Serializable

class Text_Class(var content: String?, var recipients: String?, var status: String?,var time: String?) : Serializable {
    var count: Int = 0

}
