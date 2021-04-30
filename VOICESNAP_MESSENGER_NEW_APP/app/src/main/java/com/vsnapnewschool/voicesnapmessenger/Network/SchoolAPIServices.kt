@file:Suppress("DEPRECATION")

package com.vsnapnewschool.voicesnapmessenger.Network

import android.app.Activity
import android.provider.Settings
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Rest.APIClient
import com.vsnapnewschool.voicesnapmessenger.Activities.BaseActivity
import com.vsnapnewschool.voicesnapmessenger.Activities.TeacherApproveLeave
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GenerateOtpCallBack
import com.vsnapnewschool.voicesnapmessenger.CallBacks.ReturnGlobalValue
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.COUNTRY_ID
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.DESCRIPTION
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.DEVICE_TOKEN
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.DEVICE_TYPE
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.DURATION
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.ENCRYPT_PASSWORD
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.END_HOUR
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.END_MINUTE
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.GROUP_DATA
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.ID
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.ID_SCHOOL
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.IMEI_NUMBER
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.KEY_NAME
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.LEAVE_ID
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.LOGIN_TOKEN
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.MOBILE_NUMBER
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.NEW_PASSWORD
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.OLD_PASSWORD
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.OTP_TYPE
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.REQUEST
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.SCHEDULE_DATE
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.SCHEDULE_HOUR
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.SCHEDULE_MINUTE
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.SCHOOL_ID
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.SECTION_DATA
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.SECTION_ID
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.SECURE_ID
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.STAFF_DATA
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.STAFF_ID
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.STANDARD_DATA
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.STATUS
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.STUDENT_DATA
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.TOKEN
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.VOICE_FILEPATH
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.VOICE_TYPE
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.*
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.AWSUploadedFilesList
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.ApproveLeaveList
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.ApproveLeaveTypeStatus
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.ChildListDetails
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.EndMinute
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.IsAdmin
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.IsGroupHead
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.IsParent
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.IsPrincipal
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.IsStaff
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.LogoutOtherDeviceAlert
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_TEXT_HOMEWORK
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_TYPE
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_VOICE_HOMEWORK
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MaxEmergencyVoiceDuration
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MaxGeneralSMSCount
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MaxGeneralVoiceDuration
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MaxHomeWorkSMSCount
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MaxHomeWorkVoiceDuration
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.RecipientsType
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.ScheduleType
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.SchoolListDetails
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.VoiceFilePath
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.contentTypeImg
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.contentTypePdf
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.selectedSubjectID
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.voiceHistoryList
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import com.vsnapnewschool.voicesnapmessenger.Util_Common.GifLoading
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*

object SchoolAPIServices {
    lateinit var call: Call<StatusMessageResponse?>
    fun checkMobileNumber(activity: Activity?) {
        val CountryID = Util_shared_preferences.getCountryID(activity)
        val MobileNumber = Util_shared_preferences.getMobileNumber(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty(MOBILE_NUMBER, MobileNumber)
        jsonObject.addProperty("country_id", CountryID.toString())
        Log.d("Request", jsonObject.toString())
        GifLoading.loading(activity, true)

        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.checkMobileNumberExist(jsonObject)!!
            .enqueue(object : retrofit2.Callback<CheckMobileNumberResponse?> {
                override fun onResponse(
                    call: Call<CheckMobileNumberResponse?>?,
                    response: Response<CheckMobileNumberResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("CheckMobileResponse", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                if (responseBody.data[0].number_exists == 1) {

                                    if (responseBody.data[0].is_password_updated == 1) {

                                        if (responseBody.data[0].forget_password_requested == 1) {
                                            UtilConstants.OTPScreenType = "Forgot"
                                            UtilConstants.openOTPScreen(activity)

                                        } else {
                                            UtilConstants.openPasswordScreen(activity)
                                        }
                                    } else {
                                        UtilConstants.OTPScreenType = "New"
                                        UtilConstants.openOTPScreen(activity)
                                    }


                                } else {
                                    UtilConstants.customFailureAlert(
                                        activity, responseBody.message
                                    )
                                }
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

                override fun onFailure(call: Call<CheckMobileNumberResponse?>?, t: Throwable?) {
                    Log.d("Failure", t.toString())
                    GifLoading.loading(activity, false)
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    fun generateOTP(
        activity: Activity?,
        otpType: String?,
        mobileNumber: String?,
        callBack: GenerateOtpCallBack
    ) {

        val CountryID = Util_shared_preferences.getCountryID(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(COUNTRY_ID, CountryID.toString())
        jsonObject.addProperty(OTP_TYPE, otpType)
        Log.d("generateOTPRequest", jsonObject.toString())
        GifLoading.loading(activity, true)

        var apiInterface: ApiInterface =
            APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.generateOTP(jsonObject)!!
            .enqueue(object : retrofit2.Callback<generateOTPResponse?> {
                override fun onResponse(
                    call: Call<generateOTPResponse?>?,
                    response: Response<generateOTPResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("GenerateOTPResponse", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                Util_shared_preferences.putMobileNumber(activity, mobileNumber)
                                callBack.callBackValue(responseBody.data[0])

                                if (otpType.equals("forgot")) {
                                    UtilConstants.forgotDialNumberPopup(
                                        activity,
                                        responseBody.data[0]
                                    )

                                } else {
                                    UtilConstants.normalToast(activity, responseBody.message)
                                }

                            } else {
                                UtilConstants.customFailureAlert(activity, responseBody?.message)
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

                override fun onFailure(call: Call<generateOTPResponse?>?, t: Throwable?) {
                    Log.d("Failure", t.toString())
                    GifLoading.loading(activity, false)
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }


    fun validateOTP(activity: Activity?, MobileNumber: String?, otp: String?) {
        val jsonObject = JsonObject()
        jsonObject.addProperty(MOBILE_NUMBER, MobileNumber)
        jsonObject.addProperty(ApiRequestValues.OTP, otp)
        Log.d("ValidateOtp:Request", jsonObject.toString())
        GifLoading.loading(activity, true)

        var apiInterface: ApiInterface =
            APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.validateOTP(jsonObject)!!
            .enqueue(object : retrofit2.Callback<StatusMessageResponse?> {
                override fun onResponse(
                    call: Call<StatusMessageResponse?>?,
                    response: Response<StatusMessageResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("ValidateOTPResponse", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                UtilConstants.openSetNewPasswordScreen(activity)

                            } else {
                                UtilConstants.customFailureAlert(activity, responseBody?.message)
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
                    GifLoading.loading(activity, false)
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    fun setNewPassword(activity: Activity?, password: String?, MobileNumber: String?) {
        val jsonObject = JsonObject()
        jsonObject.addProperty(MOBILE_NUMBER, MobileNumber)
        jsonObject.addProperty(NEW_PASSWORD, password)
        Log.d("setNewPasswordRequest", jsonObject.toString())
        GifLoading.loading(activity, true)

        var apiInterface: ApiInterface =
            APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.setNewPassword(jsonObject)!!
            .enqueue(object : retrofit2.Callback<StatusMessageResponse?> {
                override fun onResponse(
                    call: Call<StatusMessageResponse?>?,
                    response: Response<StatusMessageResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("setNewPasswordResponse", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                generateNewLoginToken(activity, MobileNumber, password)

                            } else {
                                UtilConstants.customFailureAlert(activity, responseBody?.message)
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
                    GifLoading.loading(activity, false)
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    fun changePassword(activity: Activity?, password: String?) {
        val MobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty(MOBILE_NUMBER, MobileNumber)
        jsonObject.addProperty(NEW_PASSWORD, password)
        jsonObject.addProperty(OLD_PASSWORD, password)
        Log.d("changePassword:Request", jsonObject.toString())
        GifLoading.loading(activity, true)

        var apiInterface: ApiInterface =
            APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.changePassword(jsonObject)!!
            .enqueue(object : retrofit2.Callback<StatusMessageResponse?> {
                override fun onResponse(
                    call: Call<StatusMessageResponse?>?,
                    response: Response<StatusMessageResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("setNewPasswordResponse", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody!!.status == 1) {

                            } else {
                                UtilConstants.customFailureAlert(activity, responseBody.message)
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
                    GifLoading.loading(activity, false)
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    fun logoutFromSameDevice(activity: Activity?) {
        val token: String? = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty(TOKEN, token)
        Log.d("logoutFromSameDeviceReq", jsonObject.toString())
        GifLoading.loading(activity, true)

        var apiInterface: ApiInterface =
            APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.logoutfromSameDevice(jsonObject)!!
            .enqueue(object : retrofit2.Callback<StatusMessageResponse?> {
                override fun onResponse(
                    call: Call<StatusMessageResponse?>?,
                    response: Response<StatusMessageResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("logoutResponse", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                UtilConstants.openSignInScreen(activity)
                            } else {
                                UtilConstants.customFailureAlert(activity, responseBody?.message)
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
                    GifLoading.loading(activity, false)
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    fun logoutfromOtherDevice(activity: Activity?, mobileNumber: String?, Password: String?) {
        val jsonObject = JsonObject()
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        Log.d("logoutfromOtherRequest", jsonObject.toString())
        GifLoading.loading(activity, true)
        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.logoutfromOtherDevice(jsonObject)!!
            .enqueue(object : retrofit2.Callback<StatusMessageResponse?> {
                override fun onResponse(
                    call: Call<StatusMessageResponse?>?,
                    response: Response<StatusMessageResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("logoutResponse", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody!!.status == 1) {
                                generateNewLoginToken(activity, mobileNumber, Password)
                            } else {
                                UtilConstants.customFailureAlert(activity, responseBody.message)
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
                    GifLoading.loading(activity, false)
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    fun getLoginDetailsByToken(
        activity: Activity?,
        token: String?,
        MobileNumber: String?,
        Password: String?
    ) {

        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, token)
        Log.d("LoginDetailsByTokenReq", jsonObject.toString())
        GifLoading.loading(activity, true)
        var apiInterface: ApiInterface =
            APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getLoginDetailsByToken(jsonObject)!!
            .enqueue(object : retrofit2.Callback<LoginResponse?> {
                override fun onResponse(
                    call: Call<LoginResponse?>?,
                    response: Response<LoginResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("loginResponse", gson.toJson(response))
                        SchoolListDetails.clear()
                        ChildListDetails.clear()
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                Util_shared_preferences.putUserLoggedIn(activity, true)
                                Util_shared_preferences.putFingerCheck(activity, true)
                                Util_shared_preferences.putUserMobileandPassword(
                                    activity,
                                    MobileNumber,
                                    Password
                                )


                                IsParent = responseBody.data[0].is_parent
                                IsPrincipal = responseBody.data[0].is_principal
                                IsStaff = responseBody.data[0].is_staff
                                IsGroupHead = responseBody.data[0].is_grouphead
                                IsAdmin = responseBody.data[0].is_admin
                                MaxGeneralSMSCount = responseBody.data[0].max_general_sms_count
                                MaxHomeWorkSMSCount = responseBody.data[0].max_homework_sms_count
                                MaxEmergencyVoiceDuration =
                                    responseBody.data[0].max_emergency_voice_duration
                                MaxGeneralVoiceDuration =
                                    responseBody.data[0].max_general_voice_duration
                                MaxHomeWorkVoiceDuration = responseBody.data[0].max_hw_voiceduration
                                SchoolListDetails =
                                    responseBody.data[0].staff_details as ArrayList<StaffDetailData>
                                ChildListDetails =
                                    responseBody.data[0].child_details as ArrayList<ChildDetailData>

                                if (responseBody.data[0].is_staff == 1 && responseBody.data[0].is_parent == 1) {
                                    UtilConstants.combinationHomeScreen(activity)

                                } else if (responseBody.data[0].is_principal == 1 && responseBody.data[0].is_parent == 1) {
                                    UtilConstants.combinationHomeScreen(activity)
                                } else if (responseBody.data[0].is_grouphead == 1 && responseBody.data[0].is_parent == 1) {
                                    UtilConstants.combinationHomeScreen(activity)

                                } else if (responseBody.data[0].is_admin == 1 && responseBody.data[0].is_parent == 1) {
                                    UtilConstants.combinationHomeScreen(activity)
                                } else if (responseBody.data[0].is_parent == 1) {
                                    UtilConstants.combinationHomeScreen(activity)

                                } else if (responseBody.data[0].is_staff == 1) {
                                    UtilConstants.schoolHomeScreen(activity)

                                } else if (responseBody.data[0].is_principal == 1) {
                                    UtilConstants.schoolHomeScreen(activity)

                                } else if (responseBody.data[0].is_admin == 1) {
                                    UtilConstants.schoolHomeScreen(activity)

                                } else if (responseBody.data[0].is_grouphead == 1) {
                                    UtilConstants.schoolHomeScreen(activity)

                                }
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

                override fun onFailure(call: Call<LoginResponse?>?, t: Throwable?) {
                    GifLoading.loading(activity, false)
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    fun generateNewLoginToken(activity: Activity?, MobileNumber: String?, Password: String?) {

        val secureID: String =
            Settings.Secure.getString(activity!!.contentResolver, Settings.Secure.ANDROID_ID)
        val jsonObject = JsonObject()
        jsonObject.addProperty(MOBILE_NUMBER, MobileNumber)
        jsonObject.addProperty(ENCRYPT_PASSWORD, Password)
        jsonObject.addProperty(DEVICE_TYPE, "ANDROID")
        jsonObject.addProperty(IMEI_NUMBER, secureID)
        jsonObject.addProperty(SECURE_ID, secureID)
        Log.d("NewloginRequest", jsonObject.toString())
        GifLoading.loading(activity, true)
        var apiInterface: ApiInterface =
            APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.generateNewLoginToken(jsonObject)!!
            .enqueue(object : retrofit2.Callback<GenerateNewLoginTokenResponse?> {
                override fun onResponse(
                    call: Call<GenerateNewLoginTokenResponse?>?,
                    response: Response<GenerateNewLoginTokenResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("NewTokenResponse", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody!!.status == 1) {
                                val Token: String = responseBody.data[0].token
                                Util_shared_preferences.putLoginToken(activity, Token)
                                getLoginDetailsByToken(activity, Token, MobileNumber, Password)
                            } else if (responseBody.status == 2) {
                                LogoutOtherDeviceAlert(
                                    activity,
                                    MobileNumber,
                                    Password,
                                    responseBody.message
                                )
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
                                activity.getString(R.string.Service_unavailable)
                            )
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }
                }

                override fun onFailure(call: Call<GenerateNewLoginTokenResponse?>?, t: Throwable?) {
                    GifLoading.loading(activity, false)
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    fun validateLoginToken(
        activity: Activity?,
        MobileNumber: String?,
        Password: String?,
        screenType: Boolean?
    ) {

        val LoginToken: String? = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, LoginToken)
        jsonObject.addProperty(MOBILE_NUMBER, MobileNumber)
        jsonObject.addProperty(ENCRYPT_PASSWORD, Password)
        Log.d("validateLoginTokenReq", jsonObject.toString())
        GifLoading.loading(activity, true)
        var apiInterface: ApiInterface =
            APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.validateLoginToken(jsonObject)!!
            .enqueue(object : retrofit2.Callback<StatusMessageResponse?> {
                override fun onResponse(
                    call: Call<StatusMessageResponse?>?,
                    response: Response<StatusMessageResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("NewTokenResponse", gson.toJson(response))

                        if (response?.code() == 200) {
                            if (responseBody!!.status == 1) {
                                getLoginDetailsByToken(activity, LoginToken, MobileNumber, Password)
                            }
                        } else if (response?.code() == 400) {
                            val errorResponseBody = Gson().fromJson(
                                response.errorBody()?.charStream(),
                                StatusMessageResponse::class.java
                            )

                            if (errorResponseBody?.status == 2) {
                                if (screenType == true) {
                                    UtilConstants.tokenExpiredAlert(
                                        activity,
                                        errorResponseBody.message
                                    )
                                } else {
                                    generateNewLoginToken(
                                        activity,
                                        MobileNumber,
                                        Password
                                    )
                                }
                            } else if (errorResponseBody?.status == 0) {
                                UtilConstants.customFailureAlert(
                                    activity,
                                    errorResponseBody.message
                                )
                            }
                        } else if (response?.code() == 500) {
                            val errorResponseBody = Gson().fromJson(
                                response.errorBody()?.charStream(),
                                StatusMessageResponse::class.java
                            )
                            UtilConstants.customFailureAlert(activity, errorResponseBody?.message)
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
                    GifLoading.loading(activity, false)
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    fun getGlobalValues(activity: Activity?, type: String?, callback: ReturnGlobalValue) {
        val jsonObject = JsonObject()
        jsonObject.addProperty(KEY_NAME, type)
        Log.d("GlobalValuesRequest", jsonObject.toString())
        GifLoading.loading(activity, true)

        var apiInterface: ApiInterface =
            APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getGlobalValues(jsonObject)!!
            .enqueue(object : retrofit2.Callback<GetGlobalValueResponse?> {
                override fun onResponse(
                    call: Call<GetGlobalValueResponse?>?,
                    response: Response<GetGlobalValueResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("getGlobalValueResponse", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                val value: String = responseBody.data[0].value
                                callback.callBackValue(value)
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

                override fun onFailure(call: Call<GetGlobalValueResponse?>?, t: Throwable?) {
                    Log.d("Failure", t.toString())
                    GifLoading.loading(activity, false)
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    fun getAppLanguages(activity: Activity?) {

        var apiInterface: ApiInterface =
            APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getAppLanguages()!!
            .enqueue(object : retrofit2.Callback<LanguageResponse?> {
                override fun onResponse(
                    call: Call<LanguageResponse?>?,
                    response: Response<LanguageResponse?>?
                ) {
                    try {
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("LanguageListResponse", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
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

                override fun onFailure(call: Call<LanguageResponse?>?, t: Throwable?) {
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    fun updateDeviceToken(activity: Activity?, devicetoken: String?) {
        val MobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val jsonObject = JsonObject().also {
            it.addProperty(MOBILE_NUMBER, MobileNumber)
            it.addProperty(DEVICE_TOKEN, "")
        }
        Log.d("DeviceTokenRequest", jsonObject.toString())

        var apiInterface: ApiInterface =
            APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.updateDeviceToken(jsonObject)!!
            .enqueue(object : retrofit2.Callback<StatusMessageResponse?> {
                override fun onResponse(
                    call: Call<StatusMessageResponse?>?,
                    response: Response<StatusMessageResponse?>?
                ) {
                    try {
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("updateDeviceToken", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
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


    fun sendNonEmergencyVoiceToEntireSchool(activity: Activity?) {
        val file = File(UtilConstants.VoiceFilePath!!)
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val bodyFile = MultipartBody.Part.createFormData("voice", file.name, requestFile)
        val jsonVoiceRequest = jsonVoiceEntireSchool(activity)
        Log.d("NonEmergencyVoice", jsonVoiceRequest.toString())

        val requestBody = RequestBody.create(MultipartBody.FORM, jsonVoiceRequest.toString())
        GifLoading.loading(activity, true)

        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.sendNonEmergencyVoiceToEntireSchool(requestBody, bodyFile)!!
            .enqueue(object : retrofit2.Callback<StatusMessageResponse?> {
                override fun onResponse(
                    call: Call<StatusMessageResponse?>?,
                    response: Response<StatusMessageResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)

                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("NonEmergencyVoiceRes:", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                UtilConstants.EntireSchoolPopupWindow!!.dismiss()
                                UtilConstants.customSuccessAlert(activity, responseBody.message)
                            } else {
                                UtilConstants.customFailureAlert(activity, responseBody?.message)
                            }
                        } else if (response?.code() == 400 || response?.code() == 500) {
                            val errorResponseBody = Gson().fromJson(
                                response.errorBody()?.charStream(),
                                StatusMessageResponse::class.java
                            )
                            UtilConstants.handleErrorResponse(
                                activity!!,
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
                    GifLoading.loading(activity, false)
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    private fun jsonVoiceEntireSchool(activity: Activity?): JsonObject {
        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, token)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, UtilConstants.SchoolID)
        jsonObject.addProperty(STAFF_ID, UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        if (ScheduleType.equals("instant")) {
            jsonObjectRequest.addProperty(VOICE_TYPE, ScheduleType)
            jsonObjectRequest.addProperty(SCHEDULE_DATE, "")
            jsonObjectRequest.addProperty(SCHEDULE_HOUR, "")
            jsonObjectRequest.addProperty(SCHEDULE_MINUTE, "")
            jsonObjectRequest.addProperty(END_HOUR, "")
            jsonObjectRequest.addProperty(END_MINUTE, "")
            jsonObjectRequest.addProperty(DURATION, UtilConstants.VoiceDuration)
            jsonObjectRequest.addProperty(DESCRIPTION, UtilConstants.Title)
            jsonObject.add(REQUEST, jsonObjectRequest)
        } else if (ScheduleType.equals("schedule")) {

            jsonObjectRequest.addProperty(VOICE_TYPE, ScheduleType)
            jsonObjectRequest.addProperty(SCHEDULE_DATE, UtilConstants.Date)
            jsonObjectRequest.addProperty(SCHEDULE_HOUR, UtilConstants.Hour)
            jsonObjectRequest.addProperty(SCHEDULE_MINUTE, UtilConstants.Minute)
            jsonObjectRequest.addProperty(END_HOUR, UtilConstants.EndHour)
            jsonObjectRequest.addProperty(END_MINUTE, UtilConstants.EndMinute)
            jsonObjectRequest.addProperty(DURATION, UtilConstants.VoiceDuration)
            jsonObjectRequest.addProperty(DESCRIPTION, UtilConstants.Title)
            jsonObject.add(REQUEST, jsonObjectRequest)

        }

        return jsonObject
    }

    fun sendNonEmergencyVoiceStd_Sec_Grp_Stud_Satff(activity: Activity?) {
        val file = File(UtilConstants.VoiceFilePath!!)
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val bodyFile = MultipartBody.Part.createFormData("voice", file.name, requestFile)
        val jsonNonEmergencyVoice = jsonNonEmergencyVoice(activity)
        Log.d("VoiceStdSecGrpStudSatf", jsonNonEmergencyVoice.toString())
        val requestBody = RequestBody.create(MultipartBody.FORM, jsonNonEmergencyVoice.toString())
        GifLoading.loading(activity, true)

        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)

        if (UtilConstants.RecipientsType == UtilConstants.Standard) {
            call = apiInterface.sendNonEmergencyVoiceToStandards(requestBody, bodyFile)!!
        } else if (UtilConstants.RecipientsType == UtilConstants.StandardSection) {
            call = apiInterface.sendNonEmergencyVoiceToSections(requestBody, bodyFile)!!
        } else if (UtilConstants.RecipientsType == UtilConstants.Students) {
            call = apiInterface.sendNonEmergencyVoiceToStudents(requestBody, bodyFile)!!
        } else if (UtilConstants.RecipientsType == UtilConstants.Group) {
            call = apiInterface.sendNonEmergencyVoiceToGroups(requestBody, bodyFile)!!
        } else if (UtilConstants.RecipientsType == UtilConstants.Staff) {
            call = apiInterface.sendNonEmergencyVoiceToStaffs(requestBody, bodyFile)!!
        }
        call.enqueue(object : retrofit2.Callback<StatusMessageResponse?> {
            override fun onResponse(
                call: Call<StatusMessageResponse?>?,
                response: Response<StatusMessageResponse?>?
            ) {
                try {
                    GifLoading.loading(activity, false)

                    val responseBody = response?.body()
                    val gson = Gson()
                    Log.d("NonEmergencyVoice", gson.toJson(response))
                    if (response?.code() == 200) {
                        if (responseBody?.status == 1) {
                            UtilConstants.customSuccessAlert(activity, responseBody.message)
                        } else {
                            UtilConstants.customFailureAlert(activity, responseBody?.message)
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

                GifLoading.loading(activity, false)

                UtilConstants.normalToast(activity, t.toString())
            }
        })
    }

    private fun jsonNonEmergencyVoice(activity: Activity?): JsonObject {
        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)
        Log.d("NonEmergencyVoiceReq", mobileNumber.toString())
        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, token)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, UtilConstants.SchoolID)
        jsonObject.addProperty(STAFF_ID, UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        if (ScheduleType.equals("instant")) {

            jsonObjectRequest.addProperty(VOICE_TYPE, ScheduleType)
            jsonObjectRequest.addProperty(SCHEDULE_DATE, "")
            jsonObjectRequest.addProperty(SCHEDULE_HOUR, "")
            jsonObjectRequest.addProperty(SCHEDULE_MINUTE, "")
            jsonObjectRequest.addProperty(END_HOUR, "")
            jsonObjectRequest.addProperty(END_MINUTE, "")
            jsonObjectRequest.addProperty(DURATION, UtilConstants.VoiceDuration)
            jsonObjectRequest.addProperty(DESCRIPTION, UtilConstants.Title)

        } else if (ScheduleType.equals("schedule")) {

            jsonObjectRequest.addProperty(VOICE_TYPE, ScheduleType)
            jsonObjectRequest.addProperty(SCHEDULE_DATE, UtilConstants.Date)
            jsonObjectRequest.addProperty(SCHEDULE_HOUR, UtilConstants.Hour)
            jsonObjectRequest.addProperty(SCHEDULE_MINUTE, UtilConstants.Minute)
            jsonObjectRequest.addProperty(END_HOUR, UtilConstants.EndHour)
            jsonObjectRequest.addProperty(END_MINUTE, UtilConstants.EndMinute)
            jsonObjectRequest.addProperty(DURATION, UtilConstants.VoiceDuration)
            jsonObjectRequest.addProperty(DESCRIPTION, UtilConstants.Title)

        }

        if (UtilConstants.RecipientsType == UtilConstants.StandardSection) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalSectionList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty(ID, it.sectionID)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add(SECTION_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)
        } else if (UtilConstants.RecipientsType == UtilConstants.Students) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalStudentList.forEach {
                val jsonsIds = JsonObject()
                val sectionID = it.sectionID
                it.studentData.forEach {
                    jsonsIds.addProperty(ID, it.student_id)
                    jsonsIds.addProperty(SECTION_ID, sectionID)
                    jsonSchoolArray.add(jsonsIds)
                }
            }
            jsonObjectRequest.add(STUDENT_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)
        } else if (UtilConstants.RecipientsType == UtilConstants.Standard) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalStandardList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty(ID, it.standard_id)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add(STANDARD_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)

        } else if (UtilConstants.RecipientsType == UtilConstants.Group) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalGroupsList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty(ID, it.group_id)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add(GROUP_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)
        } else if (UtilConstants.RecipientsType == UtilConstants.Staff) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalStaffsList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty(ID, it.staff_id)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add(STAFF_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)

        }
        return jsonObject
    }


    fun sendEmergencyVoiceToSchools(activity: Activity?) {
        val file = File(UtilConstants.VoiceFilePath!!)
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val bodyFile = MultipartBody.Part.createFormData("voice", file.name, requestFile)
        val jsonEmergencyVoiceSchool = jsonEmergencyVoiceSchool(activity)
        val requestBody =
            RequestBody.create(MultipartBody.FORM, jsonEmergencyVoiceSchool.toString())
        Log.d("EmergencyVoiceReq", jsonEmergencyVoiceSchool.toString())
        GifLoading.loading(activity, true)

        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.sendEmergencyVoiceToSchools(requestBody, bodyFile)!!
            .enqueue(object : retrofit2.Callback<StatusMessageResponse?> {
                override fun onResponse(
                    call: Call<StatusMessageResponse?>?,
                    response: Response<StatusMessageResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("EmergencyVoice", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                UtilConstants.customSuccessAlert(activity, responseBody.message)
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

                    GifLoading.loading(activity, false)

                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    private fun jsonEmergencyVoiceSchool(activity: Activity?): JsonObject {
        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, token)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, UtilConstants.SchoolID)
        jsonObject.addProperty(STAFF_ID, UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty(DESCRIPTION, UtilConstants.Title)
        jsonObjectRequest.addProperty(DURATION, UtilConstants.VoiceDuration)

        val jsonSchoolArray = JsonArray()
        UtilConstants.SelectedFinalSchoolsList.forEach {
            val jsonsIds = JsonObject()
            jsonsIds.addProperty(ID, it.school_id)
            jsonSchoolArray.add(jsonsIds)
        }
        jsonObjectRequest.add(ID_SCHOOL, jsonSchoolArray)
        jsonObject.add(REQUEST, jsonObjectRequest)

        return jsonObject

    }

    fun sendTextToEntireSchool(activity: Activity?) {
        val jsonTextRequest = jsonTextEntireSchoolRequest(activity)
        Log.d("TextEntireSchoolRequest", jsonTextRequest.toString())
        GifLoading.loading(activity, true)

        val apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.sendTextToEntireSchool(jsonTextRequest)!!
            .enqueue(object : retrofit2.Callback<StatusMessageResponse?> {
                override fun onResponse(
                    call: Call<StatusMessageResponse?>?,
                    response: Response<StatusMessageResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)

                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("TextToEntireSchool-Res:", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                UtilConstants.EntireSchoolPopupWindow!!.dismiss()
                                UtilConstants.customSuccessAlert(activity, responseBody.message)
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

    private fun jsonTextEntireSchoolRequest(activity: Activity?): JsonObject {
        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, token)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, UtilConstants.SchoolID)
        jsonObject.addProperty(STAFF_ID, UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty("content", UtilConstants.Title)
        jsonObjectRequest.addProperty("description", UtilConstants.Description)
        jsonObject.add(REQUEST, jsonObjectRequest)
        return jsonObject

    }

    fun sendTextToStandardGroupStaffSectionStudents(activity: Activity?) {
        GifLoading.loading(activity, true)

        val apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        val jsonTextRequest = jsonTextStandardGroupStaffSectionRequest(activity)
        Log.d("TextToGroupsRequest", jsonTextRequest.toString())
        if (UtilConstants.RecipientsType == UtilConstants.Standard) {
            call = apiInterface.sendTextToStandards(jsonTextRequest)!!
        } else if (UtilConstants.RecipientsType == UtilConstants.StandardSection) {
            call = apiInterface.sendTextToSections(jsonTextRequest)!!
        } else if (UtilConstants.RecipientsType == UtilConstants.Students) {
            call = apiInterface.sendTextToStudents(jsonTextRequest)!!
        } else if (UtilConstants.RecipientsType == UtilConstants.Group) {
            call = apiInterface.sendTextToGroups(jsonTextRequest)!!
        } else if (UtilConstants.RecipientsType == UtilConstants.Staff) {
            call = apiInterface.sendTextToStaff(jsonTextRequest)!!
        }
        call.enqueue(object : Callback<StatusMessageResponse?> {
            override fun onResponse(
                call: Call<StatusMessageResponse?>,
                response: Response<StatusMessageResponse?>
            ) {
                try {
                    GifLoading.loading(activity, false)

                    val responseBody = response?.body()
                    val gson = Gson()
                    Log.d("TextToGroups-Res:", gson.toJson(response))
                    if (response?.code() == 200) {
                        if (responseBody?.status == 1) {
                            UtilConstants.customSuccessAlert(activity, responseBody.message)
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
                    Log.d("TextException", e.toString())
                }
            }

            override fun onFailure(call: Call<StatusMessageResponse?>, t: Throwable) {
                Log.d("TextFailure", t.toString())
                GifLoading.loading(activity, false)

                UtilConstants.normalToast(activity, t.toString())
            }
        })

    }


    private fun jsonTextStandardGroupStaffSectionRequest(activity: Activity?): JsonObject {
        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, token)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, UtilConstants.SchoolID)
        jsonObject.addProperty(STAFF_ID, UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty("content", UtilConstants.Title)
        jsonObjectRequest.addProperty("description", UtilConstants.Description)

        if (UtilConstants.RecipientsType == UtilConstants.Students) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalStudentList.forEach {
                val jsonsIds = JsonObject()
                val sectionID = it.sectionID
                it.studentData.forEach {
                    Log.d("SectionID", sectionID!!)
                    jsonsIds.addProperty(ID, it.student_id)
                    jsonsIds.addProperty(SECTION_ID, sectionID)
                    jsonSchoolArray.add(jsonsIds)

                }

            }


            jsonObjectRequest.add(STUDENT_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)
        } else if (UtilConstants.RecipientsType == UtilConstants.Standard) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalStandardList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty(ID, it.standard_id)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add(STANDARD_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)

            Log.d("StandardRequst", jsonObjectRequest.toString())
        } else if (UtilConstants.RecipientsType == UtilConstants.StandardSection) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalSectionList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty(ID, it.sectionID)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add(SECTION_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)
        } else if (UtilConstants.RecipientsType == UtilConstants.Group) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalGroupsList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty(ID, it.group_id)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add(GROUP_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)
            Log.d("Group", jsonSchoolArray.toString())
        } else if (UtilConstants.RecipientsType == UtilConstants.Staff) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalStaffsList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty(ID, it.staff_id)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add(STAFF_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)
            Log.d("Staff", jsonSchoolArray.toString())

        }


        return jsonObject
    }

    fun sendEventsToEntireSchool(activity: Activity?) {
        val jsonEventsEntireschoolRequest = jsonEventsEntireschool(activity)
        Log.d("EventsEntireschoolReq", jsonEventsEntireschoolRequest.toString())
        GifLoading.loading(activity, true)
        val apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.sendEventToEntireSchool(jsonEventsEntireschoolRequest)!!
            .enqueue(object : retrofit2.Callback<StatusMessageResponse?> {
                override fun onResponse(
                    call: Call<StatusMessageResponse?>?,
                    response: Response<StatusMessageResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("EventsEntireSchoolRes:", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                UtilConstants.customSuccessAlert(activity, responseBody.message)
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

    private fun jsonEventsEntireschool(activity: Activity?): JsonObject {
        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, token)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, UtilConstants.SchoolID)
        jsonObject.addProperty(STAFF_ID, UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty("topic", UtilConstants.Title)
        jsonObjectRequest.addProperty("description", UtilConstants.Description)
        jsonObjectRequest.addProperty("event_date", UtilConstants.Date)
        jsonObjectRequest.addProperty("event_time", UtilConstants.EventTime)

        val jsonarray = JsonArray()
        UtilConstants.AWSUploadedFilesList.forEach {
            val jsonsIds = JsonObject()
            jsonsIds.addProperty("file_path", it.filepath)
            jsonarray.add(jsonsIds)
        }
        jsonObjectRequest.add("event_photos", jsonarray)
        jsonObject.add(REQUEST, jsonObjectRequest)
        return jsonObject

    }

    fun sendEventsToStdGrpStaffStudSection(activity: Activity?) {
        val jsonEventsStandardRequest = jsonEventRequest(activity)
        Log.d("EventsRequest", jsonEventsStandardRequest.toString())
        GifLoading.loading(activity, true)

        val apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        if (UtilConstants.RecipientsType == UtilConstants.Students) {
            call = apiInterface.sendEventToStudents(jsonEventsStandardRequest)!!
        } else if (UtilConstants.RecipientsType == UtilConstants.Standard) {
            call = apiInterface.sendEventToStandard(jsonEventsStandardRequest)!!
        } else if (UtilConstants.RecipientsType == UtilConstants.StandardSection) {
            call = apiInterface.sendEventToSections(jsonEventsStandardRequest)!!
        } else if (UtilConstants.RecipientsType == UtilConstants.Group) {
            call = apiInterface.sendEventToGroups(jsonEventsStandardRequest)!!
        } else if (UtilConstants.RecipientsType == UtilConstants.Staff) {
            call = apiInterface.sendEventToStaff(jsonEventsStandardRequest)!!
        }
        call.enqueue(object : retrofit2.Callback<StatusMessageResponse?> {
            override fun onResponse(
                call: Call<StatusMessageResponse?>?,
                response: Response<StatusMessageResponse?>?
            ) {
                try {
                    GifLoading.loading(activity, false)

                    val responseBody = response?.body()
                    val gson = Gson()
                    Log.d("EventResponse-Res:", gson.toJson(response))
                    if (response?.code() == 200) {
                        if (responseBody?.status == 1) {
                            UtilConstants.customSuccessAlert(activity, responseBody.message)
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

    private fun jsonEventRequest(activity: Activity?): JsonObject {
        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, token)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, UtilConstants.SchoolID)
        jsonObject.addProperty(STAFF_ID, UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty("topic", UtilConstants.Title)
        jsonObjectRequest.addProperty("description", UtilConstants.Description)
        jsonObjectRequest.addProperty("event_date", UtilConstants.Date)
        jsonObjectRequest.addProperty("event_time", UtilConstants.EventTime)
        val jsonarray = JsonArray()
        UtilConstants.AWSUploadedFilesList.forEach {
            val jsonsIds = JsonObject()
            jsonsIds.addProperty("file_path", it.filepath)
            jsonarray.add(jsonsIds)
        }
        jsonObjectRequest.add("event_photos", jsonarray)

        if (UtilConstants.RecipientsType == UtilConstants.Students) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalStudentList.forEach {
                val jsonsIds = JsonObject()
                val sectionID = it.sectionID
                it.studentData.forEach {
                    Log.d("SectionID", sectionID!!)
                    jsonsIds.addProperty(ID, it.student_id)
                    jsonsIds.addProperty(SECTION_ID, sectionID)
                    jsonSchoolArray.add(jsonsIds)
                }
            }
            jsonObjectRequest.add(STUDENT_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)
        } else if (UtilConstants.RecipientsType == UtilConstants.Standard) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalStandardList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty(ID, it.standard_id)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add(STANDARD_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)

        } else if (UtilConstants.RecipientsType == UtilConstants.StandardSection) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalSectionList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty(ID, it.sectionID)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add(SECTION_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)
        } else if (UtilConstants.RecipientsType == UtilConstants.Group) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalGroupsList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty(ID, it.group_id)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add(GROUP_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)
        } else if (UtilConstants.RecipientsType == UtilConstants.Staff) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalStaffsList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty(ID, it.staff_id)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add(STAFF_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)
        }
        return jsonObject
    }

    fun sendFilesToEntireSchool(activity: Activity?) {
        val jsonFilesEntireschoolRequest = jsonFilesEntireSchool(activity)
        Log.d("ImagesToEntireschoolReq", jsonFilesEntireschoolRequest.toString())
        GifLoading.loading(activity, true)
        val apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.sendFilesToEntireSchool(jsonFilesEntireschoolRequest)!!
            .enqueue(object : retrofit2.Callback<StatusMessageResponse?> {
                override fun onResponse(
                    call: Call<StatusMessageResponse?>?,
                    response: Response<StatusMessageResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("ImagesEntireSchool-Res:", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                UtilConstants.customSuccessAlert(activity, responseBody.message)
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

    private fun jsonFilesEntireSchool(activity: Activity?): JsonObject {
        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, token)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, UtilConstants.SchoolID)
        jsonObject.addProperty(STAFF_ID, UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty("description", UtilConstants.Title)
        jsonObjectRequest.addProperty("event_date", UtilConstants.Date)
        jsonObjectRequest.addProperty("event_time", UtilConstants.EventTime)

        val jsonarray = JsonArray()
        UtilConstants.AWSUploadedFilesList.forEach {
            val jsonsIds = JsonObject()
            jsonsIds.addProperty("file_path", it.filepath)
            jsonarray.add(jsonsIds)
        }
        jsonObjectRequest.add("event_photos", jsonarray)
        jsonObject.add(REQUEST, jsonObjectRequest)
        return jsonObject

    }


    fun sendFilesTostudentGrpStaffSec(activity: Activity?) {
        val jsonFilesRequest = jsonFilesRequest(activity)
        Log.d("SendFilesRequest", jsonFilesRequest.toString())
        GifLoading.loading(activity, true)
        val apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        if (UtilConstants.RecipientsType == UtilConstants.Students) {
            call = apiInterface.sendFilesToStudents(jsonFilesRequest)!!
        } else if (UtilConstants.RecipientsType == UtilConstants.Standard) {
            call = apiInterface.sendFilesToStandard(jsonFilesRequest)!!
        } else if (UtilConstants.RecipientsType == UtilConstants.StandardSection) {
            call = apiInterface.sendFilesToSection(jsonFilesRequest)!!
        } else if (UtilConstants.RecipientsType == UtilConstants.Group) {
            call = apiInterface.sendFilesToGroups(jsonFilesRequest)!!
        } else if (UtilConstants.RecipientsType == UtilConstants.Staff) {
            call = apiInterface.sendFilesToStaff(jsonFilesRequest)!!
        }
        call.enqueue(object : retrofit2.Callback<StatusMessageResponse?> {
            override fun onResponse(
                call: Call<StatusMessageResponse?>?,
                response: Response<StatusMessageResponse?>?
            ) {
                try {
                    GifLoading.loading(activity, false)
                    val responseBody = response?.body()
                    val gson = Gson()
                    Log.d("FleSecStdGrpStafStudRes", gson.toJson(response))
                    if (response?.code() == 200) {
                        if (responseBody?.status == 1) {
                            UtilConstants.customSuccessAlert(activity, responseBody.message)
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

    private fun jsonFilesRequest(activity: Activity?): JsonObject {
        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, token)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, UtilConstants.SchoolID)
        jsonObject.addProperty(STAFF_ID, UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty("description", UtilConstants.Title)
        jsonObjectRequest.addProperty("file_type", UtilConstants.Apifiletype)
        val jsonarray = JsonArray()
        AWSUploadedFilesList.forEach {
            val jsonsIds = JsonObject()
            jsonsIds.addProperty("file_path", it.filepath)
            jsonsIds.addProperty("original_file_name", it.fileName)
            jsonarray.add(jsonsIds)

        }
        jsonObjectRequest.add("file_array", jsonarray)

        if (UtilConstants.RecipientsType == UtilConstants.Standard) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalStandardList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty(ID, it.standard_id)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add(STANDARD_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)

        } else if (UtilConstants.RecipientsType == UtilConstants.Students) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalStudentList.forEach {
                val jsonsIds = JsonObject()
                val sectionID = it.sectionID

                it.studentData.forEach {
                    Log.d("SectionID", sectionID!!)
                    jsonsIds.addProperty(ID, it.student_id)
                    jsonsIds.addProperty(SECTION_ID, sectionID)
                    jsonSchoolArray.add(jsonsIds)
                }
            }
            jsonObjectRequest.add(STUDENT_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)
        } else if (UtilConstants.RecipientsType == UtilConstants.StandardSection) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalSectionList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty(ID, it.sectionID)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add(SECTION_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)
        } else if (UtilConstants.RecipientsType == UtilConstants.Group) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalGroupsList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty(ID, it.group_id)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add(GROUP_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)
        } else if (UtilConstants.RecipientsType == UtilConstants.Staff) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalStaffsList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty(ID, it.staff_id)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add(STAFF_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)
        }
        return jsonObject
    }

    fun sendNoticeboardToEntireSchool(activity: Activity?) {
        val jsonNoticeEntireschoolRequest = jsonNoticeboardEntireSchool(activity)
        Log.d("NoticeToEntireschoolReq", jsonNoticeEntireschoolRequest.toString())
        GifLoading.loading(activity, true)
        val apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.sendNoticeToEntireSchool(jsonNoticeEntireschoolRequest)!!
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
                                UtilConstants.customSuccessAlert(activity, responseBody.message)
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

    private fun jsonNoticeboardEntireSchool(activity: Activity?): JsonObject {
        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, token)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, UtilConstants.SchoolID)
        jsonObject.addProperty(STAFF_ID, UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty("topic", UtilConstants.Title)
        jsonObjectRequest.addProperty("description", UtilConstants.Title)
        val jsonarray = JsonArray()
        UtilConstants.AWSUploadedFilesList.forEach {
            val jsonsIds = JsonObject()
            jsonsIds.addProperty("file_path", it.filepath)
            jsonarray.add(jsonsIds)
        }
        jsonObjectRequest.add("notice_files", jsonarray)
        jsonObject.add(REQUEST, jsonObjectRequest)
        return jsonObject

    }


    fun sendNoticeboardTostudentGrpStaffSec(activity: Activity?) {
        val jsonNoticeboardRequest = jsonNoticeRequest(activity)
        Log.d("SendNoticeboardRequest", jsonNoticeboardRequest.toString())
        GifLoading.loading(activity, true)
        val apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        if (UtilConstants.RecipientsType == UtilConstants.Students) {
            call = apiInterface.sendNoticeToStudents(jsonNoticeboardRequest)!!
        } else if (UtilConstants.RecipientsType == UtilConstants.Standard) {
            call = apiInterface.sendNoticeToStandard(jsonNoticeboardRequest)!!
        } else if (UtilConstants.RecipientsType == UtilConstants.StandardSection) {
            call = apiInterface.sendNoticeToSection(jsonNoticeboardRequest)!!
        } else if (UtilConstants.RecipientsType == UtilConstants.Group) {
            call = apiInterface.sendNoticeToGroups(jsonNoticeboardRequest)!!
        } else if (UtilConstants.RecipientsType == UtilConstants.Staff) {
            call = apiInterface.sendNoticeToStaff(jsonNoticeboardRequest)!!
        }
        call.enqueue(object : retrofit2.Callback<StatusMessageResponse?> {
            override fun onResponse(
                call: Call<StatusMessageResponse?>?,
                response: Response<StatusMessageResponse?>?
            ) {
                try {
                    GifLoading.loading(activity, false)
                    val responseBody = response?.body()
                    val gson = Gson()
                    Log.d("NoticeCommon Res:", gson.toJson(response))
                    if (response?.code() == 200) {
                        if (responseBody?.status == 1) {
                            UtilConstants.customSuccessAlert(activity, responseBody.message)
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

    private fun jsonNoticeRequest(activity: Activity?): JsonObject {
        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, token)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, UtilConstants.SchoolID)
        jsonObject.addProperty(STAFF_ID, UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty("topic", UtilConstants.Title)
        jsonObjectRequest.addProperty("description", UtilConstants.Description)
        val jsonarray = JsonArray()

        UtilConstants.AWSUploadedFilesList.forEach {
            val jsonsIds = JsonObject()
            jsonsIds.addProperty("file_path", it.filepath)
            jsonarray.add(jsonsIds)
        }
        jsonObjectRequest.add("notice_files", jsonarray)

        if (UtilConstants.RecipientsType == UtilConstants.Standard) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalStandardList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty(ID, it.standard_id)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add(STANDARD_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)

        } else if (UtilConstants.RecipientsType == UtilConstants.Students) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalStudentList.forEach {
                val jsonsIds = JsonObject()
                val sectionID = it.sectionID

                it.studentData.forEach {
                    Log.d("SectionID", sectionID!!)
                    jsonsIds.addProperty(ID, it.student_id)
                    jsonsIds.addProperty(SECTION_ID, sectionID)
                    jsonSchoolArray.add(jsonsIds)
                }
            }
            jsonObjectRequest.add(STUDENT_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)
        } else if (UtilConstants.RecipientsType == UtilConstants.StandardSection) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalSectionList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty(ID, it.sectionID)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add(SECTION_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)
        } else if (UtilConstants.RecipientsType == UtilConstants.Group) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalGroupsList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty(ID, it.group_id)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add(GROUP_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)
        } else if (UtilConstants.RecipientsType == UtilConstants.Staff) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalStaffsList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty(ID, it.staff_id)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add(STAFF_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)
        }
        return jsonObject
    }


    fun sendHomeWork(activity: Activity?) {
        val jsonHomeWork = jsonHomeworkRequest(activity)
        Log.d("SendHomeworkRequest", jsonHomeWork.toString())
        GifLoading.loading(activity, true)
        val apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.sendHomework(jsonHomeWork)!!
            .enqueue(object : retrofit2.Callback<StatusMessageResponse?> {
                override fun onResponse(
                    call: Call<StatusMessageResponse?>?,
                    response: Response<StatusMessageResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("SendHomeworkResponse", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                UtilConstants.customSuccessAlert(activity, responseBody.message)
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

    fun jsonHomeworkRequest(activity: Activity?): JsonObject {

        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, token)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, UtilConstants.SchoolID)
        jsonObject.addProperty(STAFF_ID, UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty("sub_code", selectedSubjectID)
        jsonObjectRequest.addProperty("homework_text", UtilConstants.Title)
        jsonObjectRequest.addProperty("homework_topic", UtilConstants.Description)

        val jsonarray = JsonArray()
        UtilConstants.selectedSectionsListforSubjecject.forEach {
            val jsonsIds = JsonObject()
            jsonsIds.addProperty(ID, it.section_id)
            jsonarray.add(jsonsIds)
        }
        jsonObjectRequest.add(SECTION_DATA, jsonarray)
        if (MENU_TYPE == MENU_TEXT_HOMEWORK) {
            jsonObjectRequest.addProperty("homework_voice", "")
        } else if (MENU_TYPE == MENU_VOICE_HOMEWORK) {

            AWSUploadedFilesList.forEach {
                jsonObjectRequest.addProperty("homework_voice", it.filepath)
            }
        }
        jsonObject.add(REQUEST, jsonObjectRequest)
        return jsonObject

    }

    fun sendAssignment(activity: Activity?) {
        val jsonAssignment = jsonAssignmentRequest(activity)
        Log.d("SendAssignmentRequest", jsonAssignment.toString())
        GifLoading.loading(activity, true)
        val apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.sendAssignmentToSections(jsonAssignment)!!
            .enqueue(object : retrofit2.Callback<StatusMessageResponse?> {
                override fun onResponse(
                    call: Call<StatusMessageResponse?>?,
                    response: Response<StatusMessageResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("AssignmentResponse", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                UtilConstants.customSuccessAlert(activity, responseBody.message)
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

    fun jsonAssignmentRequest(activity: Activity?): JsonObject {

        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, token)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, UtilConstants.SchoolID)
        jsonObject.addProperty(STAFF_ID, UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty("title", UtilConstants.Title)
        jsonObjectRequest.addProperty("end_date", UtilConstants.Date)
        jsonObjectRequest.addProperty("assignment_text", UtilConstants.Description)
        jsonObjectRequest.addProperty("subject_id", selectedSubjectID)


        val jsonimgarray = JsonArray()
        val jsonpdfarray = JsonArray()
        val jsonsimgIds = JsonObject()
        val jsonspdfIds = JsonObject()

        AWSUploadedFilesList.forEach {
            val type = it.contentype
            Log.d("Testtype", it.contentype!!)
            if (it.contentype.equals(contentTypeImg)) {
                jsonsimgIds.addProperty("file_path", it.filepath)
                jsonsimgIds.addProperty("file_name", it.fileName)
                jsonimgarray.add(jsonsimgIds)
            } else if (it.contentype.equals(contentTypePdf)) {
                jsonspdfIds.addProperty("file_path", it.filepath)
                jsonspdfIds.addProperty("file_name", it.fileName)
                jsonpdfarray.add(jsonspdfIds)
            }

        }
        jsonObjectRequest.add("image_array", jsonimgarray)
        jsonObjectRequest.add("pdf_array", jsonpdfarray)


        if (RecipientsType == UtilConstants.Students) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalStudentList.forEach {
                val jsonsIds = JsonObject()
                val sectionID = it.sectionID

                it.studentData.forEach {
                    Log.d("SectionID", sectionID!!)
                    jsonsIds.addProperty(ID, it.student_id)
                    jsonsIds.addProperty("student_array", sectionID)
                    jsonSchoolArray.add(jsonsIds)
                }
            }
            jsonObjectRequest.add(STUDENT_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)
        } else if (RecipientsType == UtilConstants.StandardSection) {
            Log.d("standardsection", RecipientsType.toString())
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalSectionList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty(ID, it.sectionID)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add("section_array", jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)
        }
        return jsonObject
    }

    fun sendNonEmergencyVoiceHistoryToEntireSchool(activity: Activity?) {
        val jsonNonVoiceHistoryRequest = jsonNonVoiceHistoryEntireRequest(activity)
        Log.d("SendAssignmentRequest", jsonNonVoiceHistoryRequest.toString())
        GifLoading.loading(activity, true)
        val apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.sendNonEmergencyVoiceToEntireSchoolFromHistory(jsonNonVoiceHistoryRequest)!!
            .enqueue(object : retrofit2.Callback<StatusMessageResponse?> {
                override fun onResponse(
                    call: Call<StatusMessageResponse?>?,
                    response: Response<StatusMessageResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("AssignmentResponse", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                UtilConstants.customSuccessAlert(activity, responseBody.message)
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

    private fun jsonNonVoiceHistoryEntireRequest(activity: Activity?): JsonObject {
        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, token)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, UtilConstants.SchoolID)
        jsonObject.addProperty(STAFF_ID, UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        if (ScheduleType.equals("instant")) {
            jsonObjectRequest.addProperty(VOICE_TYPE, ScheduleType)
            jsonObjectRequest.addProperty(VOICE_FILEPATH, VoiceFilePath)
            jsonObjectRequest.addProperty(SCHEDULE_DATE, "")
            jsonObjectRequest.addProperty(SCHEDULE_HOUR, "")
            jsonObjectRequest.addProperty(SCHEDULE_MINUTE, "")
            jsonObjectRequest.addProperty(END_HOUR, "")
            jsonObjectRequest.addProperty(END_MINUTE, "")
            jsonObjectRequest.addProperty(DURATION, UtilConstants.VoiceDuration)
            jsonObjectRequest.addProperty(DESCRIPTION, UtilConstants.Title)
        } else if (ScheduleType.equals("schedule")) {
            jsonObjectRequest.addProperty(VOICE_TYPE, ScheduleType)
            jsonObjectRequest.addProperty(VOICE_FILEPATH, VoiceFilePath)
            jsonObjectRequest.addProperty(SCHEDULE_DATE, UtilConstants.Date)
            jsonObjectRequest.addProperty(SCHEDULE_HOUR, UtilConstants.Hour)
            jsonObjectRequest.addProperty(SCHEDULE_MINUTE, UtilConstants.Minute)
            jsonObjectRequest.addProperty(END_HOUR, UtilConstants.EndHour)
            jsonObjectRequest.addProperty(END_MINUTE, UtilConstants.EndMinute)
            jsonObjectRequest.addProperty(DURATION, UtilConstants.VoiceDuration)
            jsonObjectRequest.addProperty(DESCRIPTION, UtilConstants.Title)

        }
        return jsonObject

    }


    fun sendNonVoiceFromHistoryToStdSecGrpStaffStud(activity: Activity?) {
        val jsonNonVoiceHistoryToStdSecGrpStaffStud =
            jsonNonVoiceHistoryToStdSecGrpStaffStud(activity)
        Log.d("SendAssignmentRequest", jsonNonVoiceHistoryToStdSecGrpStaffStud.toString())
        GifLoading.loading(activity, true)
        val apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)

        if (RecipientsType == UtilConstants.Standard) {
            call = apiInterface.SendNonEmergencyVoicetoStandardFromhistory(
                jsonNonVoiceHistoryToStdSecGrpStaffStud
            )
        } else if (RecipientsType == UtilConstants.StandardSection) {
            call = apiInterface.SendNonEmergencyVoicetoSectionsFromhistory(
                jsonNonVoiceHistoryToStdSecGrpStaffStud
            )!!
        } else if (RecipientsType == UtilConstants.Students) {
            call = apiInterface.SendNonEmergencyVoiceToStudentsFromhistory(
                jsonNonVoiceHistoryToStdSecGrpStaffStud
            )!!
        } else if (RecipientsType == UtilConstants.Group) {
            call = apiInterface.SendNonEmergencyVoiceToGroupFromhistory(
                jsonNonVoiceHistoryToStdSecGrpStaffStud
            )!!
        } else if (RecipientsType == UtilConstants.Staff) {
            call = apiInterface.SendNonEmergencyVoiceToStaffFromhistory(
                jsonNonVoiceHistoryToStdSecGrpStaffStud
            )!!
        }
        call.enqueue(object : retrofit2.Callback<StatusMessageResponse?> {
            override fun onResponse(
                call: Call<StatusMessageResponse?>?,
                response: Response<StatusMessageResponse?>?
            ) {
                try {
                    GifLoading.loading(activity, false)
                    val responseBody = response?.body()
                    val gson = Gson()
                    Log.d("AssignmentResponse", gson.toJson(response))
                    if (response?.code() == 200) {
                        if (responseBody?.status == 1) {
                            UtilConstants.customSuccessAlert(activity, responseBody.message)
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

    private fun jsonNonVoiceHistoryToStdSecGrpStaffStud(activity: Activity?): JsonObject {
        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, token)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, UtilConstants.SchoolID)
        jsonObject.addProperty(STAFF_ID, UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()

        if (ScheduleType.equals("instant")) {
            jsonObjectRequest.addProperty(VOICE_TYPE, ScheduleType)
            jsonObjectRequest.addProperty(VOICE_FILEPATH, VoiceFilePath)
            jsonObjectRequest.addProperty(SCHEDULE_DATE, "")
            jsonObjectRequest.addProperty(SCHEDULE_HOUR, "")
            jsonObjectRequest.addProperty(SCHEDULE_MINUTE, "")
            jsonObjectRequest.addProperty(END_HOUR, "")
            jsonObjectRequest.addProperty(END_MINUTE, "")
            jsonObjectRequest.addProperty(DURATION, UtilConstants.VoiceDuration)
            jsonObjectRequest.addProperty(DESCRIPTION, UtilConstants.Title)
        } else if (ScheduleType.equals("schedule")) {
            jsonObjectRequest.addProperty(VOICE_TYPE, ScheduleType)
            jsonObjectRequest.addProperty(VOICE_FILEPATH, VoiceFilePath)
            jsonObjectRequest.addProperty(SCHEDULE_DATE, UtilConstants.Date)
            jsonObjectRequest.addProperty(SCHEDULE_HOUR, UtilConstants.Hour)
            jsonObjectRequest.addProperty(SCHEDULE_MINUTE, UtilConstants.Minute)
            jsonObjectRequest.addProperty(END_HOUR, UtilConstants.EndHour)
            jsonObjectRequest.addProperty(END_MINUTE, UtilConstants.EndMinute)
            jsonObjectRequest.addProperty(DURATION, UtilConstants.VoiceDuration)
            jsonObjectRequest.addProperty(DESCRIPTION, UtilConstants.Title)

        }

        if (RecipientsType == UtilConstants.StandardSection) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalSectionList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty(ID, it.sectionID)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add(SECTION_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)
        } else if (RecipientsType == UtilConstants.Students) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalStudentList.forEach {
                val jsonsIds = JsonObject()
                val sectionID = it.sectionID
                it.studentData.forEach {
                    jsonsIds.addProperty(ID, it.student_id)
                    jsonsIds.addProperty(SECTION_ID, sectionID)
                    jsonSchoolArray.add(jsonsIds)
                }
            }
            jsonObjectRequest.add(STUDENT_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)
        } else if (UtilConstants.RecipientsType == UtilConstants.Standard) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalStandardList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty(ID, it.standard_id)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add(STANDARD_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)
        } else if (RecipientsType == UtilConstants.Group) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalGroupsList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty(ID, it.group_id)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add(GROUP_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)
        } else if (RecipientsType == UtilConstants.Staff) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalStaffsList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty(ID, it.staff_id)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add(STAFF_DATA, jsonSchoolArray)
            jsonObject.add(REQUEST, jsonObjectRequest)

        }
        return jsonObject

    }

    fun sendEmergencyVoiceHistoryTOSChools(activity: Activity?) {
        val jsonEnergencyVoiceHistoryRequest = jsonEmergencyVoiceHistoryToSchools(activity)
        Log.d("EmergencyHistorySchl", jsonEnergencyVoiceHistoryRequest.toString())
        GifLoading.loading(activity, true)
        val apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.SendEmergencyVoiceToSchoolFromhistory(jsonEnergencyVoiceHistoryRequest)!!
            .enqueue(object : retrofit2.Callback<StatusMessageResponse?> {
                override fun onResponse(
                    call: Call<StatusMessageResponse?>?,
                    response: Response<StatusMessageResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("EmergencyHistorySchlRes", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                UtilConstants.customSuccessAlert(activity, responseBody.message)
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

    private fun jsonEmergencyVoiceHistoryToSchools(activity: Activity?): JsonObject {
        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)

        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, token)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, UtilConstants.SchoolID)
        jsonObject.addProperty(STAFF_ID, UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty(DESCRIPTION, UtilConstants.Title)
        jsonObjectRequest.addProperty(DURATION, UtilConstants.VoiceDuration)
        val jsonSchoolArray = JsonArray()
        UtilConstants.SelectedFinalSchoolsList.forEach {
            val jsonsIds = JsonObject()
            jsonsIds.addProperty(ID, it.school_id)
            jsonSchoolArray.add(jsonsIds)
        }
        jsonObjectRequest.add(ID_SCHOOL, jsonSchoolArray)
        jsonObject.add(REQUEST, jsonObjectRequest)
        return jsonObject

    }

    fun getVoiceHistoryListApi(activity: Activity?) {

        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val Logintoken: String? = Util_shared_preferences.getLoginToken(activity)

        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, Logintoken)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, UtilConstants.SchoolID)
        jsonObject.addProperty(STAFF_ID, UtilConstants.StaffID)
        Log.d("VoiceHistoryRequest", jsonObject.toString())

        GifLoading.loading(activity, true)
        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.GetVoiceHistory(jsonObject)!!
            .enqueue(object : retrofit2.Callback<GetVoiceHistory?> {
                override fun onResponse(
                    call: Call<GetVoiceHistory?>?,
                    response: Response<GetVoiceHistory?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("VoiceHistory:Res", gson.toJson(response))

                        if (response?.code() == 200) {
                            if (responseBody!!.status == 1) {
                                voiceHistoryList = responseBody.data as ArrayList<VoiceHistoryData>
                                Log.d("testHistory", voiceHistoryList.size.toString())
                            } else {
                                UtilConstants.customFailureAlert(
                                    activity, responseBody.message
                                )
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
                                activity!!.getString(R.string.Service_unavailable)
                            )
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }

                }

                override fun onFailure(call: Call<GetVoiceHistory?>?, t: Throwable?) {
                    GifLoading.loading(activity, false)
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }


    fun getApproveLeaveList(activity: Activity?) {

        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val Logintoken: String? = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, Logintoken)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, UtilConstants.SchoolID)
//        jsonObject.addProperty(STAFF_ID, "10000620")
        jsonObject.addProperty(STAFF_ID, UtilConstants.StaffID)
        Log.d("ApproveLeaveListReq:", jsonObject.toString())

        GifLoading.loading(activity, true)
        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.StaffApproveLeavelist(jsonObject)!!
            .enqueue(object : retrofit2.Callback<StaffApproveLeave?> {
                override fun onResponse(
                    call: Call<StaffApproveLeave?>?,
                    response: Response<StaffApproveLeave?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("VoiceHistory:Res", gson.toJson(response))

                        if (response?.code() == 200) {
                            if (responseBody!!.status == 1) {
                                ApproveLeaveList = responseBody.data as ArrayList<ApproveLeaveData>
                                val approveLeave: TeacherApproveLeave
                                approveLeave = TeacherApproveLeave()
                                approveLeave.setAdapterLeaveStatus()
                                Log.d("testApprveleave", "test")


                            } else {
                                UtilConstants.customFailureAlert(
                                    activity, responseBody.message
                                )
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
                                activity!!.getString(R.string.Service_unavailable)
                            )
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }

                }

                override fun onFailure(call: Call<StaffApproveLeave?>?, t: Throwable?) {
                    GifLoading.loading(activity, false)
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    fun approveLeaveStatus(activity: Activity?) {

        val jsonApproveLeaveRequest = jsonApproveLeaveStatusRequest(activity)
        Log.d("ApproveLeaveStatus:Req", jsonApproveLeaveRequest.toString())

        GifLoading.loading(activity, true)
        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.StaffApproveLeaveStatusUpdate(jsonApproveLeaveRequest)!!
            .enqueue(object : retrofit2.Callback<StatusMessageResponse?> {
                override fun onResponse(
                    call: Call<StatusMessageResponse?>?,
                    response: Response<StatusMessageResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("ApproveLeaveStatus:Res", gson.toJson(response))

                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                UtilConstants.customSuccessAlert(activity, responseBody.message)
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
                                activity!!.getString(R.string.Service_unavailable)
                            )
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }

                }

                override fun onFailure(call: Call<StatusMessageResponse?>?, t: Throwable?) {
                    GifLoading.loading(activity, false)
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    private fun jsonApproveLeaveStatusRequest(activity: Activity?): JsonObject {
        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val Logintoken: String? = Util_shared_preferences.getLoginToken(activity)

        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, Logintoken)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, UtilConstants.SchoolID)
        jsonObject.addProperty(STAFF_ID, UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty(LEAVE_ID, UtilConstants.ApproveLeaveId)
        jsonObjectRequest.addProperty(STATUS, ApproveLeaveTypeStatus)
        jsonObject.add(REQUEST, jsonObjectRequest)
        return jsonObject

    }
///Delete Assignment

}


