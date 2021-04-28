@file:Suppress("DEPRECATION")

package com.vsnapnewschool.voicesnapmessenger.Network

import android.app.Activity
import android.provider.Settings
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Rest.APIClient
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GenerateOtpCallBack
import com.vsnapnewschool.voicesnapmessenger.CallBacks.ReturnGlobalValue
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.*
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.AWSUploadedFilesList
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.ChildListDetails
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
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.contentTypeImg
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.contentTypePdf

import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.selectedSubjectID
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
        jsonObject.addProperty("mobile_number", MobileNumber)
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
        jsonObject.addProperty("mobile_number", mobileNumber)
        jsonObject.addProperty("country_id", CountryID.toString())
        jsonObject.addProperty("otp_type", otpType)
        Log.d("Request", jsonObject.toString())
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


    fun validateOTP(activity: Activity?, MobileNumber: String?, OTP: String?) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("mobile_number", MobileNumber)
        jsonObject.addProperty("otp", OTP)
        Log.d("Request", jsonObject.toString())
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
        jsonObject.addProperty("mobile_number", MobileNumber)
        jsonObject.addProperty("new_password", password)
        Log.d("Request", jsonObject.toString())
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
        jsonObject.addProperty("mobile_number", MobileNumber)
        jsonObject.addProperty("new_password", password)
        jsonObject.addProperty("old_password", password)
        Log.d("Request", jsonObject.toString())
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
        jsonObject.addProperty("token", token)
        Log.d("Request", jsonObject.toString())
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
        jsonObject.addProperty("mobile_number", mobileNumber)
        Log.d("Request", jsonObject.toString())
        GifLoading.loading(activity, true)
        var apiInterface: ApiInterface =
            APIClient.getApiClient()!!.create(ApiInterface::class.java)
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
        jsonObject.addProperty("login_token", token)
        Log.d("Request", jsonObject.toString())
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
        jsonObject.addProperty("mobile_number", MobileNumber)
        jsonObject.addProperty("encrypted_password", Password)
        jsonObject.addProperty("device_type", "ANDROID")
        jsonObject.addProperty("imei_number", secureID)
        jsonObject.addProperty("secureid", secureID)
        Log.d("Request", jsonObject.toString())
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
        jsonObject.addProperty("login_token", LoginToken)
        jsonObject.addProperty("mobile_number", MobileNumber)
        jsonObject.addProperty("encrypted_password", Password)
        Log.d("Request", jsonObject.toString())
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
        jsonObject.addProperty("key_name", type)
        Log.d("Request", jsonObject.toString())
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

    fun updateDeviceToken(activity: Activity?) {
        val MobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty("mobile_number", MobileNumber)
        jsonObject.addProperty("device_token", "")
        Log.d("Request", jsonObject.toString())

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
        jsonObject.addProperty("login_token", token)
        jsonObject.addProperty("mobile_number", mobileNumber)
        jsonObject.addProperty("school_id", UtilConstants.SchoolID)
        jsonObject.addProperty("staff_id", UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        if (ScheduleType.equals("instant")) {
            jsonObjectRequest.addProperty("voice_type", ScheduleType)
            jsonObjectRequest.addProperty("schedule_date", "")
            jsonObjectRequest.addProperty("schedule_hour", "")
            jsonObjectRequest.addProperty("schedule_minute", "")
            jsonObjectRequest.addProperty("end_hour", "")
            jsonObjectRequest.addProperty("end_minute", "")
            jsonObjectRequest.addProperty("duration", UtilConstants.VoiceDuration)
            jsonObjectRequest.addProperty("description", UtilConstants.Title)
            jsonObject.add("request", jsonObjectRequest)
        } else if (ScheduleType.equals("schedule")) {
            jsonObjectRequest.addProperty("voice_type", ScheduleType)
            jsonObjectRequest.addProperty("schedule_date", UtilConstants.Date)
            jsonObjectRequest.addProperty("schedule_hour", UtilConstants.Hour)
            jsonObjectRequest.addProperty("schedule_minute", UtilConstants.Minute)
            jsonObjectRequest.addProperty("end_hour", UtilConstants.EndHour)
            jsonObjectRequest.addProperty("end_minute", UtilConstants.EndMinute)
            jsonObjectRequest.addProperty("duration", UtilConstants.VoiceDuration)
            jsonObjectRequest.addProperty("description", UtilConstants.Title)
            jsonObject.add("request", jsonObjectRequest)

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
        Log.d("testRequst", mobileNumber.toString())
        val jsonObject = JsonObject()
        jsonObject.addProperty("login_token", token)
        jsonObject.addProperty("mobile_number", mobileNumber)
        jsonObject.addProperty("school_id", UtilConstants.SchoolID)
        jsonObject.addProperty("staff_id", UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        if (ScheduleType.equals("instant")) {
            jsonObjectRequest.addProperty("voice_type", ScheduleType)
            jsonObjectRequest.addProperty("schedule_date", "")
            jsonObjectRequest.addProperty("schedule_hour", "")
            jsonObjectRequest.addProperty("schedule_minute", "")
            jsonObjectRequest.addProperty("end_hour", "")
            jsonObjectRequest.addProperty("end_minute", "")
            jsonObjectRequest.addProperty("duration", UtilConstants.VoiceDuration)
            jsonObjectRequest.addProperty("description", UtilConstants.Title)
        } else if (ScheduleType.equals("schedule")) {
            jsonObjectRequest.addProperty("voice_type", ScheduleType)
            jsonObjectRequest.addProperty("schedule_date", UtilConstants.Date)
            jsonObjectRequest.addProperty("schedule_hour", UtilConstants.Hour)
            jsonObjectRequest.addProperty("schedule_minute", UtilConstants.Minute)
            jsonObjectRequest.addProperty("end_hour", UtilConstants.EndHour)
            jsonObjectRequest.addProperty("end_minute", UtilConstants.EndMinute)
            jsonObjectRequest.addProperty("duration", UtilConstants.VoiceDuration)
            jsonObjectRequest.addProperty("description", UtilConstants.Title)

        }

        if (UtilConstants.RecipientsType == UtilConstants.StandardSection) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalSectionList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty("id", it.sectionID)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add("section_data", jsonSchoolArray)
            jsonObject.add("request", jsonObjectRequest)
        } else if (UtilConstants.RecipientsType == UtilConstants.Students) {
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
            Log.d("selectedStudentid", jsonSchoolArray.toString())
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

        }
        return jsonObject
    }


    fun sendEmergencyVoiceToSchools(activity: Activity?) {
        val file = File(UtilConstants.VoiceFilePath!!)
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val bodyFile = MultipartBody.Part.createFormData("voice", file.name, requestFile)
        val jsonEmergencyVoiceSchool = jsonEmergencyVoiceSchool(activity)
        val requestBody = RequestBody.create(
            MultipartBody.FORM,
            jsonEmergencyVoiceSchool.toString()
        )
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
        jsonObject.addProperty("login_token", token)
        jsonObject.addProperty("mobile_number", mobileNumber)
        jsonObject.addProperty("school_id", UtilConstants.StaffID)
        jsonObject.addProperty("staff_id", UtilConstants.SchoolID)
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty("description", UtilConstants.Title)
        jsonObjectRequest.addProperty("duration", UtilConstants.VoiceDuration)

        val jsonSchoolArray = JsonArray()
        UtilConstants.SelectedFinalSchoolsList.forEach {
            val jsonsIds = JsonObject()
            jsonsIds.addProperty("id", it.school_id)
            jsonSchoolArray.add(jsonsIds)
        }
        jsonObjectRequest.add("schools_id", jsonSchoolArray)
        jsonObject.add("request", jsonObjectRequest)

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
        jsonObject.addProperty("login_token", token)
        jsonObject.addProperty("mobile_number", mobileNumber)
        jsonObject.addProperty("school_id", UtilConstants.SchoolID)
        jsonObject.addProperty("staff_id", UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty("content", UtilConstants.Title)
        jsonObjectRequest.addProperty("description", UtilConstants.Description)
        jsonObject.add("request", jsonObjectRequest)
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
        jsonObject.addProperty("login_token", token)
        jsonObject.addProperty("mobile_number", mobileNumber)
        jsonObject.addProperty("school_id", UtilConstants.SchoolID)
        jsonObject.addProperty("staff_id", UtilConstants.StaffID)
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
        jsonObject.addProperty("login_token", token)
        jsonObject.addProperty("mobile_number", mobileNumber)
        jsonObject.addProperty("school_id", UtilConstants.SchoolID)
        jsonObject.addProperty("staff_id", UtilConstants.StaffID)
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
        jsonObject.add("request", jsonObjectRequest)
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
        jsonObject.addProperty("login_token", token)
        jsonObject.addProperty("mobile_number", mobileNumber)
        jsonObject.addProperty("school_id", UtilConstants.SchoolID)
        jsonObject.addProperty("staff_id", UtilConstants.StaffID)
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
        } else if (UtilConstants.RecipientsType == UtilConstants.Staff) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalStaffsList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty("id", it.staff_id)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add("staff_data", jsonSchoolArray)
            jsonObject.add("request", jsonObjectRequest)
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
        jsonObject.addProperty("login_token", token)
        jsonObject.addProperty("mobile_number", mobileNumber)
        jsonObject.addProperty("school_id", UtilConstants.SchoolID)
        jsonObject.addProperty("staff_id", UtilConstants.StaffID)
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
        jsonObject.add("request", jsonObjectRequest)
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
        jsonObject.addProperty("login_token", token)
        jsonObject.addProperty("mobile_number", mobileNumber)
        jsonObject.addProperty("school_id", UtilConstants.SchoolID)
        jsonObject.addProperty("staff_id", UtilConstants.StaffID)
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
                jsonsIds.addProperty("id", it.standard_id)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add("standard_data", jsonSchoolArray)
            jsonObject.add("request", jsonObjectRequest)

        } else if (UtilConstants.RecipientsType == UtilConstants.Students) {
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
        } else if (UtilConstants.RecipientsType == UtilConstants.Staff) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalStaffsList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty("id", it.staff_id)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add("staff_data", jsonSchoolArray)
            jsonObject.add("request", jsonObjectRequest)
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
        jsonObject.addProperty("login_token", token)
        jsonObject.addProperty("mobile_number", mobileNumber)
        jsonObject.addProperty("school_id", UtilConstants.SchoolID)
        jsonObject.addProperty("staff_id", UtilConstants.StaffID)
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
        jsonObject.add("request", jsonObjectRequest)
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
        jsonObject.addProperty("login_token", token)
        jsonObject.addProperty("mobile_number", mobileNumber)
        jsonObject.addProperty("school_id", UtilConstants.SchoolID)
        jsonObject.addProperty("staff_id", UtilConstants.StaffID)
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
                jsonsIds.addProperty("id", it.standard_id)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add("standard_data", jsonSchoolArray)
            jsonObject.add("request", jsonObjectRequest)

        } else if (UtilConstants.RecipientsType == UtilConstants.Students) {
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
        } else if (UtilConstants.RecipientsType == UtilConstants.Staff) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalStaffsList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty("id", it.staff_id)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add("staff_data", jsonSchoolArray)
            jsonObject.add("request", jsonObjectRequest)
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
        jsonObject.addProperty("login_token", token)
        jsonObject.addProperty("mobile_number", mobileNumber)
        jsonObject.addProperty("school_id", UtilConstants.SchoolID)
        jsonObject.addProperty("staff_id", UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty("sub_code", selectedSubjectID)
        jsonObjectRequest.addProperty("homework_text", UtilConstants.Title)
        jsonObjectRequest.addProperty("homework_topic", UtilConstants.Description)

        val jsonarray = JsonArray()
        UtilConstants.selectedSectionsListforSubjecject.forEach {
            val jsonsIds = JsonObject()
            jsonsIds.addProperty("id", it.section_id)
            jsonarray.add(jsonsIds)
        }
        jsonObjectRequest.add("section_data", jsonarray)
        if (MENU_TYPE == MENU_TEXT_HOMEWORK) {
            jsonObjectRequest.addProperty("homework_voice", "")
        } else if (MENU_TYPE == MENU_VOICE_HOMEWORK) {

            UtilConstants.AWSUploadedFilesList.forEach {
                jsonObjectRequest.addProperty("homework_voice", it.filepath)
            }
        }
        jsonObject.add("request", jsonObjectRequest)
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
        jsonObject.addProperty("login_token", token)
        jsonObject.addProperty("mobile_number", mobileNumber)
        jsonObject.addProperty("school_id", UtilConstants.SchoolID)
        jsonObject.addProperty("staff_id", UtilConstants.StaffID)
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
            if(it.contentype.equals(contentTypeImg)) {
                jsonsimgIds.addProperty("file_path", it.filepath)
                jsonsimgIds.addProperty("file_name", it.fileName)
                jsonimgarray.add(jsonsimgIds)
            }
            else if(it.contentype.equals(contentTypePdf)){
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
                    jsonsIds.addProperty("id", it.student_id)
                    jsonsIds.addProperty("student_array", sectionID)
                    jsonSchoolArray.add(jsonsIds)
                }
            }
            jsonObjectRequest.add("student_data", jsonSchoolArray)
            jsonObject.add("request", jsonObjectRequest)
        } else if (RecipientsType == UtilConstants.StandardSection) {
            Log.d("standardsection", RecipientsType.toString())
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalSectionList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty("id", it.sectionID)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add("section_array", jsonSchoolArray)
            jsonObject.add("request", jsonObjectRequest)
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
        jsonObject.addProperty("login_token", token)
        jsonObject.addProperty("mobile_number", mobileNumber)
        jsonObject.addProperty("school_id", UtilConstants.SchoolID)
        jsonObject.addProperty("staff_id", UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        if (ScheduleType.equals("instant")) {
            jsonObjectRequest.addProperty("voice_type", ScheduleType)
            jsonObjectRequest.addProperty("voice_file_path", UtilConstants.VoiceFilePath)
            jsonObjectRequest.addProperty("schedule_date", "")
            jsonObjectRequest.addProperty("schedule_hour", "")
            jsonObjectRequest.addProperty("schedule_minute", "")
            jsonObjectRequest.addProperty("end_hour", "")
            jsonObjectRequest.addProperty("end_minute", "")
            jsonObjectRequest.addProperty("duration", UtilConstants.VoiceDuration)
            jsonObjectRequest.addProperty("description", UtilConstants.Title)
            jsonObject.add("request", jsonObjectRequest)
        } else if (ScheduleType.equals("schedule")) {
            jsonObjectRequest.addProperty("voice_type", ScheduleType)
            jsonObjectRequest.addProperty("voice_file_path", UtilConstants.VoiceFilePath)
            jsonObjectRequest.addProperty("schedule_date", UtilConstants.Date)
            jsonObjectRequest.addProperty("schedule_hour", UtilConstants.Hour)
            jsonObjectRequest.addProperty("schedule_minute", UtilConstants.Minute)
            jsonObjectRequest.addProperty("end_hour", UtilConstants.EndHour)
            jsonObjectRequest.addProperty("end_minute", UtilConstants.EndMinute)
            jsonObjectRequest.addProperty("duration", UtilConstants.VoiceDuration)
            jsonObjectRequest.addProperty("description", UtilConstants.Title)
            jsonObject.add("request", jsonObjectRequest)

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
        jsonObject.addProperty("login_token", token)
        jsonObject.addProperty("mobile_number", mobileNumber)
        jsonObject.addProperty("school_id", UtilConstants.SchoolID)
        jsonObject.addProperty("staff_id", UtilConstants.StaffID)
        val jsonObjectRequest = JsonObject()
        if (ScheduleType.equals("instant")) {
            jsonObjectRequest.addProperty("voice_type", ScheduleType)
            jsonObjectRequest.addProperty("voice_file_path", UtilConstants.VoiceFilePath)
            jsonObjectRequest.addProperty("schedule_date", "")
            jsonObjectRequest.addProperty("schedule_hour", "")
            jsonObjectRequest.addProperty("schedule_minute", "")
            jsonObjectRequest.addProperty("end_hour", "")
            jsonObjectRequest.addProperty("end_minute", "")
            jsonObjectRequest.addProperty("duration", UtilConstants.VoiceDuration)
            jsonObjectRequest.addProperty("description", UtilConstants.Title)
            jsonObject.add("request", jsonObjectRequest)
        } else if (ScheduleType.equals("schedule")) {
            jsonObjectRequest.addProperty("voice_type", ScheduleType)
            jsonObjectRequest.addProperty("voice_file_path", UtilConstants.VoiceFilePath)
            jsonObjectRequest.addProperty("schedule_date", UtilConstants.Date)
            jsonObjectRequest.addProperty("schedule_hour", UtilConstants.Hour)
            jsonObjectRequest.addProperty("schedule_minute", UtilConstants.Minute)
            jsonObjectRequest.addProperty("end_hour", UtilConstants.EndHour)
            jsonObjectRequest.addProperty("end_minute", UtilConstants.EndMinute)
            jsonObjectRequest.addProperty("duration", UtilConstants.VoiceDuration)
            jsonObjectRequest.addProperty("description", UtilConstants.Title)
            jsonObject.add("request", jsonObjectRequest)

        }


        if (RecipientsType == UtilConstants.StandardSection) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalSectionList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty("id", it.sectionID)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add("section_data", jsonSchoolArray)
            jsonObject.add("request", jsonObjectRequest)
        } else if (RecipientsType == UtilConstants.Students) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalStudentList.forEach {
                val jsonsIds = JsonObject()
                val sectionID = it.sectionID
                it.studentData.forEach {
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
        } else if (RecipientsType == UtilConstants.Group) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalGroupsList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty("id", it.group_id)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add("group_data", jsonSchoolArray)
            jsonObject.add("request", jsonObjectRequest)
        } else if (RecipientsType == UtilConstants.Staff) {
            val jsonSchoolArray = JsonArray()
            UtilConstants.selectedFinalStaffsList.forEach {
                val jsonsIds = JsonObject()
                jsonsIds.addProperty("id", it.staff_id)
                jsonSchoolArray.add(jsonsIds)
            }
            jsonObjectRequest.add("staff_data", jsonSchoolArray)
            jsonObject.add("request", jsonObjectRequest)

        }
        return jsonObject

    }


}


