package com.vsnapnewschool.voicesnapmessenger.Network

import android.app.Activity
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Rest.APIClient
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetTextMessagesCallBack
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetTextMessages
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StatusMessageResponse
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import com.vsnapnewschool.voicesnapmessenger.Util_Common.GifLoading
import retrofit2.Call
import retrofit2.Response

object StudentAPIServices {

    fun getTextMessages(activity: Activity?, callBack: GetTextMessagesCallBack) {

        val mobileNumber = Util_shared_preferences.getMobileNumber(activity)
        val loginToken = Util_shared_preferences.getLoginToken(activity)
        val schoolID = Util_shared_preferences.getStudentSchoolID(activity)
        val StudentID = Util_shared_preferences.getStudentID(activity)
        val ClassID = Util_shared_preferences.getStudentClassID(activity)
        val SectionID = Util_shared_preferences.getStudentSectionID(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty("login_token", loginToken)
        jsonObject.addProperty("mobile_number", mobileNumber)
        jsonObject.addProperty("school_id", schoolID)
        jsonObject.addProperty("student_id", StudentID)
        jsonObject.addProperty("class_id", ClassID)
        jsonObject.addProperty("section_id", SectionID)
        Log.d("ParentTextMsgRequest", jsonObject.toString())
        GifLoading.loading(activity, true)
        var apiInterface: ApiInterface =
            APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getTextMessages(jsonObject)!!
            .enqueue(object : retrofit2.Callback<GetTextMessages?> {
                override fun onResponse(
                    call: Call<GetTextMessages?>?,
                    response: Response<GetTextMessages?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("logoutResponse", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody!!.status == 1) {
                                callBack.callBackTextMessages(responseBody)
                            } else {
                                UtilConstants.customFailureAlert(activity, responseBody.message)
                            }
                        } else if (response?.code() == 400 || response?.code() == 500) {
                            val errorResponseBody = Gson().fromJson(
                                response.errorBody()?.charStream(),
                                StatusMessageResponse::class.java
                            )
                            UtilConstants.handleErrorResponse(activity, response.code(), errorResponseBody
                            )
                        } else {
                            UtilConstants.normalToast(
                                activity,
                                activity?.getString(R.string.Service_unavailable)
                            )
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }
                }

                override fun onFailure(call: Call<GetTextMessages?>?, t: Throwable?) {
                    GifLoading.loading(activity, false)
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }
}