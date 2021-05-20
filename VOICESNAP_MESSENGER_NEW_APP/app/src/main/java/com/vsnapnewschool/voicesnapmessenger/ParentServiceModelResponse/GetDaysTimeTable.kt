package com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse

data class GetDaysTimeTable(
    val `data`: List<DateData>,
    val message: String, // List Received Successfully
    val status: Int // 1
) {
    data class DateData(
        val day_id: String, // 1
        val day_name: String // Mon
    )
}