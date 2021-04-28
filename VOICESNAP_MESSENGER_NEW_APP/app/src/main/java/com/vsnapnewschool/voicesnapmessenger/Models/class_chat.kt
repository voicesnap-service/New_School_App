package com.vsnapnewschool.voicesnapmessenger.Models

import java.io.Serializable

class class_chat(var timing: String?, var title: String?, var image: Int, var content_: String?) : Serializable{


    fun getId(): Int {
        return image
    }

}