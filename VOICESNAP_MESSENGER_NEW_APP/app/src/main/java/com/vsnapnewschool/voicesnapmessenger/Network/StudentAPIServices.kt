package com.vsnapnewschool.voicesnapmessenger.Network

import android.app.Activity
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Rest.APIClient
import com.vsnapnewschool.voicesnapmessenger.CallBacks.*
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.CLASS_ID
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.FILE_ARRAY
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.LEAVE_FILE
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.LEAVE_FROM
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.LEAVE_OTHER_DESCRIPTION
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.LEAVE_REASON
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.LEAVE_TO
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.LEAVE_TYPE_ID
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.LOGIN_TOKEN
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.MOBILE_NUMBER
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.SCHOOL_ID
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.SECTION_ID
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.STUDENT_ID
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.STUDENT_NAME
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.*
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetTextMessages
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetVoiceMessages
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StatusMessageResponse
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.API_NORMAL
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.DateIdTimeTable
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.LeaveFrom
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.LeaveOtherDescription
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.LeaveReason
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.LeaveTo
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.SelectedLeaveID
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
        callBack: ReadStatusCallBacak?
    ) {

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
        var call: Call<StatusMessageResponse?>?
        if (ApiType.equals(API_NORMAL)) {
            call = apiInterface.updateReadStatus(jsonObject)
        } else {
            call = apiInterface.updateReadStatus_Archive(jsonObject)
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
                            callBack?.callBackReadStatus(true)
                        }
                    } else if (response?.code() == 400 || response?.code() == 500) {
                        callBack?.callBackReadStatus(false)
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
                        callBack?.callBackReadStatus(false)
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

    fun getTextMessages(
        activity: Activity?,
        callBack: GetTextMessagesCallBack,
        ApiType: String?,
        recyclerview: RecyclerView,
        shimmerFrameLayout: ShimmerFrameLayout
    ) {

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
        var call: Call<GetTextMessages?>?
        if (ApiType.equals(API_NORMAL)) {
            call = apiInterface.getTextMessages(jsonObject)
        } else {
            call = apiInterface.getTextMessages_Archive(jsonObject)
        }
        call!!.enqueue(object :
            retrofit2.Callback<GetTextMessages?> {
            override fun onResponse(
                call: Call<GetTextMessages?>?,
                response: Response<GetTextMessages?>?
            ) {
                try {
                    shimmerFrameLayout.stopShimmerAnimation()
                    recyclerview.visibility = View.VISIBLE
                    shimmerFrameLayout.visibility = View.GONE

                    val responseBody = response?.body()
                    val gson = Gson()
                    Log.d("TextMessageResponse", gson.toJson(response))
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
                            UtilConstants.handleParentErrorResponse(
                                activity, response.code(), errorResponseBody, ApiType
                            )

                        } else {
                            UtilConstants.parentCustomFailureAlert(
                                activity,
                                responseBody.message,
                                ApiType
                            )
                        }
                    } else if (response?.code() == 400 || response?.code() == 500) {
                        val errorResponseBody = Gson().fromJson(
                            response.errorBody()?.charStream(),
                            StatusMessageResponse::class.java
                        )
                        UtilConstants.handleParentErrorResponse(
                            activity,
                            response.code(),
                            errorResponseBody, ApiType
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

    fun getVoiceMessages(
        activity: Activity?,
        callBack: GetVoiceMessageCallBack,
        ApiType: String?,
        recyclerview: RecyclerView,
        shimmerFrameLayout: ShimmerFrameLayout
    ) {

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
        Log.d("ParentVoiceMsgRequest", jsonObject.toString())

        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        var call: Call<GetVoiceMessages?>?
        if (ApiType.equals(API_NORMAL)) {
            call = apiInterface.getVoiceMessages(jsonObject)
        } else {
            call = apiInterface.getVoiceMessages_Archive(jsonObject)
        }
        call!!.enqueue(object :
            retrofit2.Callback<GetVoiceMessages?> {
            override fun onResponse(
                call: Call<GetVoiceMessages?>?,
                response: Response<GetVoiceMessages?>?
            ) {
                try {
                    shimmerFrameLayout.stopShimmerAnimation()
                    recyclerview.visibility = View.VISIBLE
                    shimmerFrameLayout.visibility = View.GONE

                    val responseBody = response?.body()
                    val gson = Gson()
                    Log.d("VoiceMessageResponse", gson.toJson(response))
                    if (response?.code() == 200) {
                        if (responseBody!!.status == 1) {
                            if (ApiType.equals(API_NORMAL)) {
                                callBack.callBackVoiceMessages(responseBody)
                            } else {
                                callBack.callBackVoiceMessages_Archive(responseBody)
                            }

                        } else if (response?.code() == 400 || response?.code() == 500) {
                            val errorResponseBody = Gson().fromJson(
                                response.errorBody()?.charStream(),
                                StatusMessageResponse::class.java
                            )
                            UtilConstants.handleParentErrorResponse(
                                activity, response.code(), errorResponseBody, ApiType
                            )

                        } else {
                            UtilConstants.parentCustomFailureAlert(
                                activity,
                                responseBody.message,
                                ApiType
                            )
                        }
                    } else if (response?.code() == 400 || response?.code() == 500) {
                        val errorResponseBody = Gson().fromJson(
                            response.errorBody()?.charStream(),
                            StatusMessageResponse::class.java
                        )
                        UtilConstants.handleParentErrorResponse(
                            activity,
                            response.code(),
                            errorResponseBody, ApiType
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

            override fun onFailure(call: Call<GetVoiceMessages?>?, t: Throwable?) {
                Log.d("Failure", t.toString())
                UtilConstants.normalToast(activity, t.toString())
            }
        })
    }


    fun studentApplyLeave(activity: Activity?) {

        Log.d("test", "test")
        val jsonApplyLeaveRequest = jsonApplyLeave(activity)


        GifLoading.loading(activity, true)
        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.studentApplyLeave(jsonApplyLeaveRequest)!!
            .enqueue(object : retrofit2.Callback<StatusMessageResponse?> {
                override fun onResponse(
                    call: Call<StatusMessageResponse?>?,
                    response: Response<StatusMessageResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("NoticeEntireSchool-Res:", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                UtilConstants.parentcustomSuccessAlert(
                                    activity,
                                    responseBody.message
                                )
                            } else {
                                UtilConstants.customFailureAlert(activity, responseBody!!.message)

                            }
                        } else if (response?.code() == 400 || response?.code() == 500) {
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

    private fun jsonApplyLeave(activity: Activity?): JsonObject {
        val mobileNumber = Util_shared_preferences.getMobileNumber(activity)
        val loginToken = Util_shared_preferences.getLoginToken(activity)
        val schoolID = Util_shared_preferences.getStudentSchoolID(activity)
        val StudentID = Util_shared_preferences.getStudentID(activity)
        val ClassID = Util_shared_preferences.getStudentClassID(activity)
        val SectionID = Util_shared_preferences.getStudentSectionID(activity)
        val StudentName = Util_shared_preferences.getStudentName(activity)


        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, loginToken)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, schoolID)
        jsonObject.addProperty(STUDENT_ID, StudentID)
        jsonObject.addProperty(CLASS_ID, ClassID)
        jsonObject.addProperty(SECTION_ID, SectionID)
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty(STUDENT_NAME, StudentName)
        jsonObjectRequest.addProperty(LEAVE_FROM, LeaveFrom)
        jsonObjectRequest.addProperty(LEAVE_TO, LeaveTo)
        jsonObjectRequest.addProperty(LEAVE_TYPE_ID, SelectedLeaveID)
        if (SelectedLeaveID.equals("0")) {
            jsonObjectRequest.addProperty(LEAVE_OTHER_DESCRIPTION, LeaveOtherDescription)

        } else {
            jsonObjectRequest.addProperty(LEAVE_OTHER_DESCRIPTION, "")

        }
        jsonObjectRequest.addProperty(LEAVE_REASON, LeaveReason)
        val jsonarray = JsonArray()
        val jsonsIds = JsonObject()
        jsonsIds.addProperty(LEAVE_FILE, "")
        jsonarray.add(jsonsIds)
        jsonObject.add(ApiRequestValues.REQUEST, jsonObjectRequest)
        Log.d("ApplyLeaveRequest", jsonObject.toString())

        return jsonObject

    }

    fun getUpcomingEventsList(activity: Activity?, callback: UpcomingEventsCallback) {

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
        Log.d("EventRequest", jsonObject.toString())

        GifLoading.loading(activity, true)
        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getUpcomingEvents(jsonObject)!!
            .enqueue(object : retrofit2.Callback<UpcomingEventsResponse?> {
                override fun onResponse(
                    call: Call<UpcomingEventsResponse?>?,
                    response: Response<UpcomingEventsResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("UpcomingEvents-Res:", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                callback.callbackUpcomingEvents(responseBody)
                            } else {
                                UtilConstants.customFailureAlert(activity, responseBody!!.message)

                            }
                        } else if (response?.code() == 400 || response?.code() == 500) {
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
                            UtilConstants.normalToast(
                                activity,
                                activity?.getString(R.string.Service_unavailable)
                            )
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }
                }

                override fun onFailure(call: Call<UpcomingEventsResponse?>?, t: Throwable?) {
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    fun getNoticeBoard(activity: Activity?, callback: GetNoticeBoardCallBack) {


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
        Log.d("NoticeBoardRequest", jsonObject.toString())

        GifLoading.loading(activity, true)
        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getNoticeBoard(jsonObject)!!
            .enqueue(object : retrofit2.Callback<GetNoticeBoardResponse?> {
                override fun onResponse(
                    call: Call<GetNoticeBoardResponse?>?,
                    response: Response<GetNoticeBoardResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("UpcomingEvents-Res:", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                callback.callBackNoticeBoard(responseBody)
                            } else {
                                UtilConstants.customFailureAlert(activity, responseBody!!.message)

                            }
                        } else if (response?.code() == 400 || response?.code() == 500) {
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
                            UtilConstants.normalToast(
                                activity,
                                activity?.getString(R.string.Service_unavailable)
                            )
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }
                }

                override fun onFailure(call: Call<GetNoticeBoardResponse?>?, t: Throwable?) {
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    fun getImageFiles(
        activity: Activity?,
        callBack: GetImageListCallBack,
        ApiType: String?,
        recyclerview: RecyclerView,
        shimmerFrameLayout: ShimmerFrameLayout
    ) {

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
        Log.d("ParentImagesRequest", jsonObject.toString())

        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        var call: Call<GetImageFilesResponse?>?
        if (ApiType.equals(API_NORMAL)) {
            call = apiInterface.getParentImageFiles(jsonObject)
        } else {
            call = apiInterface.getParentImageFilesArchive(jsonObject)
        }
        call!!.enqueue(object :
            retrofit2.Callback<GetImageFilesResponse?> {
            override fun onResponse(
                call: Call<GetImageFilesResponse?>?,
                response: Response<GetImageFilesResponse?>?
            ) {
                try {
                    shimmerFrameLayout.stopShimmerAnimation()
                    recyclerview.visibility = View.VISIBLE
                    shimmerFrameLayout.visibility = View.GONE

                    val responseBody = response?.body()
                    val gson = Gson()
                    Log.d("ImageFileResponse", gson.toJson(response))
                    if (response?.code() == 200) {
                        if (responseBody!!.status == 1) {
                            if (ApiType.equals(API_NORMAL)) {
                                callBack.callBackImageFiles(responseBody)
                            } else {
                                callBack.callBackImageFiles_Archive(responseBody)
                            }

                        } else if (response?.code() == 400 || response?.code() == 500) {
                            val errorResponseBody = Gson().fromJson(
                                response.errorBody()?.charStream(),
                                StatusMessageResponse::class.java
                            )
                            UtilConstants.handleParentErrorResponse(
                                activity, response.code(), errorResponseBody, ApiType
                            )

                        } else {
                            UtilConstants.parentCustomFailureAlert(
                                activity,
                                responseBody.message,
                                ApiType
                            )
                        }
                    } else if (response?.code() == 400 || response?.code() == 500) {
                        val errorResponseBody = Gson().fromJson(
                            response.errorBody()?.charStream(),
                            StatusMessageResponse::class.java
                        )
                        UtilConstants.handleParentErrorResponse(
                            activity,
                            response.code(),
                            errorResponseBody, ApiType
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

            override fun onFailure(call: Call<GetImageFilesResponse?>?, t: Throwable?) {
                Log.d("Failure", t.toString())
                UtilConstants.normalToast(activity, t.toString())
            }
        })
    }

    fun getPdfFiles(
        activity: Activity?,
        callBack: GetPdfListCallBack,
        ApiType: String?,
        recyclerview: RecyclerView,
        shimmerFrameLayout: ShimmerFrameLayout
    ) {

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
        Log.d("ParentPdfRequest", jsonObject.toString())

        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        var call: Call<GetPdfFilesResponse?>?
        if (ApiType.equals(API_NORMAL)) {
            call = apiInterface.getParentPdfList(jsonObject)
        } else {
            call = apiInterface.getParentPdfListArchive(jsonObject)
        }
        call!!.enqueue(object :
            retrofit2.Callback<GetPdfFilesResponse?> {
            override fun onResponse(
                call: Call<GetPdfFilesResponse?>?,
                response: Response<GetPdfFilesResponse?>?
            ) {
                try {
                    shimmerFrameLayout.stopShimmerAnimation()
                    recyclerview.visibility = View.VISIBLE
                    shimmerFrameLayout.visibility = View.GONE

                    val responseBody = response?.body()
                    Log.d("responseBody", responseBody!!.data.toString())
                    val gson = Gson()
                    Log.d("PDF_fileResponse", gson.toJson(response))
                    if (response?.code() == 200) {
                        if (responseBody!!.status == 1) {
                            if (ApiType.equals(API_NORMAL)) {
                                callBack.callBackPdfFiles(responseBody)
                            } else {
                                callBack.callBackPdfFilesArchive(responseBody)
                            }

                        } else if (response?.code() == 400 || response?.code() == 500) {
                            val errorResponseBody = Gson().fromJson(
                                response.errorBody()?.charStream(),
                                StatusMessageResponse::class.java
                            )
                            UtilConstants.handleParentErrorResponse(
                                activity, response.code(), errorResponseBody, ApiType
                            )

                        } else {
                            UtilConstants.parentCustomFailureAlert(
                                activity,
                                responseBody.message,
                                ApiType
                            )
                        }
                    } else if (response?.code() == 400 || response?.code() == 500) {
                        val errorResponseBody = Gson().fromJson(
                            response.errorBody()?.charStream(),
                            StatusMessageResponse::class.java
                        )
                        UtilConstants.handleParentErrorResponse(
                            activity,
                            response.code(),
                            errorResponseBody, ApiType
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

            override fun onFailure(call: Call<GetPdfFilesResponse?>?, t: Throwable?) {
                Log.d("Failure", t.toString())
                UtilConstants.normalToast(activity, t.toString())
            }
        })
    }

    fun getParentAssignment(
        activity: Activity?,
        callBack: GetParentAssingmentCallback,
        ApiType: String?,
        recyclerview: RecyclerView,
        shimmerFrameLayout: ShimmerFrameLayout
    ) {

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
        Log.d("ParentAssingmentRequest", jsonObject.toString())

        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        var call: Call<GetParentAssignmentResponse?>?
        if (ApiType.equals(API_NORMAL)) {
            call = apiInterface.getParentAssingment(jsonObject)
        } else {
            call = apiInterface.getParentAssingmentArchive(jsonObject)
        }
        call!!.enqueue(object :
            retrofit2.Callback<GetParentAssignmentResponse?> {
            override fun onResponse(
                call: Call<GetParentAssignmentResponse?>?,
                response: Response<GetParentAssignmentResponse?>?
            ) {
                try {
                    shimmerFrameLayout.stopShimmerAnimation()
                    recyclerview.visibility = View.VISIBLE
                    shimmerFrameLayout.visibility = View.GONE

                    val responseBody = response?.body()
                    val gson = Gson()
                    Log.d("ParentAssingmentRes", gson.toJson(response))
                    if (response?.code() == 200) {
                        if (responseBody!!.status == 1) {
                            if (ApiType.equals(API_NORMAL)) {
                                callBack.callBackAssignmentDue(responseBody)
                            } else {
                                callBack.callBackAssignmentDueArchive(responseBody)
                            }

                        } else if (response?.code() == 400 || response?.code() == 500) {
                            val errorResponseBody = Gson().fromJson(
                                response.errorBody()?.charStream(),
                                StatusMessageResponse::class.java
                            )
                            UtilConstants.handleParentErrorResponse(
                                activity, response.code(), errorResponseBody, ApiType
                            )

                        } else {
                            UtilConstants.parentCustomFailureAlert(
                                activity,
                                responseBody.message,
                                ApiType
                            )
                        }
                    } else if (response?.code() == 400 || response?.code() == 500) {
                        val errorResponseBody = Gson().fromJson(
                            response.errorBody()?.charStream(),
                            StatusMessageResponse::class.java
                        )
                        UtilConstants.handleParentErrorResponse(
                            activity,
                            response.code(),
                            errorResponseBody, ApiType
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

            override fun onFailure(call: Call<GetParentAssignmentResponse?>?, t: Throwable?) {
                Log.d("Failure", t.toString())
                UtilConstants.normalToast(activity, t.toString())
            }
        })
    }

    fun parentSubmitAssingment(activity: Activity?) {

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

        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty(
            ApiRequestValues.HEADER_ID,
            UtilConstants.ParentAssignmentHeaderID
        )

        jsonObjectRequest.addProperty(
            ApiRequestValues.DETAIL_ID,
            UtilConstants.ParentAssignmentDetailID
        )
        jsonObjectRequest.addProperty(
            ApiRequestValues.FILETYPE,
            UtilConstants.ParentAssingmentFileType
        )
        jsonObjectRequest.addProperty(ApiRequestValues.DESCRIPTION, UtilConstants.Description)
        jsonObjectRequest.addProperty(
            ApiRequestValues.IS_ARCHIVE,
            UtilConstants.ParentAssignmentIsArchieve
        )
        val jsonFilearray = JsonArray()
        val jsonsimgIds = JsonObject()
        UtilConstants.AWSUploadedFilesList.forEach {
            Log.d("Testtype", it.contentype!!)

            jsonsimgIds.addProperty("file_path", it.filepath)
            jsonsimgIds.addProperty("original_name", it.fileName)
            jsonFilearray.add(jsonsimgIds)

        }
        jsonObjectRequest.add(FILE_ARRAY, jsonFilearray)
        jsonObject.add(ApiRequestValues.REQUEST, jsonObjectRequest)
        Log.d("ParentAssingmentFile", jsonObject.toString())

        GifLoading.loading(activity, true)
        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.parentSubmitAssingment(jsonObject)!!
            .enqueue(object : retrofit2.Callback<StatusMessageResponse?> {
                override fun onResponse(
                    call: Call<StatusMessageResponse?>?,
                    response: Response<StatusMessageResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("ParentAssignment-Res:", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                UtilConstants.parentcustomSuccessAlert(
                                    activity,
                                    responseBody.message
                                )
                            } else {
                                UtilConstants.customFailureAlert(activity, responseBody!!.message)

                            }
                        } else if (response?.code() == 400 || response?.code() == 500) {
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


    fun getParentSubmittedAssingmentFiles(
        activity: Activity?,
        callBAck: GetParentSubmittedAssignmentCallback
    ) {
        Log.d("testapi", "test")
        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)
        val schoolID = Util_shared_preferences.getStudentSchoolID(activity)

        val StudentID = Util_shared_preferences.getStudentID(activity)
        val ClassID = Util_shared_preferences.getStudentClassID(activity)
        val SectionID = Util_shared_preferences.getStudentSectionID(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, token)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, schoolID)

        jsonObject.addProperty(STUDENT_ID, StudentID)
        jsonObject.addProperty(CLASS_ID, ClassID)
        jsonObject.addProperty(SECTION_ID, SectionID)

        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty(ApiRequestValues.HEADER_ID, UtilConstants.ParentAssignmentHeaderID)
        jsonObjectRequest.addProperty(ApiRequestValues.FILETYPE, UtilConstants.AssignmentFiletype)
        jsonObject.add(ApiRequestValues.REQUEST, jsonObjectRequest)

        Log.d("GetAssingmentViewFile", jsonObject.toString())
        val apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.parentSubmittedAssignmentFiles(jsonObject)!!
            .enqueue(object : retrofit2.Callback<GetParentAssingmentFiles?> {
                override fun onResponse(
                    call: Call<GetParentAssingmentFiles?>?,
                    response: Response<GetParentAssingmentFiles?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("AssignmentResponse", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                callBAck.callBackViewSubmittedFiles(responseBody)

                            } else {
                                UtilConstants.customFailureAlert(activity, responseBody!!.message)

                            }
                        } else if (response?.code() == 400 || response?.code() == 500) {
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
                            UtilConstants.normalToast(
                                activity,
                                activity?.getString(R.string.Service_unavailable)
                            )
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }
                }

                override fun onFailure(call: Call<GetParentAssingmentFiles?>?, t: Throwable?) {
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    fun getDatesHomework(activity: Activity?,callback: GetDatesHomeWorkCallBack) {
        val jsondateshomework=jsonDatesHomework(activity)

        GifLoading.loading(activity, true)
        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getDaysforHomework(jsondateshomework)!!
            .enqueue(object : retrofit2.Callback<GetDatesHomeWorkListResponse?> {
                override fun onResponse(
                    call: Call<GetDatesHomeWorkListResponse?>?,
                    response: Response<GetDatesHomeWorkListResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("DatesHW-Res:", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                callback.callBackDatesHomework(responseBody)
                            } else {
                                UtilConstants.customFailureAlert(activity, responseBody!!.message)

                            }
                        } else if (response?.code() == 400 || response?.code() == 500) {
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
                            UtilConstants.normalToast(
                                activity,
                                activity?.getString(R.string.Service_unavailable)
                            )
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }
                }

                override fun onFailure(call: Call<GetDatesHomeWorkListResponse?>?, t: Throwable?) {
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    private fun jsonDatesHomework(activity: Activity?): JsonObject {


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
        Log.d("DatesHomeworkreq",jsonObject.toString())

        return jsonObject

    }
    fun getHomework(activity: Activity?,callback: GetHomeWorkCallBack) {
        val jsonhomework=jsonHomework(activity)

        GifLoading.loading(activity, true)
        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getHomework(jsonhomework)!!
            .enqueue(object : retrofit2.Callback<GetHomeWorkListResponse?> {
                override fun onResponse(
                    call: Call<GetHomeWorkListResponse?>?,
                    response: Response<GetHomeWorkListResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("HW-Res:", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                callback.callBackHomework(responseBody)
                            } else {

                                UtilConstants.customFailureAlert(activity, responseBody!!.message)

                            }
                        } else if (response?.code() == 400 || response?.code() == 500) {
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
                            UtilConstants.normalToast(
                                activity,
                                activity?.getString(R.string.Service_unavailable)
                            )
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }
                }

                override fun onFailure(call: Call<GetHomeWorkListResponse?>?, t: Throwable?) {
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    private fun jsonHomework(activity: Activity?): JsonObject {


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
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty(ApiRequestValues.HOMEWORK_DATE, UtilConstants.Datehomework)
        jsonObject.add(ApiRequestValues.REQUEST, jsonObjectRequest)
        Log.d("HW-req",jsonObject.toString())

        return jsonObject

    }
    fun ParentVideos(activity: Activity?,callback: GetParentVideoCallBack) {
        val jsonRequest = jsonParentVideos(activity)
        Log.d("Request", jsonRequest.toString())
        GifLoading.loading(activity, true)

        val apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.ParentVideos(jsonRequest)!!
            .enqueue(object : retrofit2.Callback<ParentVideoResponse?> {
                override fun onResponse(
                    call: Call<ParentVideoResponse?>?,
                    response: Response<ParentVideoResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)

                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("Res:", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                callback.callbackvideo(responseBody)
                            } else {
                                UtilConstants.customFailureAlert(activity, responseBody!!.message)

                            }
                        } else if (response?.code() == 400 || response?.code() == 500) {
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
                            UtilConstants.normalToast(
                                activity,
                                activity?.getString(R.string.Service_unavailable)
                            )
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }
                }

                override fun onFailure(call: Call<ParentVideoResponse?>?, t: Throwable?) {
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    private fun jsonParentVideos(activity: Activity?): JsonObject {
        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty("login_token", token)
        jsonObject.addProperty("mobile_number", mobileNumber)
        jsonObject.addProperty("school_id", Util_shared_preferences.getStudentSchoolID(activity))
        jsonObject.addProperty("student_id", Util_shared_preferences.getStudentID(activity))
        jsonObject.addProperty("class_id", Util_shared_preferences.getStudentClassID(activity))
        jsonObject.addProperty("section_id", Util_shared_preferences.getStudentSectionID(activity))
        return jsonObject

    }

    //Chat

    fun StudentStaffforChat(activity: Activity?,callBack: GetStudentStaffChatCallBack) {
        val jsonRequest = jsonStudentstaffforchat(activity)
        Log.d("Request", jsonRequest.toString())
        GifLoading.loading(activity, true)

        val apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.StudentStaffsforChat(jsonRequest)!!
            .enqueue(object : retrofit2.Callback<StudentStaffChatResponse?> {
                override fun onResponse(
                    call: Call<StudentStaffChatResponse?>?,
                    response: Response<StudentStaffChatResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)

                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("Res:", gson.toJson(response))
//                        StudentStaffDetails.clear()
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                callBack.callbackstudentstaffchat(responseBody)
//                                StudentStaffDetails=responseBody.data as ArrayList<StaffChatDetails>
//                                Adapterview()
                            } else {
                                UtilConstants.customFailureAlert(activity, responseBody!!.message)

                            }
                        } else if (response?.code() == 400 || response?.code() == 500) {
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
                            UtilConstants.normalToast(
                                activity,
                                activity?.getString(R.string.Service_unavailable)
                            )
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }
                }

                override fun onFailure(call: Call<StudentStaffChatResponse?>?, t: Throwable?) {
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    private fun jsonStudentstaffforchat(activity: Activity?): JsonObject {
        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty("login_token", token)
        jsonObject.addProperty("mobile_number", mobileNumber)
        jsonObject.addProperty("school_id", Util_shared_preferences.getStudentSchoolID(activity))
        jsonObject.addProperty("student_id", Util_shared_preferences.getStudentID(activity))
        jsonObject.addProperty("class_id", Util_shared_preferences.getStudentClassID(activity))
        jsonObject.addProperty("section_id", Util_shared_preferences.getStudentSectionID(activity))
        return jsonObject

    }


    fun StudentChatScreen(activity: Activity?,currentoffset: Int,callback:GetStudentChatScreenCallBack) {
        val jsonRequest = jsonStudentchatscreen(activity,currentoffset)
        Log.d("Request", jsonRequest.toString())
        GifLoading.loading(activity, true)

        val apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.studentChatScreen(jsonRequest)!!
            .enqueue(object : retrofit2.Callback<StudentChatScreenResponse?> {
                override fun onResponse(
                    call: Call<StudentChatScreenResponse?>?,
                    response: Response<StudentChatScreenResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)

                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("Res:", gson.toJson(response))
//                        StaffChatscreenDetails.clear()
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                callback.callbackstudentchatscreen(responseBody!!,currentoffset)

                            } else {
                                UtilConstants.customFailureAlert(activity, responseBody!!.message)

                            }

                        } else if (response?.code() == 400 || response?.code() == 500) {
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
                            UtilConstants.normalToast(
                                activity,
                                activity?.getString(R.string.Service_unavailable)
                            )
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }
                }

                override fun onFailure(call: Call<StudentChatScreenResponse?>?, t: Throwable?) {
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    private fun jsonStudentchatscreen(activity: Activity?,offset:Int): JsonObject {
        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty("login_token", token)
        jsonObject.addProperty("mobile_number", mobileNumber)
        jsonObject.addProperty("school_id", Util_shared_preferences.getStudentSchoolID(activity))
        jsonObject.addProperty("student_id", Util_shared_preferences.getStudentID(activity))
        jsonObject.addProperty("class_id", Util_shared_preferences.getStudentClassID(activity))
        jsonObject.addProperty("section_id", Util_shared_preferences.getStudentSectionID(activity))
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty("off_set", offset)
        jsonObjectRequest.addProperty("staff_id", UtilConstants.chatstaffid)
        jsonObjectRequest.addProperty("subject_id", UtilConstants.chatsubjectid)
        jsonObjectRequest.addProperty("is_class_teacher", UtilConstants.chatisclassteacher.toString())
        jsonObject.add("request", jsonObjectRequest)
        return jsonObject

    }

    fun StudentAskQuestion(activity: Activity?, question: String,callback:GetSutudentAskQueCallBack) {
        val jsonRequest = jsonStudentaskQuestion(activity,question)
        Log.d("Request", jsonRequest.toString())
        GifLoading.loading(activity, true)

        val apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.studentAskQuestion(jsonRequest)!!
            .enqueue(object : retrofit2.Callback<StudentChatScreenResponse?> {
                override fun onResponse(
                    call: Call<StudentChatScreenResponse?>?,
                    response: Response<StudentChatScreenResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)

                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("Res:", gson.toJson(response))
//                        lblContent.setText("")
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                callback.callbackstudentaskque(responseBody)
//                                StudentChatscreenDetails.addAll(responseBody.data.chat_data as ArrayList<StudentChatScreenResponse.Data.StudentChatData>)
//                                ParentAdapterview()
                            } else {
                                UtilConstants.customFailureAlert(activity, responseBody!!.message)

                            }
                        } else if (response?.code() == 400 || response?.code() == 500) {
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
                            UtilConstants.normalToast(
                                activity,
                                activity?.getString(R.string.Service_unavailable)
                            )
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }
                }

                override fun onFailure(call: Call<StudentChatScreenResponse?>?, t: Throwable?) {
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    private fun jsonStudentaskQuestion(activity: Activity?, question: String): JsonObject {
        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty("login_token", token)
        jsonObject.addProperty("mobile_number", mobileNumber)
        jsonObject.addProperty("school_id", Util_shared_preferences.getStudentSchoolID(activity))
        jsonObject.addProperty("student_id", Util_shared_preferences.getStudentID(activity))
        jsonObject.addProperty("class_id", Util_shared_preferences.getStudentClassID(activity))
        jsonObject.addProperty("section_id", Util_shared_preferences.getStudentSectionID(activity))
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty("student_name", Util_shared_preferences.getStudentName(activity))
        jsonObjectRequest.addProperty("staff_id", UtilConstants.chatstaffid)
        jsonObjectRequest.addProperty("subject_id", UtilConstants.chatsubjectid)
        jsonObjectRequest.addProperty("is_class_teacher", UtilConstants.chatisclassteacher.toString())
        jsonObjectRequest.addProperty("question", question)
        jsonObject.add("request", jsonObjectRequest)
        return jsonObject

    }
    fun getDatesForTimetable(activity: Activity?,callback: GetDateForTimeTableCallBack) {
        val jsondatesTimetable=jsonDateTimetable(activity)

        GifLoading.loading(activity, true)
        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getTimeTabledays(jsondatesTimetable)!!
            .enqueue(object : retrofit2.Callback<GetDaysTimeTable?> {
                override fun onResponse(
                    call: Call<GetDaysTimeTable?>?,
                    response: Response<GetDaysTimeTable?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("DatesHW-Res:", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                callback.callBackDatesTimeTable(responseBody)
                            } else {
                                UtilConstants.customFailureAlert(activity, responseBody!!.message)

                            }
                        } else if (response?.code() == 400 || response?.code() == 500) {
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
                            UtilConstants.normalToast(
                                activity,
                                activity?.getString(R.string.Service_unavailable)
                            )
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }
                }

                override fun onFailure(call: Call<GetDaysTimeTable?>?, t: Throwable?) {
                    Log.d("DaysFailure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    private fun jsonDateTimetable(activity: Activity?): JsonObject {

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
        Log.d("DatesTimeTableReq",jsonObject.toString())

        return jsonObject

    }

    fun getTimeTable(activity: Activity?,callback: TimeTableCallBack) {
        val jsontimetable= jsonTimetable(activity)

        GifLoading.loading(activity, true)
        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getStudentTimeTable(jsontimetable)!!
            .enqueue(object : retrofit2.Callback<GetTimeTableList?> {
                override fun onResponse(
                    call: Call<GetTimeTableList?>?,
                    response: Response<GetTimeTableList?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("HW-Res:", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                callback.callbackTimeTable(responseBody)
                            } else {

                                UtilConstants.customFailureAlert(activity, responseBody!!.message)

                            }
                        } else if (response?.code() == 400 || response?.code() == 500) {
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
                            UtilConstants.normalToast(
                                activity,
                                activity?.getString(R.string.Service_unavailable)
                            )
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }
                }

                override fun onFailure(call: Call<GetTimeTableList?>?, t: Throwable?) {
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    private fun jsonTimetable(activity: Activity?): JsonObject {
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
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty(ApiRequestValues.DAY_ID, DateIdTimeTable )
        jsonObject.add(ApiRequestValues.REQUEST, jsonObjectRequest)
        Log.d("TimeTable-Req:",jsonObject.toString())

        return jsonObject

    }
}