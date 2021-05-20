package com.vsnapnewschool.voicesnapmessenger.UtilCommon

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.vsnapnewschool.voicesnapmessenger.Activities.CombinationHomeScreen
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.ChildDetailData
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.CountryData

class Util_shared_preferences {
    companion object {

        //SP NAMES
        var SP_NAME: String? = "SHARED_PREF_SP"
        var COUNTRY_SP: String? = "COUNTRY_SP"
        var COUNTRY_INFO: String? = "COUNTRY_INFO"

        //KEY NAMES
        var FirstTimeUser: String? = "FirstTimeUser"
        var CountryName: String? = "CountryName"
        var CountryBaseUrl: String? = "CountryBaseUrl"
        var CountryID: String? = "CountryID"
        var CountryCode: String? = "CountryCode"
        var CountryMobileNumberLength: String? = "CountryMobileNumberLength"
        var LoginToken: String? = "LoginToken"
        var ForgotPassword: String? = "ForgotPassword"
        var Mobileumber: String? = "Mobileumber"
        var Password: String? = "Password"

        var IsPrincipal: String? = "IsPrincipal"
        var IsStaff: String? = "IsStaff"
        var IsGroupHead: String? = "IsGroupHead"
        var IsAdmin: String? = "IsAdmin"
        var IsParent: String? = "IsParent"

        var MaxGeneralSMSCount: String? = "MaxGeneralSMSCount"
        var MaxHomeWorkSMSCount: String? = "MaxHomeWorkSMSCount"
        var MaxEmergencyVoiceDuration: String? = "MaxEmergencyVoiceDuration"
        var MaxGeneralVoiceDuration: String? = "MaxGeneralVoiceDuration"
        var MaxHomeWorkVoiceDuration: String? = "MaxHomeWorkVoiceDuration"

        var MakePayment: String? = "MakePayment"
        var RazorpayKeyID: String? = "RazorpayKeyID"
        var RazorpayApiKey: String? = "RazorpayApiKey"
        var ChildVisible: String? = "ChildVisible"
        var ChildMonthVisible: String? = "ChildMonthVisible"
        var userLoggedIn: String? = "userLoggedIn"
        var fingerCheckLoggedIn: String? = "fingerCheckLoggedIn"
        var MobileNumberScreen: String? = "MobileNumberScreen"
        var MemberType: String? = "MemberType"
        var ModuleType: String? = "ModuleType"
        var VoiceHistory: Boolean = false

        var StudentName: String? = "StudentName"
        var StudentSchoolID: String? = "StudentSchoolID"
        var StudentID: String? = "StudentID"
        var StudentClassID: String? = "StudentClassID"
        var StudentSectionID: String? = "StudentSectionID"
        var VoiceType: String? = "VoiceType"
        var TextCount: String? = "CountText"
        var VoiceCount: String? = "CountVoice"
        var ImageCount: String? = "CountImage"
        var PdfCount: String? = "CountPdf"
        var VideoCount: String? = "CountVideo"

        var Teacher: String? = "Teacher"
        var Student: String? = "Student"
        var Girls: String? = "Girls"
        var Boys: String? = "Boys"
        var Unspecified: String? = "Unspecified"
        var TabPosition: Int? = 0


        fun putChildInfo(activity: Activity?, child_info: ChildDetailData) {
            val sharedPreferences: SharedPreferences =
                activity!!.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(StudentName, child_info.child_name)
            editor.putString(StudentSchoolID, child_info.school_id)
            editor.putString(StudentID, child_info.child_id)
            editor.putString(StudentClassID, child_info.standard_id)
            editor.putString(StudentSectionID, child_info.section_id)
            editor.commit()
        }

        fun getStudentName(activity: Activity?): String? {
            val member = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                ?.getString(StudentName, "")
            return member
        }

        //XCXCXC
        fun getStudentID(activity: Activity?): String? {
            val member = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                ?.getString(StudentID, "")
            return member
        }

        fun getStudentSchoolID(activity: Activity?): String? {
            val member = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                ?.getString(StudentSchoolID, "")
            return member
        }

        fun getStudentClassID(activity: Activity?): String? {
            val member = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                ?.getString(StudentClassID, "")
            return member
        }

        fun getStudentSectionID(activity: Activity?): String? {
            val member = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                ?.getString(StudentSectionID, "")
            return member
        }


        fun putMemberType(context: Context?, type: String?) {
            val sharedPreferences: SharedPreferences =
                context!!.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(MemberType, type!!)
            editor.commit()
        }

        fun getMemberType(activity: Activity?): String? {
            val member = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                ?.getString(MemberType, "")
            return member
        }

        fun putModuleType(context: Context?, type: String?) {
            val sharedPreferences: SharedPreferences =
                context!!.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(ModuleType, type!!)
            editor.commit()
        }

        fun getModuleType(activity: Activity?): String? {
            val module = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                ?.getString(ModuleType, "")
            return module
        }


        fun putForgotPassword(context: Context?, type: Boolean?) {
            val sharedPreferences: SharedPreferences =
                context!!.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean(ForgotPassword, type!!)
            editor.commit()
        }

        fun getForgotPassword(activity: Activity?): Boolean? {
            val forgot = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                ?.getBoolean(ForgotPassword, false)
            return forgot
        }

        fun putFingerCheck(activity: Activity?, type: Boolean?) {
            val sharedPreferences: SharedPreferences =
                activity!!.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean(fingerCheckLoggedIn, type!!)
            editor.commit()
        }

        fun getFingerCheck(activity: Activity?): Boolean? {
            val name = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                ?.getBoolean(fingerCheckLoggedIn, false)
            return name
        }

        fun putUserLoggedIn(activity: Activity?, type: Boolean?) {
            val sharedPreferences: SharedPreferences =
                activity!!.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean(userLoggedIn, type!!)
            editor.commit()
        }

        fun getUserLoggedIn(activity: Activity?): Boolean? {
            val name = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                ?.getBoolean(userLoggedIn, false)
            return name
        }


        fun putUserMobileandPassword(activity: Activity?, number: String?, password: String?) {
            val sharedPreferences: SharedPreferences =
                activity!!.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(Mobileumber, number)
            editor.putString(Password, password)
            editor.commit()
        }

        fun putMobileNumber(activity: Activity?, type: String?) {
            val sharedPreferences: SharedPreferences =
                activity!!.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(Mobileumber, type)
            editor.commit()
        }

        fun getMobileNumber(activity: Activity?): String? {
            val number = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                ?.getString(Mobileumber, "")
            return number
        }

        fun getPassword(activity: Activity?): String? {
            val number = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                ?.getString(Password, "")
            return number
        }


        fun putExistingUser(activity: Activity?, type: Boolean?) {
            val sharedPreferences: SharedPreferences =
                activity!!.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean(FirstTimeUser, type!!)
            editor.commit()
        }

        fun getExistingUser(activity: Activity?): Boolean? {
            val name = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                ?.getBoolean(FirstTimeUser, false)
            return name
        }

        fun putPasswordScreen(activity: Activity?, type: Boolean?) {
            val sharedPreferences: SharedPreferences =
                activity!!.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean(MobileNumberScreen, type!!)
            editor.commit()
        }

        fun getPasswordScreen(activity: Activity?): Boolean? {
            val name = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                ?.getBoolean(MobileNumberScreen, false)
            return name
        }

        fun putCountryNameandUrl(activity: Activity?, countryDatas: CountryData) {
            val sharedPreferences: SharedPreferences =
                activity!!.getSharedPreferences(COUNTRY_SP, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(CountryName, countryDatas.country_name)
            editor.putString(CountryBaseUrl, countryDatas.base_url)
            editor.commit()
        }

        fun putCountryInformation(activity: Activity?, countryDatas: CountryData) {
            val sharedPreferences: SharedPreferences =
                activity!!.getSharedPreferences(COUNTRY_INFO, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putInt(CountryCode, countryDatas.country_code)
            editor.putInt(CountryID, countryDatas.id)
            editor.putInt(CountryMobileNumberLength, countryDatas.mobile_number_length)
            editor.commit()
        }


        fun getCountryBaseUrl(activity: Activity?): String? {
            val CountryBaseurl = activity?.getSharedPreferences(COUNTRY_SP, Context.MODE_PRIVATE)
                ?.getString(CountryBaseUrl, "")
            return CountryBaseurl
        }

        fun getCountryID(activity: Activity?): Int? {
            val CountryID = activity!!.getSharedPreferences(COUNTRY_INFO, Context.MODE_PRIVATE)
                .getInt(CountryID, 0)
            return CountryID
        }

        fun getCountryMobileLength(activity: Activity?): Int? {
            val MobileLength = activity?.getSharedPreferences(COUNTRY_INFO, Context.MODE_PRIVATE)
                ?.getInt(CountryMobileNumberLength, 0)
            return MobileLength
        }

        fun putLoginToken(activity: Activity?, token: String?) {
            val sharedPreferences: SharedPreferences =
                activity!!.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(LoginToken!!, token)
            editor.commit()
        }

        fun getLoginToken(activity: Activity?): String? {
            val token = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                ?.getString(LoginToken, "")
            return token
        }


        fun getIsPrincipal(activity: Activity?): Int? {
            val IsPrincipal = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                ?.getInt(IsPrincipal, 0)
            return IsPrincipal
        }

        fun getIsStaff(activity: Activity?): Int? {
            val IsStaff =
                activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)?.getInt(IsStaff, 0)
            return IsStaff
        }

        fun getIsGroupHead(activity: Activity?): Int? {
            val IsGH = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                ?.getInt(IsGroupHead, 0)
            return IsGH
        }

        fun getIsAdmin(activity: Activity?): Int? {
            val IsAdmin =
                activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)?.getInt(IsAdmin, 0)
            return IsAdmin
        }

        fun getIsParent(activity: Activity?): Int? {
            val IsParent =
                activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)?.getInt(IsParent, 0)
            return IsParent
        }


        @JvmStatic
        fun putPaymentDetails(
            activity: Activity?,
            makepayment: String?,
            razorpayKeyID: String?,
            razorpayApiKey: String?
        ) {
            val sharedPreferences: SharedPreferences =
                activity!!.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(MakePayment, makepayment)
            editor.putString(RazorpayKeyID, razorpayKeyID)
            editor.putString(RazorpayApiKey, razorpayApiKey)
            editor.commit()
        }

        @JvmStatic
        fun putChildMonthVisible(activity: Activity?, childVisible: String?) {
            val sharedPreferences: SharedPreferences =
                activity!!.getSharedPreferences(SP_NAME!!, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(ChildMonthVisible, childVisible)
            editor.commit()
        }

        @JvmStatic
        fun getChildFeeMonthVisible(activity: Activity?): String? {
            val childMonthVisible = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                ?.getString(ChildMonthVisible, "")
            return childMonthVisible
        }

        @JvmStatic
        fun putChildVisible(activity: Activity?, childVisible: String?) {
            val sharedPreferences: SharedPreferences =
                activity!!.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(ChildVisible, childVisible)
            editor.commit()
        }

        @JvmStatic
        fun getChildFeeVisible(activity: Activity?): String? {
            val childVisible = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                ?.getString(ChildVisible, "")
            return childVisible
        }

        @JvmStatic
        fun getMakePayment(activity: Activity?): String? {
            val paymentEnable = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                ?.getString(MakePayment, "")
            return paymentEnable
        }

        @JvmStatic
        fun getRazorPayKeyID(activity: Activity?): String? {
            val KeyID = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                ?.getString(RazorpayKeyID, "")
            return KeyID
        }

        @JvmStatic
        fun getRazorPayAPIKey(activity: Activity?): String? {
            val ApiKey = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                ?.getString(RazorpayApiKey, "")
            return ApiKey
        }

        fun putVoiceType(activity: Activity?, voiceType: Boolean) {
            val sharedPreferences: SharedPreferences =
                activity!!.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean(VoiceType, voiceType)

            editor.commit()
        }

        fun getVoiceType(activity: Activity?): Boolean? {
            val voicetype = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                ?.getBoolean(VoiceType, false)
            return voicetype
        }

        fun putTabposition(activity: Activity?, tabposition: Int) {
            val sharedPreferences: SharedPreferences =
                activity!!.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putInt("tab", tabposition)

            editor.commit()
        }

        fun getTabPosition(activity: Activity?): Int? {
            val position =
                activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)?.getInt("tab", 0)
            return position
        }

        fun putCountDetails(
            activity: Activity?,
            text: String?,
            voice: String?,
            image: String?,
            pdf: String?,
            video: String?
        ) {
            val sharedPreferences: SharedPreferences =
                activity!!.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(TextCount, text)
            editor.putString(VoiceCount, voice)
            editor.putString(ImageCount, image)
            editor.putString(PdfCount, pdf)
            editor.putString(VideoCount, video)
            editor.commit()
        }

        fun getTextCount(activity: Activity?): String? {
            val text = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)?.getString(
                TextCount, ""
            )
            return text
        }

        fun getVoiceCount(activity: Activity?): String? {
            val Voice = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)?.getString(
                VoiceCount, ""
            )
            return Voice
        }

        fun getImageCount(activity: Activity?): String? {
            val text = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)?.getString(
                ImageCount, ""
            )
            return text
        }

        fun getPdfCount(activity: Activity?): String? {
            val text = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)?.getString(
                PdfCount, ""
            )
            return text
        }

        fun getVideoCount(activity: Activity?): String? {
            val text = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)?.getString(
                VideoCount, ""
            )
            return text
        }


        fun putStrengthDetails(
            activity: Activity?,
            teacher: String?,
            student: String?,
            girls: String?,
            boys: String?,
            unspecified: String?
        ) {
            val sharedPreferences: SharedPreferences =
                activity!!.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(Teacher, teacher)
            editor.putString(Student, student)
            editor.putString(Girls, girls)
            editor.putString(Boys, boys)
            editor.putString(Unspecified, unspecified)
            editor.commit()
        }

        fun getTeacherCount(activity: Activity?): String? {
            val teacher = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)?.getString(
                Teacher, ""
            )
            return teacher
        }

        fun getStudentCount(activity: Activity?): String? {
            val student = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)?.getString(
                Student, ""
            )
            return student
        }

        fun getGirlsCount(activity: Activity?): String? {
            val girls = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)?.getString(
                Girls, ""
            )
            return girls
        }

        fun getBoysCount(activity: Activity?): String? {
            val boys = activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)?.getString(
                Boys, ""
            )
            return boys
        }

        fun getUnspecifiedCount(activity: Activity?): String? {
            val unspecific =
                activity?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)?.getString(
                    Unspecified, ""
                )
            return unspecific
        }
    }
}