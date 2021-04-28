package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class AllStudentsResponse(
    val `data`: List<StudentData>,
    val message: String,
    val status: Int
)

data class StudentData(
    val admission_no: String,
    val mobile_number: String,
    val student_id: String,
    val student_name: String
)