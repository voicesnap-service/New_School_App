package com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse

data class GetParentAssingmentFiles(
    val `data`: List<FilesData>,
    val message: String, // List Received Successfully
    val status: Int // 1
) {
    data class FilesData(
        val file_path: String, // https://school-app-files.s3.amazonaws.com/File_20210119131422_IMG_20210119_131354.jpg
        val original_name: String
    )
}