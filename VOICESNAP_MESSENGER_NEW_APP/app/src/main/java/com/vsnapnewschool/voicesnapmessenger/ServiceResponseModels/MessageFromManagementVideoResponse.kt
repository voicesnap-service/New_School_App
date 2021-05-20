package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class MessageFromManagementVideoResponse(
    val `data`: List<VideoData>,
    val message: String, // List Received Successfully
    val status: Int // 1
) {
    data class VideoData(
        val app_viewed: Int, // 0
        val created_by: String, // priya VS
        val created_by_short: String, // pr
        val created_on: String, // 15/04/2021 03:34
        val description: String, // test description
        val detail_id: String, // 6797699
        val header_id: String, // 154179
        val iframe: String, // https://player.vimeo.com/video/524375474?title=0&amp;byline=0&amp;portrait=0&amp;badge=0&amp;autopause=0&amp;player_id=0&amp;app_id=177030
        val is_archive: Int, // 0
        val message_type: String, // Management message
        val title: String, // test title
        val video_link: String, // 524375474/e66566d420
        val video_url: String // https://vimeo.com/524375474/e66566d420
    )
}