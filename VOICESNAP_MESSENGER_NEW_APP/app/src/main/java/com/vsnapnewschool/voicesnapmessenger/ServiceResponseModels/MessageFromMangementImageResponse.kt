package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

import java.io.Serializable

data class MessageFromMangementImageResponse(
    val `data`: List<ImageData>,
    val message: String, // List Received Successfully
    val status: Int // 1
):Serializable {
    data class ImageData(
        val app_viewed: Int, // 0
        val created_by: String, // priya VS
        val created_by_short: String, // pr
        val createdon: String, // 09/04/2021 07:30 PM
        val description: String, // science sms
        val detail_id: String, // 4988457
        val file_array: List<FileArray>,
        val header_id: String, // 45890
        val is_archive: Int, // 0
        val message_type: String // Management circular
    ):Serializable {
        data class FileArray(
            val file_path: String, // https://school-app-files.s3.amazonaws.com/File_20210119113008_20210119_112828.jpg
            val original_file_name: String
        ):Serializable
    }
}