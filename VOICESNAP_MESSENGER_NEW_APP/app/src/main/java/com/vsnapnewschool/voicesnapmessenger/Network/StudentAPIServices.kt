package com.vsnapnewschool.voicesnapmessenger.Network

import android.app.Activity
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Rest.APIClient
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetTextMessagesCallBack
import com.vsnapnewschool.voicesnapmessenger.Interfaces.TextMessagesClickListener
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.CLASS_ID
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.LOGIN_TOKEN
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.MOBILE_NUMBER
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.SCHOOL_ID
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.SECTION_ID
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.STUDENT_ID
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetTextMessages
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StatusMessageResponse
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.API_NORMAL
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import com.vsnapnewschool.voicesnapmessenger.Util_Common.GifLoading
import retrofit2.Call
import retrofit2.Response

object StudentAPIServices {


    fun updateReadStatus(
        activity: Activity?,
        header_id: String?,
        detail_id: String?,
        ApiType: String?,
        callBack: TextMessagesClickListener
    ){

        val loginToken: String? = Util_shared_preferences.getLoginToken(activity)
        val MobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val ModuleType: String? = Util_shared_preferences.getModuleType(activity)
        val SchoolID: String? = Util_shared_preferences.getStudentSchoolID(activity)
        val StudentID: String? = Util_shared_preferences.getStudentID(activity)
        val ClassID: String? = Util_shared_preferences.getStudentClassID(activity)
        val SectionID: String? = Util_shared_preferences.getStudentSectionID(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty("login_token", loginToken)
        jsonObject.addProperty("mobile_number", MobileNumber)
        jsonObject.addProperty("school_id", SchoolID)
        jsonObject.addProperty("student_id", StudentID)
        jsonObject.addProperty("class_id", ClassID)
        jsonObject.addProperty("section_id", SectionID)
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty("header_id", header_id)
        jsonObjectRequest.addProperty("detail_id", detail_id)
        jsonObjectRequest.addProperty("module_type", ModuleType)
        jsonObject.add("request", jsonObjectRequest)
        Log.d("updateReadStatusRequest", jsonObject.toString())

        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        var call:Call<StatusMessageResponse?>?
        if(ApiType.equals(API_NORMAL)){
            call= apiInterface.updateReadStatus(jsonObject)
        }
        else{
            call= apiInterface.updateReadStatus_Archive(jsonObject)
        }
        call!!.enqueue(object : retrofit2.Callback<StatusMessageResponse?> {
            override fun onResponse(
                call: Call<StatusMessageResponse?>?,
                response: Response<StatusMessageResponse?>?
            ) {
                try {
                    val responseBody = response?.body()
                    val gson = Gson()
                    Log.d("updateReadStatus", gson.toJson(response))
                    if (response?.code() == 200) {
                        if (responseBody?.status == 1) {
                            callBack.callBackReadStatus(true)
                        }
                    }
                    else if (response?.code() == 400 || response?.code() == 500) {
                        callBack.callBackReadStatus(false)
                        val errorResponseBody = Gson().fromJson(
                            response.errorBody()?.charStream(),
                            StatusMessageResponse::class.java
                        )
                        UtilConstants.handleErrorResponse(
                            activity,
                            response.code(),
                            errorResponseBody
                        )
                    } else {
                        callBack.callBackReadStatus(false)
                        UtilConstants.normalToast(
                            activity,
                            activity?.getString(R.string.Service_unavailable)
                        )
                    }
                } catch (e: Exception) {
                    Log.d("Exception", e.toString())
                }
            }

            override fun onFailure(call: Call<StatusMessageResponse?>?, t: Throwable?) {
                Log.d("Failure", t.toString())
                UtilConstants.normalToast(activity, t.toString())
            }
        })
    }

    fun getTextMessages(activity: Activity?, callBack: GetTextMessagesCallBack, ApiType: String?) {

        val mobileNumber = Util_shared_preferences.getMobileNumber(activity)
        val loginToken = Util_shared_preferences.getLoginToken(activity)
        val schoolID = Util_shared_preferences.getStudentSchoolID(activity)
        val StudentID = Util_shared_preferences.getStudentID(activity)
        val ClassID = Util_shared_preferences.getStudentClassID(activity)
        val SectionID = Util_shared_preferences.getStudentSectionID(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, loginToken)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, schoolID)
        jsonObject.addProperty(STUDENT_ID, StudentID)
        jsonObject.addProperty(CLASS_ID, ClassID)
        jsonObject.addProperty(SECTION_ID, SectionID)
        Log.d("ParentTextMsgRequest", jsonObject.toString())

        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        var call:Call<GetTextMessages?>?
        if(ApiType.equals(API_NORMAL)){
            call= apiInterface.getTextMessages(jsonObject)
        }
        else{
            call= apiInterface.getTextMessages_Archive(jsonObject)
        }
        call!!.enqueue(object :
            retrofit2.Callback<GetTextMessages?> {
            override fun onResponse(
                call: Call<GetTextMessages?>?,
                response: Response<GetTextMessages?>?
            ) {
                try {
                    val responseBody = response?.body()
                    val gson = Gson()
                    Log.d("logoutResponse", gson.toJson(response))
                    if (response?.code() == 200) {
                        if (responseBody!!.status == 1) {
                            if (ApiType.equals(API_NORMAL)) {
                                callBack.callBackTextMessages(responseBody)
                            } else {
                                callBack.callBackTextMessages_Archive(responseBody)
                            }
                        } else if (response?.code() == 400 || response?.code() == 500) {
                            val errorResponseBody = Gson().fromJson(
                                response.errorBody()?.charStream(),
                                StatusMessageResponse::class.java
                            )
                            UtilConstants.handleParentErrorResponse(activity, response.code(), errorResponseBody,ApiType
                            )

                        } else {
                            UtilConstants.parentCustomFailureAlert(activity, responseBody.message,ApiType)
                        }
                    } else if (response?.code() == 400 || response?.code() == 500) {
                        val errorResponseBody = Gson().fromJson(
                            response.errorBody()?.charStream(),
                            StatusMessageResponse::class.java
                        )
                        UtilConstants.handleParentErrorResponse(
                            activity,
                            response.code(),
                            errorResponseBody,ApiType
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
                Log.d("Failure", t.toString())
                UtilConstants.normalToast(activity, t.toString())
            }
        })
    }
}