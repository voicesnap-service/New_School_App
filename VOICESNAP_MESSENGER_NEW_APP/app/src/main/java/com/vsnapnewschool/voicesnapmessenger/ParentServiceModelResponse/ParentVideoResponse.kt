package com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse

data class ParentVideoResponse(
    val `data`: List<ParentVideoDetail>,
    val message: String,
    val status: Int
)

data class ParentVideoDetail(
    val app_viewed: Int,
    val created_by: String,
    val created_by_short: String,
    val created_on: String,
    val description: String,
    val detail_id: String,
    val header_id: String,
    val iframe: String,
    val is_archive: Int,
    val message_type: String,
    val title: String,
    val video_link: String,
    val video_url: String
)