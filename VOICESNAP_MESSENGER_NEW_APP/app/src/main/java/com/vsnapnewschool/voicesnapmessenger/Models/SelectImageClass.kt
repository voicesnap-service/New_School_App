package com.vsnapnewschool.voicesnapmessenger.Models

import java.io.Serializable

class SelectImageClass(val image : String, var Day: String?, var Month: String?, var Content: String?, var description: String?) :
    Serializable {
    var count: Int = 0

}
