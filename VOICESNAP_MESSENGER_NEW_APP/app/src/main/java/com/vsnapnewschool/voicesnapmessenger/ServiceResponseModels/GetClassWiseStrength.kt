package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class GetClassWiseStrength(
    val `data`: List<ClassStrengthData>,
    val message: String, // List Received Successfully
    val status: Int // 1
) {
    data class ClassStrengthData(
        val class_id: String, // 3110
        val class_name: String, // I
        val student_boys_count: String, // 23
        val student_girls_count: String, // 4
        val student_unspecified_count: String, // 0
        val total_student_count: String // 27
    )
}