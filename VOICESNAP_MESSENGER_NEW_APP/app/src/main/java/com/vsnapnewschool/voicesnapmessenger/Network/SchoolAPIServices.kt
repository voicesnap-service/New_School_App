@file:Suppress("DEPRECATION")

package com.vsnapnewschool.voicesnapmessenger.Network

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentResolver
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.os.SystemClock
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Rest.APIClient
import com.vsnapnewschool.voicesnapmessenger.Activities.TeacherApproveLeave
import com.vsnapnewschool.voicesnapmessenger.CallBacks.*
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.CLASS_ID
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.COUNTRY_ID
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.DESCRIPTION
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.DEVICE_TOKEN
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.DEVICE_TYPE
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.DIALING_ID
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.DURATION
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.ENCRYPT_PASSWORD
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.END_HOUR
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.END_MINUTE
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.FILETYPE
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.FILE_ARRAY
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.GROUP_DATA
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.HEADER_ID
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.ID
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.ID_SCHOOL
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.IMEI_NUMBER
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.IS_ARCHIVE
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
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.STUDENT_ARRAY
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.STUDENT_DATA
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.STUDENT_ID
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.TARGET_TYPE
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.TOKEN
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.VOICE_FILEPATH
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues.Companion.VOICE_TYPE
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices.call
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.*
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.AWSUploadedFilesList
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.ApproveLeaveList
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.ApproveLeaveTypeStatus
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.AssignmentFiletype
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.AssignmentStudentID
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
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.ManagementCountImage
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.ManagementCountPdf
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.ManagementCountText
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.ManagementCountVideo
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.ManagementCountVoice
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
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import com.vsnapnewschool.voicesnapmessenger.Util_Common.GifLoading
import net.ypresto.androidtranscoder.MediaTranscoder
import net.ypresto.androidtranscoder.format.MediaFormatStrategyPresets
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.*
import java.util.*
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

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
        jsonObjectRequest.addProperty("file_type", UtilConstants.FileTypeForApi)
        val jsonarray = JsonArray()
        AWSUploadedFilesList.forEach {
            val jsonsIds = JsonObject()
            jsonsIds.addProperty("file_path", it.filepath)
            jsonsIds.addProperty("original_file_name", it.fileName)
            jsonarray.add(jsonsIds)

        }
        jsonObjectRequest.add(FILE_ARRAY, jsonarray)
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
        jsonObjectRequest.addProperty("file_type", UtilConstants.FileTypeForApi)
        val jsonarray = JsonArray()
        AWSUploadedFilesList.forEach {
            val jsonsIds = JsonObject()
            jsonsIds.addProperty("file_path", it.filepath)
            jsonsIds.addProperty("original_file_name", it.fileName)
            jsonarray.add(jsonsIds)

        }
        jsonObjectRequest.add(FILE_ARRAY, jsonarray)

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
        if (RecipientsType == UtilConstants.Students) {
            call = apiInterface.sendAssignmentToStudents(jsonAssignment)!!
        } else if (RecipientsType == UtilConstants.StandardSection) {
            call = apiInterface.sendAssignmentToSections(jsonAssignment)!!

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
                    jsonsIds.addProperty(ID, it.student_id)
                    jsonsIds.addProperty(SECTION_ID, sectionID)
                    jsonSchoolArray.add(jsonsIds)
                }
            }
            jsonObjectRequest.add(STUDENT_ARRAY, jsonSchoolArray)
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

    fun getAssingmentListApi(activity: Activity?, callBack: AssingmentHistoryCallback) {

        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, token)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, UtilConstants.SchoolID)
        jsonObject.addProperty(STAFF_ID, UtilConstants.StaffID)

        Log.d("GetAssingmentList", jsonObject.toString())
        val apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getAssingmentList(jsonObject)!!
            .enqueue(object : retrofit2.Callback<GetAssingmentResponse?> {
                override fun onResponse(
                    call: Call<GetAssingmentResponse?>?,
                    response: Response<GetAssingmentResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("AssignmentResponse", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                callBack.callBackAssingmentHistory(responseBody)
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

                override fun onFailure(call: Call<GetAssingmentResponse?>?, t: Throwable?) {
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }


    fun getAssingmentMemberList(
        activity: Activity?,
        callBAck: GetAssingmentMemberSubmissionCallback
    ) {

        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)

        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, token)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, UtilConstants.SchoolID)
        jsonObject.addProperty(STAFF_ID, UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty(TARGET_TYPE, UtilConstants.AssingmentTargetType)
        jsonObjectRequest.addProperty(HEADER_ID, UtilConstants.AssignmentHeaderID)
        jsonObjectRequest.addProperty(IS_ARCHIVE, "0")
        jsonObject.add(REQUEST, jsonObjectRequest)

        Log.d("GetAssingmentMemberList", jsonObject.toString())
        val apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getMemberAssingmentList(jsonObject)!!
            .enqueue(object : retrofit2.Callback<GetAssingmentMemberSubmissions?> {
                override fun onResponse(
                    call: Call<GetAssingmentMemberSubmissions?>?,
                    response: Response<GetAssingmentMemberSubmissions?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("AssignmentResponse", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                callBAck.callbackSubmissions(responseBody)

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

                override fun onFailure(
                    call: Call<GetAssingmentMemberSubmissions?>?,
                    t: Throwable?
                ) {
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    fun getSubmittedViewAssingmentFiles(
        activity: Activity?,
        callBAck: GetViewSubmittedAssignmentFiles
    ) {

        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)


        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, token)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, UtilConstants.SchoolID)
        jsonObject.addProperty(STAFF_ID, UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty(STUDENT_ID, AssignmentStudentID)
        jsonObjectRequest.addProperty(HEADER_ID, UtilConstants.AssignmentHeaderID)
        jsonObjectRequest.addProperty(FILETYPE, AssignmentFiletype)
        jsonObject.add(REQUEST, jsonObjectRequest)

        Log.d("GetAssingmentViewFile", jsonObject.toString())
        val apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getSubmittedAssingmentViewFiles(jsonObject)!!
            .enqueue(object : retrofit2.Callback<GetAssingmentSubmittedFiles?> {
                override fun onResponse(
                    call: Call<GetAssingmentSubmittedFiles?>?,
                    response: Response<GetAssingmentSubmittedFiles?>?
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

                override fun onFailure(call: Call<GetAssingmentSubmittedFiles?>?, t: Throwable?) {
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }


    fun sendNonEmergencyVoiceHistoryToEntireSchool(activity: Activity?) {
        val jsonNonVoiceHistoryRequest = jsonNonVoiceHistoryEntireRequest(activity)
        Log.d("NonVoiceHistoryEntire", jsonNonVoiceHistoryRequest.toString())
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
                        Log.d("NonVoiceHistoryEntire", gson.toJson(response))
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
        jsonObjectRequest.addProperty(VOICE_TYPE, "schedule")
        jsonObjectRequest.addProperty(VOICE_FILEPATH, VoiceFilePath)
        jsonObjectRequest.addProperty(SCHEDULE_DATE, "")
        jsonObjectRequest.addProperty(SCHEDULE_HOUR, "")
        jsonObjectRequest.addProperty(SCHEDULE_MINUTE, "")
        jsonObjectRequest.addProperty(END_HOUR, "")
        jsonObjectRequest.addProperty(END_MINUTE, "")
        jsonObjectRequest.addProperty(DURATION, UtilConstants.VoiceDuration)
        jsonObjectRequest.addProperty(DESCRIPTION, "test")
        jsonObject.add(REQUEST, jsonObjectRequest)


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

        jsonObjectRequest.addProperty(VOICE_TYPE, "schedule")
        jsonObjectRequest.addProperty(VOICE_FILEPATH, VoiceFilePath)
        jsonObjectRequest.addProperty(SCHEDULE_DATE, "")
        jsonObjectRequest.addProperty(SCHEDULE_HOUR, "")
        jsonObjectRequest.addProperty(SCHEDULE_MINUTE, "")
        jsonObjectRequest.addProperty(END_HOUR, "")
        jsonObjectRequest.addProperty(END_MINUTE, "")
        jsonObjectRequest.addProperty(DURATION, UtilConstants.VoiceDuration)
        jsonObjectRequest.addProperty(DESCRIPTION, "test")

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

    fun getVoiceHistoryListApi(activity: Activity?, callBack: TeacherVoiceHistoryCallBack) {

        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val Logintoken: String? = Util_shared_preferences.getLoginToken(activity)

        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, Logintoken)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, UtilConstants.SchoolID)
        jsonObject.addProperty(STAFF_ID, UtilConstants.StaffID)
        Log.d("VoiceHistoryRequest", jsonObject.toString())

        GifLoading.loading(activity, false)
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
                                callBack.callBackHistoryList(responseBody)


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


    fun DeleteAssingment(activity: Activity?) {
        val jsonDeleteAssingment = jsonDeleteAssingment(activity)
        Log.d("DeleteAssingment", jsonDeleteAssingment.toString())
        GifLoading.loading(activity, true)
        val apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.DeleteAssignment(jsonDeleteAssingment)!!
            .enqueue(object : retrofit2.Callback<StatusMessageResponse?> {
                override fun onResponse(
                    call: Call<StatusMessageResponse?>?,
                    response: Response<StatusMessageResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("DeleteAssingment-Res:", gson.toJson(response))
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

    private fun jsonDeleteAssingment(activity: Activity?): JsonObject {
        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)

        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, token)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, UtilConstants.SchoolID)
        jsonObject.addProperty(STAFF_ID, UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty(ID, UtilConstants.AssignmentHeaderID)

        jsonObject.add(REQUEST, jsonObjectRequest)
        return jsonObject

    }

    fun getMessageFromManagementTextMessage(
        activity: Activity?,
        callBack: MsgFromManagementCallBack,
        ApiType: String?,
        recyclerview: RecyclerView,
        shimmerFrameLayout: ShimmerFrameLayout
    ) {

        val jsonManagementRequest = jsonManagementRequest(activity)
        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        var call: Call<MessageFromManagementTextResponse?>?
        if (ApiType.equals(UtilConstants.API_NORMAL)) {
            call = apiInterface.MessageFomManagementText(jsonManagementRequest)
        } else {
            call = apiInterface.MessageFomManagementText_Archive(jsonManagementRequest)
        }
        call!!.enqueue(object :
            retrofit2.Callback<MessageFromManagementTextResponse?> {
            override fun onResponse(
                call: Call<MessageFromManagementTextResponse?>?,
                response: Response<MessageFromManagementTextResponse?>?
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
                            if (ApiType.equals(UtilConstants.API_NORMAL)) {
                                callBack.callBackManagementText(responseBody)
                            } else {
                                callBack.callBackManagementText_Archive(responseBody)
                            }

                        } else if (response.code() == 400 || response.code() == 500) {
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

            override fun onFailure(call: Call<MessageFromManagementTextResponse?>?, t: Throwable?) {
                Log.d("Failure", t.toString())
                UtilConstants.normalToast(activity, t.toString())
            }
        })
    }

    fun getMessageFromManagementImageMessage(
        activity: Activity?,
        callBack: MsgFromManagementImageCallBack,
        ApiType: String?,
        recyclerview: RecyclerView,
        shimmerFrameLayout: ShimmerFrameLayout
    ) {

        val jsonManagementRequest = jsonManagementRequest(activity)
        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        var call: Call<MessageFromMangementImageResponse?>?
        if (ApiType.equals(UtilConstants.API_NORMAL)) {
            call = apiInterface.MessageFomManagementImage(jsonManagementRequest)
        } else {
            call = apiInterface.MessageFomManagementImage_Archive(jsonManagementRequest)
        }
        call!!.enqueue(object :
            retrofit2.Callback<MessageFromMangementImageResponse?> {
            override fun onResponse(
                call: Call<MessageFromMangementImageResponse?>?,
                response: Response<MessageFromMangementImageResponse?>?
            ) {
                try {
                    shimmerFrameLayout.stopShimmerAnimation()
                    recyclerview.visibility = View.VISIBLE
                    shimmerFrameLayout.visibility = View.GONE

                    val responseBody = response?.body()
                    val gson = Gson()
                    Log.d("ImageMessageResponse", gson.toJson(response))
                    if (response?.code() == 200) {
                        if (responseBody!!.status == 1) {
                            if (ApiType.equals(UtilConstants.API_NORMAL)) {
                                callBack.callBackManagementImage(responseBody)
                            } else {
                                callBack.callBackManagementImage_Archive(responseBody)
                            }

                        } else if (response.code() == 400 || response.code() == 500) {
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

            override fun onFailure(call: Call<MessageFromMangementImageResponse?>?, t: Throwable?) {
                Log.d("Failure", t.toString())
                UtilConstants.normalToast(activity, t.toString())
            }
        })
    }

    fun getMessageFromManagementPdfMessage(
        activity: Activity?,
        callBack: MsgFromManagementPdfCallBack,
        ApiType: String?,
        recyclerview: RecyclerView,
        shimmerFrameLayout: ShimmerFrameLayout
    ) {

        val jsonManagementRequest = jsonManagementRequest(activity)
        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        var call: Call<MessageFromManagementPdfResponse?>?
        if (ApiType.equals(UtilConstants.API_NORMAL)) {
            call = apiInterface.MessageFomManagementPdf(jsonManagementRequest)
        } else {
            call = apiInterface.MessageFomManagementPdf_Archive(jsonManagementRequest)
        }
        call!!.enqueue(object :
            retrofit2.Callback<MessageFromManagementPdfResponse?> {
            override fun onResponse(
                call: Call<MessageFromManagementPdfResponse?>?,
                response: Response<MessageFromManagementPdfResponse?>?
            ) {
                try {
                    shimmerFrameLayout.stopShimmerAnimation()
                    recyclerview.visibility = View.VISIBLE
                    shimmerFrameLayout.visibility = View.GONE

                    val responseBody = response?.body()
                    val gson = Gson()
                    Log.d("PDFMessageResponse", gson.toJson(response))
                    if (response?.code() == 200) {
                        if (responseBody!!.status == 1) {
                            if (ApiType.equals(UtilConstants.API_NORMAL)) {
                                callBack.callBackManagementPdf(responseBody)
                            } else {
                                callBack.callBackManagementPdf_Archive(responseBody)
                            }

                        } else if (response.code() == 400 || response.code() == 500) {
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

            override fun onFailure(call: Call<MessageFromManagementPdfResponse?>?, t: Throwable?) {
                Log.d("Failure", t.toString())
                UtilConstants.normalToast(activity, t.toString())
            }
        })
    }

    fun getMessageFromManagementVideoMessage(
        activity: Activity?,
        callBack: MsgFromManagementVideoCallBack,
        ApiType: String?,
        recyclerview: RecyclerView,
        shimmerFrameLayout: ShimmerFrameLayout
    ) {

        val jsonManagementRequest = jsonManagementRequest(activity)
        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        var call: Call<MessageFromManagementVideoResponse?>?
        if (ApiType.equals(UtilConstants.API_NORMAL)) {
            call = apiInterface.MessageFomManagementVideo(jsonManagementRequest)
        } else {
            call = apiInterface.MessageFomManagementVideo_Archive(jsonManagementRequest)
        }
        call!!.enqueue(object :
            retrofit2.Callback<MessageFromManagementVideoResponse?> {
            override fun onResponse(
                call: Call<MessageFromManagementVideoResponse?>?,
                response: Response<MessageFromManagementVideoResponse?>?
            ) {
                try {
                    shimmerFrameLayout.stopShimmerAnimation()
                    recyclerview.visibility = View.VISIBLE
                    shimmerFrameLayout.visibility = View.GONE

                    val responseBody = response?.body()
                    val gson = Gson()
                    Log.d("VideoMessageResponse", gson.toJson(response))
                    if (response?.code() == 200) {
                        if (responseBody!!.status == 1) {
                            if (ApiType.equals(UtilConstants.API_NORMAL)) {
                                callBack.callBackManagementVideo(responseBody)
                            } else {
                                callBack.callBackManagementVideo_Archive(responseBody)
                            }

                        } else if (response.code() == 400 || response.code() == 500) {
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

            override fun onFailure(
                call: Call<MessageFromManagementVideoResponse?>?,
                t: Throwable?
            ) {
                Log.d("Failure", t.toString())
                UtilConstants.normalToast(activity, t.toString())
            }
        })
    }


    fun getMessageFromManagementVoiceMessage(
        activity: Activity?,
        callBack: MsgFromManagementVoiceCallBack,
        ApiType: String?,
        recyclerview: RecyclerView,
        shimmerFrameLayout: ShimmerFrameLayout
    ) {

        val jsonManagementRequest = jsonManagementRequest(activity)
        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        var call: Call<MessageFromManagementVoiceResponse?>?
        if (ApiType.equals(UtilConstants.API_NORMAL)) {
            call = apiInterface.MessageFomManagementVoice(jsonManagementRequest)
        } else {
            call = apiInterface.MessageFomManagementVoice_Archive(jsonManagementRequest)
        }
        call!!.enqueue(object :
            retrofit2.Callback<MessageFromManagementVoiceResponse?> {
            override fun onResponse(
                call: Call<MessageFromManagementVoiceResponse?>?,
                response: Response<MessageFromManagementVoiceResponse?>?
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
                            if (ApiType.equals(UtilConstants.API_NORMAL)) {
                                callBack.callBackManagementVoice(responseBody)
                            } else {
                                callBack.callBackManagementVoice_Archive(responseBody)
                            }

                        } else if (response.code() == 400 || response.code() == 500) {
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

            override fun onFailure(
                call: Call<MessageFromManagementVoiceResponse?>?,
                t: Throwable?
            ) {
                Log.d("Failure", t.toString())
                UtilConstants.normalToast(activity, t.toString())
            }
        })
    }

    fun jsonManagementRequest(activity: Activity?): JsonObject {
        val mobileNumber = Util_shared_preferences.getMobileNumber(activity)
        val loginToken = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, loginToken)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, UtilConstants.SchoolID)
        jsonObject.addProperty(STAFF_ID, UtilConstants.StaffID)

        Log.d("ManagementRequest", jsonObject.toString())
        return jsonObject

    }

    fun jsonSectionStrenthRequest(activity: Activity?): JsonObject {
        val mobileNumber = Util_shared_preferences.getMobileNumber(activity)
        val loginToken = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty(LOGIN_TOKEN, loginToken)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, UtilConstants.SchoolID)
        jsonObject.addProperty(STAFF_ID, UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty(CLASS_ID, UtilConstants.ClassStrengthID)
        jsonObject.add(REQUEST, jsonObjectRequest)
        Log.d("sectionStrength", jsonObject.toString())
        return jsonObject

    }

    fun getCountForManagement(activity: Activity?) {
        val jsonManagementRequest = jsonManagementRequest(activity)

        Log.d("CountManagementRequest", jsonManagementRequest.toString())
        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getUnReadCountForManagement(jsonManagementRequest)!!
            .enqueue(object : retrofit2.Callback<GetCountForManagement?> {
                override fun onResponse(
                    call: Call<GetCountForManagement?>?,
                    response: Response<GetCountForManagement?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("CountManagement", gson.toJson(response))

                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {

//                                callBack.callBackCount(responseBody)

                                ManagementCountText = responseBody.data[0].sms_count
                                Log.d("ManagementCountText", ManagementCountText!!)
                                ManagementCountVoice = responseBody.data[0].voice_count
                                Log.d("ManagementCountVoice", ManagementCountVoice!!)

                                ManagementCountImage = responseBody.data[0].image_count
                                ManagementCountPdf = responseBody.data[0].pdf_count
                                ManagementCountVideo = responseBody.data[0].video_count
                                Util_shared_preferences.putCountDetails(
                                    activity,
                                    ManagementCountText,
                                    ManagementCountVoice,
                                    ManagementCountImage,
                                    ManagementCountPdf,
                                    ManagementCountVideo
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
                                activity?.getString(R.string.Service_unavailable)
                            )
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }
                }

                override fun onFailure(call: Call<GetCountForManagement?>?, t: Throwable?) {
                    GifLoading.loading(activity, false)
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    fun SendVideoToEntireSchool(activity: Activity?) {
        val jsonTextRequest = jsonVideoEntireSchoolRequest(activity)
        Log.d("VideoRequest", jsonTextRequest.toString())
        GifLoading.loading(activity, true)

        val apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.sendVideoToEntireSchool(jsonTextRequest)!!
            .enqueue(object : retrofit2.Callback<StatusMessageResponse?> {
                override fun onResponse(
                    call: Call<StatusMessageResponse?>?,
                    response: Response<StatusMessageResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)

                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("VideoRes:", gson.toJson(response))
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

    private fun jsonVideoEntireSchoolRequest(activity: Activity?): JsonObject {
        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty("login_token", token)
        jsonObject.addProperty("mobile_number", mobileNumber)
        jsonObject.addProperty("school_id", UtilConstants.SchoolID)
        jsonObject.addProperty("staff_id", UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty("title", UtilConstants.Title)
        jsonObjectRequest.addProperty("description", UtilConstants.Description)
        jsonObjectRequest.addProperty("iframe", UtilConstants.VimeoIframe)
        jsonObjectRequest.addProperty("video_url", UtilConstants.VimeoVideoUrl)
        jsonObject.add("request", jsonObjectRequest)
        return jsonObject

    }

    fun sendVideoToStandardGroupStaffSectionStudents(activity: Activity?) {
        GifLoading.loading(activity, true)

        val apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        val jsonTextRequest = jsonVideoStandardGroupStaffSectionRequest(activity)
        Log.d("VideoToGroupsRequest", jsonTextRequest.toString())
        if (UtilConstants.RecipientsType == UtilConstants.Standard) {
            call = apiInterface.sendVideoToStandards(jsonTextRequest)!!
        } else if (UtilConstants.RecipientsType == UtilConstants.StandardSection) {
            call = apiInterface.sendVideoToSections(jsonTextRequest)!!
        } else if (UtilConstants.RecipientsType == UtilConstants.Students) {
            call = apiInterface.sendVideoToStudents(jsonTextRequest)!!
        } else if (UtilConstants.RecipientsType == UtilConstants.Group) {
            call = apiInterface.sendVideoToGroups(jsonTextRequest)!!
        } else if (UtilConstants.RecipientsType == UtilConstants.Staff) {
            call = apiInterface.sendVideoToStaffs(jsonTextRequest)!!
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
                    Log.d("VideoToGroups-Res:", gson.toJson(response))
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
                    Log.d("VideoException", e.toString())
                }
            }

            override fun onFailure(call: Call<StatusMessageResponse?>, t: Throwable) {
                Log.d("VideoFailure", t.toString())
                GifLoading.loading(activity, false)

                UtilConstants.normalToast(activity, t.toString())
            }
        })

    }


    private fun jsonVideoStandardGroupStaffSectionRequest(activity: Activity?): JsonObject {
        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty("login_token", token)
        jsonObject.addProperty("mobile_number", mobileNumber)
        jsonObject.addProperty("school_id", UtilConstants.SchoolID)
        jsonObject.addProperty("staff_id", UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty("title", UtilConstants.Title)
        jsonObjectRequest.addProperty("description", UtilConstants.Description)
        jsonObjectRequest.addProperty("iframe", UtilConstants.VimeoIframe)
        jsonObjectRequest.addProperty("video_url", UtilConstants.VimeoVideoUrl)

        if (UtilConstants.RecipientsType == UtilConstants.Students) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalStudentList.forEach {
                val jsonsIds = JsonObject()
                val sectionID = it.sectionID
                it.studentData.forEach {
                    Log.d("SectionID", sectionID!!)
                    jsonsIds.addProperty("id", it.student_id)
                    jsonsIds.addProperty("section_id", sectionID)
                    jsonSchoolArray.add(jsonsIds)

                }

            }


            jsonObjectRequest.add("student_data", jsonSchoolArray)
            jsonObject.add("request", jsonObjectRequest)
        } else if (UtilConstants.RecipientsType == UtilConstants.Standard) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalStandardList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty("id", it.standard_id)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add("standard_data", jsonSchoolArray)
            jsonObject.add("request", jsonObjectRequest)

            Log.d("StandardRequst", jsonObjectRequest.toString())
        } else if (UtilConstants.RecipientsType == UtilConstants.StandardSection) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalSectionList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty("id", it.sectionID)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add("section_data", jsonSchoolArray)
            jsonObject.add("request", jsonObjectRequest)
        } else if (UtilConstants.RecipientsType == UtilConstants.Group) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalGroupsList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty("id", it.group_id)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add("group_data", jsonSchoolArray)
            jsonObject.add("request", jsonObjectRequest)
            Log.d("Group", jsonSchoolArray.toString())
        } else if (UtilConstants.RecipientsType == UtilConstants.Staff) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalStaffsList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty("id", it.staff_id)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add("staff_data", jsonSchoolArray)
            jsonObject.add("request", jsonObjectRequest)
            Log.d("Staff", jsonSchoolArray.toString())

        }


        return jsonObject
    }

    fun videosizereducer(activity: Activity) {
        var file: File
        var mFuture: Future<Void>? = null
        try {
            val outputDir = File(activity.getExternalFilesDir(null), "outputs")
            outputDir.mkdir()
            file = File.createTempFile("transcode_test", ".mp4", outputDir)
        } catch (e: IOException) {
            Log.e("fail", "Failed to create temporary file.", e)
            return
        }
        val resolver: ContentResolver = activity.getContentResolver()
        val parcelFileDescriptor: ParcelFileDescriptor
        val file1 = File(UtilConstants.VideoFilePath)
        parcelFileDescriptor = try {
            resolver.openFileDescriptor(Uri.fromFile(file1), "r")!!
        } catch (e: FileNotFoundException) {
            Log.w("Could not open '" + Uri.fromFile(file1) + "'", e)
            return
        }
        assert(parcelFileDescriptor != null)
        val fileDescriptor = parcelFileDescriptor.fileDescriptor
        val progressBar = ProgressDialog(activity)
        progressBar.setCancelable(false)
        progressBar.setMessage("loading")
        progressBar.show()
        val startTime = SystemClock.uptimeMillis()
        fun onTranscodeFinished(
            isSuccess: Boolean,
            parcelFileDescriptor: ParcelFileDescriptor
        ) {
            val progressBar = ProgressDialog(activity)
            progressBar.setCancelable(false)
            progressBar.setMessage("loading")
            progressBar.setCancelable(false)
            progressBar.show()
            progressBar.isIndeterminate = false
            try {
                progressBar.dismiss()
                parcelFileDescriptor.close()
            } catch (e: IOException) {
                progressBar.dismiss()
                Log.w("Error while closing", e)
            }
        }

        val listener: MediaTranscoder.Listener = object : MediaTranscoder.Listener {
            override fun onTranscodeProgress(progress: Double) {
                if (progress < 0) {
                    progressBar.isIndeterminate = true
                } else {
                    progressBar.isIndeterminate = false
                    progressBar.progress = Math.round(progress * 100).toInt()
                }
            }

            override fun onTranscodeCompleted() {
                progressBar.dismiss()
                Log.d(
                    "test",
                    "transcoding took " + (SystemClock.uptimeMillis() - startTime) + "ms"
                )
                onTranscodeFinished(true, parcelFileDescriptor)
                VimeoVideoUpload(activity, file)

            }

            override fun onTranscodeCanceled() {
                progressBar.dismiss()
                onTranscodeFinished(false, parcelFileDescriptor)
                file = File(UtilConstants.VideoFilePath)
                VimeoVideoUpload(activity, file)


            }

            override fun onTranscodeFailed(exception: java.lang.Exception) {
                progressBar.dismiss()
                onTranscodeFinished(false, parcelFileDescriptor)
                file = File(UtilConstants.VideoFilePath)
                VimeoVideoUpload(activity, file)


            }
        }

        mFuture = MediaTranscoder.getInstance().transcodeVideo(
            fileDescriptor,
            file.getAbsolutePath(),
            MediaFormatStrategyPresets.createAndroid720pStrategy(),
            listener
        )

    }


    fun VimeoVideoUpload(activity: Activity, file: File) {
        val strsize = file.length()
        Log.d("size", strsize.toString())
        val clientinterceptor = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        clientinterceptor.interceptors().add(interceptor)
        val client1: OkHttpClient
        client1 = OkHttpClient.Builder()
            .connectTimeout(300, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .build()
        val retrofit = Retrofit.Builder()
            .client(client1)
            .baseUrl("https://api.vimeo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service: ApiInterface =
            retrofit.create(ApiInterface::class.java)
        val mProgressDialog = ProgressDialog(activity)
        mProgressDialog.isIndeterminate = true
        mProgressDialog.setMessage("Connecting...")
        mProgressDialog.setCancelable(false)
        mProgressDialog.show()
        val `object` = JsonObject()
        val jsonObjectclasssec = JsonObject()
        jsonObjectclasssec.addProperty("approach", "post")
        jsonObjectclasssec.addProperty("size", strsize.toString())
        val jsonprivacy = JsonObject()
        jsonprivacy.addProperty("view", "unlisted")
        val jsonshare = JsonObject()
        jsonshare.addProperty("share", "false")
        val jsonembed = JsonObject()
        jsonembed.add("buttons", jsonshare)
        `object`.add("upload", jsonObjectclasssec)
        `object`.addProperty("name", UtilConstants.Title)
        `object`.addProperty("description", UtilConstants.Description)
        `object`.add("privacy", jsonprivacy)
        `object`.add("embed", jsonembed)
        val head =
            "Bearer " + "8d74d8bf6b5742d39971cc7d3ffbb51a"
        Log.d("header", head)
        val call: Call<JsonObject> = service.VideoUpload(`object`, head)
        Log.d("jsonOBJECT", `object`.toString())
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                if (mProgressDialog.isShowing) mProgressDialog.dismiss()
                val res = response.code()
                Log.d("RESPONSE", res.toString())
                if (response.isSuccessful) {
                    try {
                        Log.d("try", "testtry")
                        val object1 = JSONObject(response.body().toString())
                        Log.d("Response sucess", "response entered success")
                        val obj = object1.getJSONObject("upload")
                        val obj1 = object1.getJSONObject("embed")
                        val upload_link = obj.getString("upload_link")
                        val link = object1.getString("link")
                        val iframe = obj1.getString("html")
                        Log.d("c", upload_link)
                        Log.d("iframe", iframe)

                        UtilConstants.VimeoIframe = iframe
                        UtilConstants.VimeoVideoUrl = link
                        try {
                            VIDEOUPLOAD(upload_link, file, activity)
                        } catch (e: Exception) {
                            Log.e("VIMEO Exception", e.message!!)
                            UtilConstants.normalToast(activity, "Video sending failed.Retry")
                        }
                    } catch (e: Exception) {
                        Log.e("VIMEO Exception", e.message!!)
                        UtilConstants.normalToast(activity, e.message)
                    }
                } else {
                    Log.d("Response fail", "fail")
                    UtilConstants.normalToast(activity, "Video sending failed.Retry")
                }
            }

            override fun onFailure(
                call: Call<JsonObject>,
                t: Throwable
            ) {
                if (mProgressDialog.isShowing) mProgressDialog.dismiss()
                Log.e("Response Failure", t.message!!)
                UtilConstants.normalToast(activity, "Video sending failed.Retry")
            }
        })
    }


    private fun VIDEOUPLOAD(
        upload_link: String,
        file: File,
        activity: Activity
    ) {
        val separated =
            upload_link.split("?").toTypedArray()

        val name = separated[0] //"/"
        val FileName = separated[1]
        val upload = name.replace("upload", "")
        val id = FileName.split("&").toTypedArray()
        val ticket_id = id[0]
        val video_file_id = id[1]
        val signature = id[2]
        val v6 = id[3]
        val redirect_url = id[4]
        val seperate1: Array<String> = ticket_id.split("=").toTypedArray()
        val ticket = seperate1[0] //"/"
        val ticket2 = seperate1[1]
        val seperate2: Array<String> = video_file_id.split("=").toTypedArray()
        val ticket1 = seperate2[0] //"/"
        val ticket3 = seperate2[1]
        val seper: Array<String> = signature.split("=").toTypedArray()
        val ticke = seper[0] //"/"
        val tick = seper[1]
        val sepera: Array<String> = v6.split("=").toTypedArray()
        val str = sepera[0] //"/"
        val str1 = sepera[1]
        val sucess: Array<String> = redirect_url.split("=").toTypedArray()
        val urlRIDERCT = sucess[0] //"/"
        val redirect_url123 = sucess[1]
        val client1: OkHttpClient
        client1 = OkHttpClient.Builder()
            .connectTimeout(600, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.MINUTES)
            .writeTimeout(40, TimeUnit.MINUTES)
            .build()
        val retrofit = Retrofit.Builder()
            .client(client1)
            .baseUrl(upload)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val mProgressDialog = ProgressDialog(activity)
        mProgressDialog.isIndeterminate = true
        mProgressDialog.setMessage("Uploading...")
        mProgressDialog.setCancelable(false)
        mProgressDialog.show()
        val service: ApiInterface =
            retrofit.create(ApiInterface::class.java)
        var requestFile: RequestBody? = null
        try {
            val filepath = file.toString()
            Log.d("filepath", filepath)
            Log.d("filepath", UtilConstants.VideoFilePath.toString())
            val `in`: InputStream = FileInputStream(filepath)
            val buf: ByteArray
            buf = ByteArray(`in`.available())
            while (`in`.read(buf) != -1);
            requestFile = RequestBody.create(
                MediaType.parse("application/offset+octet-stream"),
                buf
            )
        } catch (e: IOException) {
            e.printStackTrace()
            UtilConstants.normalToast(activity, e.message)
        }
        val call: Call<ResponseBody> = service.patchVimeoVideoMetaData(
            ticket2,
            ticket3,
            tick,
            str1,
            redirect_url123 + "www.voicesnapforschools.com",
            requestFile
        )
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: Response<ResponseBody?>
            ) {
                if (mProgressDialog.isShowing) mProgressDialog.dismiss()
                try {
                    if (response.isSuccessful) {
                        if (RecipientsType == UtilConstants.Standard || RecipientsType == UtilConstants.StandardSection || RecipientsType == UtilConstants.Students ||
                            RecipientsType == UtilConstants.Staff || RecipientsType == UtilConstants.Group
                        ) {
                            SchoolAPIServices.sendVideoToStandardGroupStaffSectionStudents(activity)
                        } else {
                            SchoolAPIServices.SendVideoToEntireSchool(activity)
                        }

                    } else {
                        UtilConstants.normalToast(activity, "Video sending failed.Retry")
                    }
                } catch (e: Exception) {
                    UtilConstants.normalToast(activity, e.message)
                }
            }

            override fun onFailure(
                call: Call<ResponseBody?>,
                t: Throwable
            ) {
                if (mProgressDialog.isShowing) mProgressDialog.dismiss()
                UtilConstants.normalToast(activity, "Video sending failed.Retry")
            }
        })
    }

    //Chat - Teacher

    fun getStaffClassesforChat(activity: Activity?, callback: GetStaffClassesChatCallBack) {
        val jsonRequest = jsonStaffclassesforchat(activity)
        Log.d("Request", jsonRequest.toString())
        GifLoading.loading(activity, true)

        val apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.staffClassesforChat(jsonRequest)!!
            .enqueue(object : retrofit2.Callback<StaffChatClassResponse?> {
                override fun onResponse(
                    call: Call<StaffChatClassResponse?>?,
                    response: Response<StaffChatClassResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)

                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("Res:", gson.toJson(response))
//                        StaffClassDetails.clear()
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                callback.callbackstaffclasseschat(responseBody)
//                                StaffClassDetails=responseBody.data as ArrayList<StaffChatClassDetail>
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

                override fun onFailure(call: Call<StaffChatClassResponse?>?, t: Throwable?) {
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    private fun jsonStaffclassesforchat(activity: Activity?): JsonObject {
        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty("login_token", token)
        jsonObject.addProperty("mobile_number", mobileNumber)
        jsonObject.addProperty("school_id", UtilConstants.SchoolID)
        jsonObject.addProperty("staff_id", UtilConstants.StaffID)
        return jsonObject

    }

    fun StaffChatScreen(
        activity: Activity?,
        currentoffset: Int,
        callback: GetStaffChatScreenCallBack
    ) {
        val jsonRequest = jsonStaffchatscreen(activity, currentoffset)
        Log.d("Request", jsonRequest.toString())
        GifLoading.loading(activity, true)

        val apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.staffChatScreen(jsonRequest)!!
            .enqueue(object : retrofit2.Callback<StaffChatScreenResponse?> {
                override fun onResponse(
                    call: Call<StaffChatScreenResponse?>?,
                    response: Response<StaffChatScreenResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)

                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("Res:", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {

                                callback.callbackstafchatscreen(responseBody!!, currentoffset)

                            } else {
                                if (currentoffset == 0) {
                                    UtilConstants.customFailureAlert(
                                        activity,
                                        responseBody!!.message
                                    )
                                }

                            }
//                            if (responseBody?.status == 1) {
//                                if (offset == 0) {
//                                    limit=responseBody.data.limit.toInt()
//                                    if (limit > 0) {
//                                        chatCount=responseBody.data.chat_count
//                                        offset =responseBody.data.off_set.toInt()+1
//                                        StaffChatscreenDetails.clear()
//                                        StaffChatscreenDetails =
//                                            responseBody.data.chat_data as ArrayList<ChatData>
//                                        Adapterview()
//
//                                    }
//                                }
//                                else{
//                                    StaffChatscreenDetails =
//                                        responseBody.data.chat_data as ArrayList<ChatData>
//                                    Adapterview()
//                                    LastPage = currentoffset == totalPages - 1
//                                    offset =responseBody.data.off_set.toInt()+1
//                                    if (LastPage) Loading = true else Loading = false
//
//                                }
//                            } else {
//                                UtilConstants.customFailureAlert(activity, responseBody!!.message)
//
//                            }
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

                override fun onFailure(call: Call<StaffChatScreenResponse?>?, t: Throwable?) {
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    private fun jsonStaffchatscreen(activity: Activity?, offset: Int): JsonObject {
        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty("login_token", token)
        jsonObject.addProperty("mobile_number", mobileNumber)
        jsonObject.addProperty("school_id", UtilConstants.SchoolID)
        jsonObject.addProperty("staff_id", UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty("off_set", offset)
        jsonObjectRequest.addProperty("section_id", UtilConstants.chatsectionid)
        jsonObjectRequest.addProperty("subject_id", UtilConstants.chatsubjectid)
        jsonObjectRequest.addProperty(
            "is_class_teacher",
            UtilConstants.chatisclassteacher.toString()
        )
        jsonObject.add("request", jsonObjectRequest)
        return jsonObject

    }

    fun StaffAnswerChat(
        activity: Activity?,
        answer: String,
        replytype: String,
        questionid: String,
        changeAnswer: String,
        callback: GetStaffAnsChatCallBack
    ) {
        val jsonRequest = jsonStaffAnswerChat(activity, answer, replytype, questionid, changeAnswer)
        Log.d("Request", jsonRequest.toString())
        GifLoading.loading(activity, true)

        val apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.answerStudentQuestion(jsonRequest)!!
            .enqueue(object : retrofit2.Callback<StaffChatAnswerResponse?> {
                override fun onResponse(
                    call: Call<StaffChatAnswerResponse?>?,
                    response: Response<StaffChatAnswerResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)

                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("Res:", gson.toJson(response))
//                        lblContent.setText("")
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                callback.callbackstaffanschat(responseBody)
//                                val index: Int = StaffChatscreenDetails.indexOf(teacherChatAnswer)
//                                StaffChatscreenDetails.get(index).answered_on =responseBody.data.chat_data.get(0).answered_on
//                                StaffChatscreenDetails.get(index).change_answer = responseBody.data.chat_data.get(0).change_answer.toString()
//                                StaffChatscreenDetails.get(index).reply_type =responseBody.data.chat_data.get(0).reply_type
//                                StaffChatscreenDetails.get(index).question_id =responseBody.data.chat_data.get(0).question_id
//                                StaffChatscreenDetails.get(index).answer =responseBody.data.chat_data.get(0).answer

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

                override fun onFailure(call: Call<StaffChatAnswerResponse?>?, t: Throwable?) {
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    private fun jsonStaffAnswerChat(
        activity: Activity?,
        answer: String,
        replytype: String,
        questionid: String,
        changeAnswer: String
    ): JsonObject {
        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty("login_token", token)
        jsonObject.addProperty("mobile_number", mobileNumber)
        jsonObject.addProperty("school_id", UtilConstants.SchoolID)
        jsonObject.addProperty("staff_id", UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty("question_id", questionid)
        jsonObjectRequest.addProperty("answer", answer)
        jsonObjectRequest.addProperty("is_change_answer", changeAnswer)
        jsonObjectRequest.addProperty("reply_type", replytype)
        jsonObject.add("request", jsonObjectRequest)
        return jsonObject

    }


    fun getOverAllStrength(activity: Activity?) {
        val jsonManagementRequest = jsonManagementRequest(activity)

        Log.d("overallStrength", jsonManagementRequest.toString())
        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getOverAllStrength(jsonManagementRequest)!!
            .enqueue(object : retrofit2.Callback<GetOverallStrength?> {
                override fun onResponse(
                    call: Call<GetOverallStrength?>?,
                    response: Response<GetOverallStrength?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("CountManagement", gson.toJson(response))

                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {

//                                callBack.callBackCount(responseBody)

                                var TeacherCount = responseBody.data[0].total_staff_count
                                Log.d("TeacherCount", TeacherCount)
                                var StudentCount = responseBody.data[0].total_student_count
                                Log.d("StudentCount", StudentCount)

                                var gilrsCount = responseBody.data[0].student_girls_count
                                var boysCount = responseBody.data[0].student_boys_count
                                var unspecified = responseBody.data[0].student_unspecified_count
                                Util_shared_preferences.putStrengthDetails(
                                    activity,
                                    TeacherCount,
                                    StudentCount,
                                    gilrsCount,
                                    boysCount,
                                    unspecified
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
                                activity?.getString(R.string.Service_unavailable)
                            )
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }
                }

                override fun onFailure(call: Call<GetOverallStrength?>?, t: Throwable?) {
                    GifLoading.loading(activity, false)
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    fun getClassWiseStrength(activity: Activity?, callBack: GetClassWiseStrengthCallBack) {
        val jsonManagementRequest = jsonManagementRequest(activity)

        Log.d("classwiseStrength", jsonManagementRequest.toString())
        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getClassWiseStrength(jsonManagementRequest)!!
            .enqueue(object : retrofit2.Callback<GetClassWiseStrength?> {
                override fun onResponse(
                    call: Call<GetClassWiseStrength?>?,
                    response: Response<GetClassWiseStrength?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("ClassStrength:Res", gson.toJson(response))

                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {

                                callBack.callBackClassStrength(responseBody)
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

                override fun onFailure(call: Call<GetClassWiseStrength?>?, t: Throwable?) {
                    GifLoading.loading(activity, false)
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    fun getSectionWiseStrength(activity: Activity?, callBack: GetSectionWiseCallBack) {
        val jsonManagementRequest = jsonSectionStrenthRequest(activity)

        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getSectionWiseStrength(jsonManagementRequest)!!
            .enqueue(object : retrofit2.Callback<GetSectionWiseStrength?> {
                override fun onResponse(
                    call: Call<GetSectionWiseStrength?>?,
                    response: Response<GetSectionWiseStrength?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("SectionStrength:Res", gson.toJson(response))

                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {

                                callBack.callBackSectionStrength(responseBody)
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

                override fun onFailure(call: Call<GetSectionWiseStrength?>?, t: Throwable?) {
                    GifLoading.loading(activity, false)
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }


     fun getStaffForConference(activity: Activity?,callBack:ConferenceCallBack) {

         val jsonObject = jsonManagementRequest(activity)

         Log.d("Conference:Req", jsonObject.toString())
        GifLoading.loading(activity, true)

        var apiInterface: ApiInterface =
            APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getStaffForConferenceCall(jsonObject)!!
            .enqueue(object : retrofit2.Callback<ConferenceStaffResponse?> {
                override fun onResponse(
                    call: Call<ConferenceStaffResponse?>?,
                    response: Response<ConferenceStaffResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("ClassStrength:Res", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {

                                callBack.callBackConference(responseBody)
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

                override fun onFailure(call: Call<ConferenceStaffResponse?>?, t: Throwable?) {
                    GifLoading.loading(activity, false)
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    fun sendConfernceCall(activity: Activity?) {
        val jsonConferenceRequest = jsonConferenceRequest(activity)
        Log.d("ConferenceRequest", jsonConferenceRequest.toString())
        GifLoading.loading(activity, true)

        val apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.sendConferneceCall(jsonConferenceRequest)!!
            .enqueue(object : retrofit2.Callback<StatusMessageResponse?> {
                override fun onResponse(
                    call: Call<StatusMessageResponse?>?,
                    response: Response<StatusMessageResponse?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)

                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("ConferenceRequest-Res:", gson.toJson(response))
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                UtilConstants.EntireSchoolPopupWindow!!.dismiss()
                                UtilConstants.customSuccessAlert(activity, responseBody.message)
                            }
//                            else {
//                                UtilConstants.customFailureAlert(activity, responseBody!!.message)
//
//                            }
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

    private fun jsonConferenceRequest(activity: Activity?): JsonObject {
        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val token: String? = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()

        jsonObject.addProperty(LOGIN_TOKEN, token)
        jsonObject.addProperty(MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(SCHOOL_ID, UtilConstants.SchoolID)
        jsonObject.addProperty(STAFF_ID, UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        val jsonSchoolArray = JsonArray()

        UtilConstants.SelectedConferenceList.forEach {
            val jsonsIds = JsonObject()
            jsonsIds.addProperty(ID, it.staff_id)
            jsonSchoolArray.add(jsonsIds)
        }
        jsonObjectRequest.add(DIALING_ID, jsonSchoolArray)
        jsonObject.add(REQUEST, jsonObjectRequest)

        return jsonObject

    }

}


