package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class GetOverallStrength(
    val `data`: List<Data>,
    val message: String, // List Received Successfully
    val status: Int // 1
) {
    data class Data(
        val student_boys_count: String, // 87
        val student_girls_count: String, // 9
        val student_unspecified_count: String, // 9
        val total_staff_count: String, // 36
        val total_student_count: String // 96
    )
}