package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class GetSectionWiseStrength(
    val `data`: List<SectionStrengthData>,
    val message: String, // List Received Successfully
    val status: Int // 1
) {
    data class SectionStrengthData(
        val section_id: String, // 12124
        val section_name: String, // A
        val student_boys_count: String, // 3
        val student_girls_count: String, // 0
        val student_unspecified_count: String, // 0
        val total_student_count: String // 3
    )
}