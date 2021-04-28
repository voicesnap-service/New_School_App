package com.vsnapnewschool.voicesnapmessenger.Models

import java.io.Serializable

class Voice_Class(var content: String?, var recipients: String?, var status: String?, var time: String?,var selectstatus: Int?,var id : Int?,var pause : Int?,var voiceid : String?) : Serializable {
    var count: Int = 0

}
