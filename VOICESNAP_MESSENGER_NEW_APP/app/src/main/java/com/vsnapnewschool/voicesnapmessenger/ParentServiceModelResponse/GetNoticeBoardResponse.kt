package com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse

import java.io.Serializable

data class GetNoticeBoardResponse(
    val `data`: List<NoticeData>,
    val message: String, // List Received Successfully
    val status: Int // 1
) : Serializable {
    data class NoticeData(
        val app_viewed: Int, // 0
        val created_by: String, // priya VS
        val created_by_short: String, // pr
        val created_on: String, // 20/04/2021 12:27
        val description: String, // science notice
        val detail_id: String, // 205
        val header_id: String, // 255
        val is_archive: Int, // 0
        val notice_json: List<NoticeJson>,
        val title: String // test notice 20042021
    ) : Serializable {
        data class NoticeJson(
            val file_path: String // https://school-app-files.s3.amazonaws.com/File_20210119113008_20210119_112828.jpg
        )
    }
}