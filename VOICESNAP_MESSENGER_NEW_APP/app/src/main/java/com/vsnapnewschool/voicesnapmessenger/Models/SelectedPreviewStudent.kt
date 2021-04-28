package com.vsnapnewschool.voicesnapmessenger.Models
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StudentData
import java.io.Serializable


class SelectedPreviewStudent(var standardSectionName: String?, var sectionID: String?, var studentData: ArrayList<StudentData>) : Serializable {


}

