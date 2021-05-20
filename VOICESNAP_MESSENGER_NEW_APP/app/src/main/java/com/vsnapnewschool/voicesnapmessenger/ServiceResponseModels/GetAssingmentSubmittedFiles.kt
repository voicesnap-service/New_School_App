package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class GetAssingmentSubmittedFiles(
    val `data`: List<SubmittedFilesData>,
    val message: String, // List Received Successfully
    val status: Int // 1
) {
    data class SubmittedFilesData(
        val description: String, // test
        val file_path: String, // https://school-app-files.s3.amazonaws.com/File_20210205181612_3_1.jpg
        val original_name: String // my file
    )
}


