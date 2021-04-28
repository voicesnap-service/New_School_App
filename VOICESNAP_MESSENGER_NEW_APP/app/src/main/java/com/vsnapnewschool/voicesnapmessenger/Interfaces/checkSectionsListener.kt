package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Models.CheckListClass
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.Section

interface checkSectionsListener {
    fun add(sections: Section?)
    fun remove(sections: Section?)

//
//    fun addSubjectSec(sections:Section)
//    fun removeSubjectSec(sections: Section?)

}