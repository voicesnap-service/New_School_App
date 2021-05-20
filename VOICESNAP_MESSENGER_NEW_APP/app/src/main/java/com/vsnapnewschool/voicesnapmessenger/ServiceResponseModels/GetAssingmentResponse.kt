package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

import java.io.Serializable

data class GetAssingmentResponse(
    val `data`: List<AssingmentData>,
    val message: String, // List Received Successfully
    val status: Int // 1
):Serializable {

    data class AssingmentData(
        val created_on: String, // 23/04/2021 04:38 PM
        val description: String, // science assignment
        val end_date: String, // 20/04/2021
        val header_id: String, // 87434
        val image_array: List<ImageArray>,
        val pdf_array: List<PdfArray>,
        val subject: String, // SOCIAL_SCIENCE
        val submitted_count: String, // 0
        val title: String, // test assignment
        val total_count: String // 1
    ) : Serializable {

    data class ImageArray(
        val file_path: String, // https://school-app-files.s3.amazonaws.com/File_20210119113008_20210119_112828.jpg
        val original_file_name: String // My image
    ):Serializable

    data class PdfArray(
        val file_path: String, // https://school-app-files.s3.amazonaws.com/File_20210119113008_20210119_112828.pdf
        val original_file_name: String // My image
    ):Serializable
}
}
