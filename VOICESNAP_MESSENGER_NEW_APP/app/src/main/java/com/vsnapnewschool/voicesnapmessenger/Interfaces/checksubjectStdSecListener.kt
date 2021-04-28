package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StudentData

interface checksubjectStdSecListener {

    fun add(students: StudentData?)
    fun remove(students: StudentData?)
}