package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class GetCountForManagement(
    val `data`: List<CountData>,
    val message: String, // List Received Successfully
    val status: Int // 1
) {
    data class CountData(
        val image_count: String, // 5
        val pdf_count: String, // 1
        val sms_count: String, // 20
        val video_count: String, // 1
        val voice_count: String // 0
    )
}