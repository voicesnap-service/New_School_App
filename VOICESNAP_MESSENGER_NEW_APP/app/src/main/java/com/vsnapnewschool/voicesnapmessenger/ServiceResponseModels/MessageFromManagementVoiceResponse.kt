package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class MessageFromManagementVoiceResponse(
    val `data`: List<VoiceData>,
    val message: String, // List Received Successfully
    val status: Int // 1
) {
    data class VoiceData(
        val app_viewed: Int, // 0
        val created_by: String, // priya VS
        val created_by_short: String, // pr
        val created_on: String, // 25/03/2021 02:01
        val description: String, // emergency
        val detail_id: String, // 13885242
        val duration: String, // 41
        val header_id: String, // 28310
        val is_archive: Int, // 0
        val is_emergency: Int, // 1
        val message_type: String, // Management message
        val voice_file: String // http://localhost:8002/voice/VS_1616661109219.mp3
    )
}