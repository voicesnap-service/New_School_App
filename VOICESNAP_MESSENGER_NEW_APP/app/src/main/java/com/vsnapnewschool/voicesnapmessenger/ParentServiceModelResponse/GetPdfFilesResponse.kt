package com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse

import java.io.Serializable

  data class GetPdfFilesResponse(
    val `data`: List<PdfData>,
    val message: String, // List Received Successfully
    val status: Int // 1
):Serializable {
    data class PdfData(
        val app_viewed: Int, // 1
        val created_by: String, // priya VS
        val created_by_short: String, // pr
        val createdon: String, // 16/04/2021 04:36 PM
        val description: String, // science sms
        val detail_id: String, // 4988714
        val file_array: List<FileArray>,
        val header_id: String, // 45900
        val is_archive: Int, // 0
        val message_type: String, // Management circular
        val question: String
    ):Serializable {
        data class FileArray(
            val file_path: String, // https://voicesnap-school-files.s3.amazonaws.com/File_20201026094957115.pdf
            val original_file_name: String
        ):Serializable
    }
}