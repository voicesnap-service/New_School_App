package com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse

import java.io.Serializable

 data class GetParentAssignmentResponse(
    val `data`: List<AssingmentDueData>,
    val message: String, // List Received Successfully
    val status: Int // 1
):Serializable {
    data class AssingmentDueData(
        val app_viewed: Int, // 0
        val created_by: String, // SWATHI
        val created_by_short: String, // SW
        val created_on: String, // 23/04/2021 04:38 PM
        val description: String, // science assignment
        val detail_id: String, // 6036217
        val end_date: String, // 20/04/2021
        val header_id: String, // 87435
        val image_array: List<ImageArray>,
        val is_archive: Int, // 0
        val message_type: String, // Staff circular
        val pdf_array: List<PdfArray>,
        val subject: String, // SOCIAL_SCIENCE
        val submitted_count: String, // 0
        val title: String // test assignment
    ):Serializable {

        data class ImageArray(
            val file_path: String, // https://school-app-files.s3.amazonaws.com/File_20210119113008_20210119_112828.jpg
            val original_file_name: String // My image
        ):Serializable

        data class PdfArray(
            val file_path: String, // https://school-app-files.s3.amazonaws.com/File_20210119113008_20210119_112828.jpg
            val original_file_name: String // My image
        ):Serializable
    }
}