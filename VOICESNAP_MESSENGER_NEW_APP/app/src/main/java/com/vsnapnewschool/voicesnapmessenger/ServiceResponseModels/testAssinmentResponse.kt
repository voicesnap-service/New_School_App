package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class AssignmentHistoryResponse(
    val `data`: List<Data>,
    val message: String, // List Received Successfully
    val status: Int // 1
) {
    data class Data(
        val created_on: String, // 23/04/2021 04:38 PM
        val description: String, // science assignment
        val end_date: String, // 20/04/2021
        val header_id: String, // 87434
        val image_array: List<ImageArray>,
        val subject: String, // SOCIAL_SCIENCE
        val submitted_count: String, // 0
        val title: String, // test assignment
        val total_count: String // 1
    ) {
        data class ImageArray(
            val file_path: String, // https://school-app-files.s3.amazonaws.com/File_20210119113008_20210119_112828.jpg
            val original_file_name: String // My image
        )
    }
}