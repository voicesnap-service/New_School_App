package com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse

import java.io.Serializable

data class UpcomingEventsResponse(
    val `data`: List<EventsData>,
    val message: String,
    val status: Int
):Serializable

data class EventsData(
    val app_viewed: Int,
    val created_by: String,
    val created_by_short: String,
    val description: String,
    val detail_id: String,
    val event_date: String,
    val event_time: String,
    val header_id: String,
    val is_archive: Int,
    val is_photo_exists: Int,
    val title: String
):Serializable