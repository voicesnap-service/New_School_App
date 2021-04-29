package com.vsnapnewschool.voicesnapmessenger.Network

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.*
import com.vsnapnewschool.voicesnapmessenger.payment.Model.FeeDetailsItems
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*


interface ApiInterface {


    //Payment

    @GET("institute-fee-rate/student-fee-details-app")
    fun getFeeDetails(
        @Query("ChildID") childid: String?,
        @Query("SchoolID") schollid: String?
    ): Call<FeeDetailsItems?>?

    @POST("institute-fee-rate/student-fee-payment-app")
    fun feePayment(@Body jsonObject: JsonObject?): Call<JsonArray?>?

    @POST("online-fee-payment-logs")
    fun feePaymentLogs(@Body jsonObject: JsonObject?): Call<JsonArray?>?

    @POST("{id}/transfers")
    fun transferToMultipleAccout(
        @Path("id") paymentID: String?, @Header("Content-Type") content_type: String?, @Header(
            "Authorization"
        ) secretKey: String?, @Body jsonobject: JsonObject?
    ): Call<JsonObject?>?


    @POST("{id}/refund")
    fun refundAmountToCustomer(
        @Path("id") paymentID: String?, @Header("Content-Type") content_type: String?, @Header(
            "Authorization"
        ) secretKey: String?, @Body jsonobject: JsonObject?
    ): Call<JsonObject?>?

    @POST("{id}/capture")
    fun changeCapturePayment(
        @Path("id") paymentID: String?, @Header("Content-Type") content_type: String?, @Header(
            "Authorization"
        ) secretKey: String?, @Body jsonobject: JsonObject?
    ): Call<JsonObject?>?

    @Streaming
    @GET
    fun downloadFileWithDynamicUrlAsync(@Url fileUrl: String?): Call<ResponseBody?>?

    @POST("launch/api/app_launcher/agree_terms_and_conditions")
    fun agreeTermsandConditions(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("launch/api/app_launcher/version_check")
    fun VersionCheck(@Body jsonObject: JsonObject?): Call<VersionCheckResponse?>?

    @POST("authenticate/api/validate_mobilenumber/check_if_mobile_number_exists")
    fun checkMobileNumberExist(@Body jsonObject: JsonObject?): Call<CheckMobileNumberResponse?>?

    @POST("authenticate/api/validate_mobilenumber/generate_new_otp")
    fun generateOTP(@Body jsonObject: JsonObject?): Call<generateOTPResponse?>?

    @POST("authenticate/api/validate_mobilenumber/validate_otp")
    fun validateOTP(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("authenticate/api/validate_mobilenumber/set_new_password_for_user")
    fun setNewPassword(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("authenticate/api/validate_mobilenumber/change_password")
    fun changePassword(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("launch/api/app_launcher/get_country_list")
    fun getCountryList(): Call<CountryListResponse?>?

    @POST("login/api/app_login/generate_new_login_token")
    fun generateNewLoginToken(@Body jsonObject: JsonObject?): Call<GenerateNewLoginTokenResponse?>?

    @POST("login/api/app_login/validate_login_token")
    fun validateLoginToken(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("login/api/app_login/get_login_details_by_token")
    fun getLoginDetailsByToken(@Body jsonObject: JsonObject?): Call<LoginResponse?>?

    @POST("login/api/app_login/update_logout_details")
    fun logoutfromSameDevice(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("login/api/app_login/update_logout_for_other_devices")
    fun logoutfromOtherDevice(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("authenticate/api/validate_mobilenumber/get_global_values")
    fun getGlobalValues(@Body jsonObject: JsonObject?): Call<GetGlobalValueResponse?>?

    @POST("launch/api/app_launcher/get_app_languages")
    fun getAppLanguages(): Call<LanguageResponse?>?

    @POST("launch/api/app_launcher/get_menu_list_by_country_id")
    fun getMenuListByCountry(@Body jsonObject: JsonObject?): Call<GetMenuList?>?

    @POST("authenticate/api/validate_mobilenumber/update_device_token")
    fun updateDeviceToken(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("lists/api/get_recipient/get_all_standards_by_schoolid")
    fun getAllStandardList(@Body jsonObject: JsonObject?): Call<AllStandardResponse?>?

    @POST("lists/api/get_recipient/get_all_groups_by_schoolid")
    fun getAllGroups(@Body jsonObject: JsonObject?): Call<AllGroupsResponse?>?

    @POST("lists/api/get_recipient/get_all_staffs_by_schoolid")
    fun getAllStaffs(@Body jsonObject: JsonObject?): Call<AllStaffsResponse?>?

    @POST("lists/api/get_recipient/get_all_standardwise_sections")
    fun getStandardSections(@Body jsonObject: JsonObject?): Call<StandardSectionsResponse?>?

    @POST("lists/api/get_recipient/get_all_students_by_sectionid")
    fun getAllStudents(@Body jsonObject: JsonObject?): Call<AllStudentsResponse?>?

    @POST("lists/api/get_recipient/get_all_subjects_by_sectionid")
    fun getSubjects(@Body jsonObject: JsonObject?): Call<GetAllSubjects?>?

    //Non-Emergency Voice
    @Multipart
    @POST("sendvoice/api/send_voice/send_non_emergency_voice_to_entire_school")
    fun sendNonEmergencyVoiceToEntireSchool(
        @Part("info") requestBody: RequestBody?,
        @Part file: MultipartBody.Part?
    ): Call<StatusMessageResponse?>?

    @Multipart
    @POST("sendvoice/api/send_voice/send_non_emergency_voice_to_standards")
    fun sendNonEmergencyVoiceToStandards(
        @Part("info") requestBody: RequestBody?,
        @Part file: MultipartBody.Part?
    ): Call<StatusMessageResponse?>?

    @Multipart
    @POST("sendvoice/api/send_voice/send_non_emergency_voice_to_sections")
    fun sendNonEmergencyVoiceToSections(
        @Part("info") requestBody: RequestBody?,
        @Part file: MultipartBody.Part?
    ): Call<StatusMessageResponse?>?

    @Multipart
    @POST("sendvoice/api/send_voice/send_non_emergency_voice_to_groups")
    fun sendNonEmergencyVoiceToGroups(
        @Part("info") requestBody: RequestBody?,
        @Part file: MultipartBody.Part?
    ): Call<StatusMessageResponse?>?


    @Multipart
    @POST("sendvoice/api/send_voice/send_non_emergency_voice_to_students")
    fun sendNonEmergencyVoiceToStudents(
        @Part("info") requestBody: RequestBody?,
        @Part file: MultipartBody.Part?
    ): Call<StatusMessageResponse?>?


    @Multipart
    @POST("sendvoice/api/send_voice/send_non_emergency_voice_to_staffs")
    fun sendNonEmergencyVoiceToStaffs(
        @Part("info") requestBody: RequestBody?,
        @Part file: MultipartBody.Part?
    ): Call<StatusMessageResponse?>?


    @Multipart
    @POST("sendvoice/api/send_voice/send_emergency_voice_to_schools")
    fun sendEmergencyVoiceToSchools(
        @Part("info") requestBody: RequestBody?,
        @Part file: MultipartBody.Part?
    ): Call<StatusMessageResponse?>?


    //TextApi

    @POST("sendtext/api/send_text/send_text_to_entire_school")
    fun sendTextToEntireSchool(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("sendtext/api/send_text/send_text_to_groups")
    fun sendTextToGroups(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("sendtext/api/send_text/send_text_to_staffs")
    fun sendTextToStaff(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("sendtext/api/send_text/send_text_to_standards")
    fun sendTextToStandards(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("sendtext/api/send_text/send_text_to_sections")
    fun sendTextToSections(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("sendtext/api/send_text/send_text_to_students")
    fun sendTextToStudents(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    //Events

    @POST("sendevent/api/send_event/send_event_to_entire_school")
    fun sendEventToEntireSchool(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("sendevent/api/send_event/send_event_to_standards")
    fun sendEventToStandard(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("sendevent/api/send_event/send_event_to_students")
    fun sendEventToStudents(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("sendevent/api/send_event/send_event_to_staffs")
    fun sendEventToStaff(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("sendevent/api/send_event/send_event_to_sections")
    fun sendEventToSections(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("sendevent/api/send_event/send_event_to_groups")
    fun sendEventToGroups(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?


    //IMAGES

    @POST("/sendfile/api/send_file/send_file_to_entire_school")
    fun sendFilesToEntireSchool(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("/sendfile/api/send_file/send_file_to_standards")
    fun sendFilesToStandard(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("/sendfile/api/send_file/send_file_to_sections")
    fun sendFilesToSection(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("/sendfile/api/send_file/send_file_to_students")
    fun sendFilesToStudents(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("/sendfile/api/send_file/send_file_to_groups")
    fun sendFilesToGroups(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("/sendfile/api/send_file/send_file_to_staffs")
    fun sendFilesToStaff(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    //NoticeBoard
    @POST("/sendnotice/api/send_notice/send_notice_to_entire_school")
    fun sendNoticeToEntireSchool(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("/sendnotice/api/send_notice/send_notice_to_standards")
    fun sendNoticeToStandard(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("/sendnotice/api/send_notice/send_notice_to_sections")
    fun sendNoticeToSection(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("/sendnotice/api/send_notice/send_notice_to_groups")
    fun sendNoticeToStudents(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("/sendnotice/api/send_notice/send_notice_to_students")
    fun sendNoticeToGroups(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("/sendnotice/api/send_notice/send_notice_to_staffs")
    fun sendNoticeToStaff(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?


    //HomeWork
    @POST("/sendhomework/api/send_homework/send_homework")
    fun sendHomework(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    //Assignment
    @POST("/sendassignment/api/send_assignment/send_assignment_to_sections")
    fun sendAssignmentToSections(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("/sendassignment/api/send_assignment/send_assignment_to_students")
    fun sendAssignmentToStudents(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("/sendassignment/api/send_assignment/delete_assignment")
    fun DeleteAssignment(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

//    @POST("/sendassignment/api/send_assignment/delete_assignment")
//    fun DeleteAssignment(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?


    //SendVoiceHistory
    @POST("/sendvoice/api/send_voice/send_non_emergency_voice_to_entire_school_from_history")
    fun sendNonEmergencyVoiceToEntireSchoolFromHistory(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("/sendvoice/api/send_voice/send_non_emergency_voice_to_standards_from_history")
    fun SendNonEmergencyVoicetoStandardFromhistory(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>

    @POST("/sendvoice/api/send_voice/send_non_emergency_voice_to_sections_from_history")
    fun SendNonEmergencyVoicetoSectionsFromhistory(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("/sendvoice/api/send_voice/send_non_emergency_voice_to_students_from_history")
    fun SendNonEmergencyVoiceToStudentsFromhistory(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("/sendvoice/api/send_voice/send_non_emergency_voice_to_staffs_from_history")
    fun SendNonEmergencyVoiceToStaffFromhistory(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("/sendvoice/api/send_voice/send_non_emergency_voice_to_groups_from_history")
    fun SendNonEmergencyVoiceToGroupFromhistory(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST("/sendvoice/api/send_voice/send_emergency_voice_to_schools_from_history")
    fun SendEmergencyVoiceToSchoolFromhistory(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    //VoiceHistoryListApi
    @POST("/sendhistory/api/send_history/staff_get_voice_history")
    fun GetVoiceHistory(@Body jsonObject: JsonObject?): Call<GetVoiceHistory?>?

//<<<<<<< HEAD
    //LeaveRequestList

    @POST("/sendleave/api/leave/staff_get_student_leave")
    fun StaffApproveLeavelist(@Body jsonObject: JsonObject?): Call<StaffApproveLeave?>?

    @POST("/sendleave/api/leave/staff_update_leave_status")
    fun StaffApproveLeaveStatusUpdate(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

//
//=======
//>>>>>>> f3d2e822fd1d1eb38f24910f8fcc33efc5eda1e0
    //Parent
    @POST("/receivercommunication/api/receiver/get_text_messages")
    fun getTextMessages(@Body jsonObject: JsonObject?): Call<GetTextMessages?>?

    @POST("/receivercommunication/api/receiver/get_text_messages_archive")
    fun getTextMessages_Archive(@Body jsonObject: JsonObject?): Call<GetTextMessages?>?

}