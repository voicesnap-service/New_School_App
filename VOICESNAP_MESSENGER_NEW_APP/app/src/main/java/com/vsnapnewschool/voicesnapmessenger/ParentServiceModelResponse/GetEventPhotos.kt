package com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse

data class GetEventPhotos(
    val `data`: List<Data>,
    val message: String, // List Received Successfully
    val status: Int // 1
) {
    data class Data(
        val file_path: String // https://school-app-files.s3.amazonaws.com/File_20210119113008_20210119_112828.jpg
    )
}