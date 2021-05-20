package com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse

data class GetTimeTableList(
    val `data`: List<TimeTableData>,
    val message: String, // List Received Successfully
    val status: Int // 1
) {
    data class TimeTableData(
        val duration: String, // 30
        val end_time: String, // 10:00
        val hour_id: String, // 24
        val hour_name: String, // Hour 1
        val hour_type: String, // 1
        val staff_break: String,
        val start_time: String, // 09:30
        val subject: String // ENGLISH
    )
}