package com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse

import java.io.Serializable

data class GetHomeWorkListResponse(
    val `data`: List<ParentHomeworklist>,
    val message: String,
    val status: Int
): Serializable {

    data class ParentHomeworklist(
        val created_by: String,
        val created_by_short: String,
        val created_on: String,
        val day_id: String,
        val homework_text: String,
        val homework_topic: String,
        val homework_voice: String,
        val subject_day_id: String,
        val subject_name: String
    ):Serializable
}