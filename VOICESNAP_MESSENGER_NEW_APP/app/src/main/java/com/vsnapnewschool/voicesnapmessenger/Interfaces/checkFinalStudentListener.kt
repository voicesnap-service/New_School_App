package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Models.SelectedPreviewStudent
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StudentData

interface checkFinalStudentListener {
    fun addFinalStudent(students: StudentData?,StandardSectionName:String?,SectionID:String?,TitlePosition:Int?)
    fun removeFinalStudent(students: StudentData?,StandardSectionName:String?,SectionID:String?,TitlePosition:Int?)
}