package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class GetAssingmentMemberSubmissions(
    val `data`: List<MemberSubmmisionsData>,
    val message: String, // List Received Successfully
    val status: Int // 1
) {
    data class MemberSubmmisionsData(
        val class_name: String, // I
        val detail_id: String, // 6036199
        val issubmitted: Int, // 1
        val member_id: String, // 5276725
        val member_name: String, // VIKASH
        val section_name: String // A
    )
}

