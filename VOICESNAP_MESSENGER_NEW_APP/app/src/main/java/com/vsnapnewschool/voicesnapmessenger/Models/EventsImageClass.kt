package com.vsnapnewschool.voicesnapmessenger.Models

import java.io.Serializable

class EventsImageClass(val image : Int,var Day: String?, var Month: String?, var Content: String?,var description: String?,var filetype:String) :
    Serializable {
    var count: Int = 0

}
