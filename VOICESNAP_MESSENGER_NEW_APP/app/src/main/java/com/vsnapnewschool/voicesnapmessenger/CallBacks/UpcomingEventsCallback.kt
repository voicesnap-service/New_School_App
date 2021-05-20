package com.vsnapnewschool.voicesnapmessenger.CallBacks

import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.UpcomingEventsResponse

interface UpcomingEventsCallback
{

    fun callbackUpcomingEvents(responseBody: UpcomingEventsResponse)

}