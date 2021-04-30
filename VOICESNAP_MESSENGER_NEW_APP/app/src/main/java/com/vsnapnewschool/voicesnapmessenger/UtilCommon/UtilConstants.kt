package com.vsnapnewschool.voicesnapmessenger.UtilCommon

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Activities.*
import com.vsnapnewschool.voicesnapmessenger.Adapters.ForgotDialNumbers
import com.vsnapnewschool.voicesnapmessenger.Models.*
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.R.layout.*
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.*
import com.vsnapnewschool.voicesnapmessenger.albumImage.AlbumSelectActivity
import java.io.File
import java.util.*
import kotlin.system.exitProcess


@Suppress("DEPRECATION")
class UtilConstants {
    companion object {


        // School Menus
        var MENU_EMERGENCY: Int? = 100
        var MENU_VOICE: Int? = 101
        var MENU_TEXT: Int? = 102
        var MENU_VOICE_HOMEWORK: Int? = 103
        var MENU_TEXT_HOMEWORK: Int? = 104
        var MENU_ATTEDANCE_MARKING: Int? = 105
        var MENU_ABSENTEES_REPORT: Int? = 106
        var MENU_IMGAE_PDF: Int? = 107
        var MENU_SCHEDULE_EXAM: Int? = 108
        var MENU_NOTICEBOARD: Int? = 109
        var MENU_EVENTS: Int? = 110
        var MENU_SCHOOL_STRENGTH: Int? = 111
        var MENU_FEEDBACK: Int? = 112
        var MENU_IMPORTANT_INFO: Int? = 113
        var MENU_ASSIGNMENT: Int? = 114
        var MENU_VIDEO: Int? = 115
        var MENU_MESSAGES_FROM_MANAGEMENT: Int? = 116
        var MENU_OTHER_SERVICES: Int? = 117
        var MENU_INTRACTION_WITH_STUDENT: Int? = 118
        var MENU_LEAVE_REQUESTS: Int? = 119
        var MENU_ONLINE_TEXT_BOOK: Int? = 120
        var MENU_PDF_UPLOAD: Int? = 121

        //Parent menus

        var PARENT_MENU_VOICE: Int? = 200
        var PARENT_MENU_TEXT: Int? = 201
        var PARENT_MENU_HOMEWORK: Int? = 202
        var PARENT_MENU_EXAM_TEST: Int? = 203
        var PARENT_MENU_EXAM_MARKS: Int? = 204
        var PARENT_MENU_CIRCULARS: Int? = 205
        var PARENT_MENU_NOTICEBOARD: Int? = 206
        var PARENT_MENU_EVENTS: Int? = 207
        var PARENT_MENU_ATTEDANCE_REPORT: Int? = 208
        var PARENT_MENU_REQUEST_LEAVE: Int? = 209
        var PARENT_MENU_IMAGES: Int? = 210
        var PARENT_MENU_FEE_DETAILS: Int? = 211
        var PARENT_MENU_INTRACTION_WITH_STAFF: Int? = 212
        var PARENT_MENU_ASSIGNMENT: Int? = 213
        var PARENT_MENU_VIDEO: Int? = 214
        var PARENT_MENU_ONLINE_TEXT_BOOK: Int? = 215
        var PARENT_MENU_LIBRARY: Int? = 216
        var MENU_TYPE: Int? = 0
        var PARENT_MENU_TYPE: Int? = 0


        var SchoolListDetails = ArrayList<StaffDetailData>()
        var ChildListDetails = ArrayList<ChildDetailData>()
        var IsPrincipal: Int? = 0
        var IsStaff: Int? = 0
        var IsGroupHead: Int? = 0
        var IsAdmin: Int? = 0
        var IsParent: Int? = 0
        var MaxGeneralSMSCount: Int? = 0
        var MaxHomeWorkSMSCount: Int? = 0
        var MaxEmergencyVoiceDuration: Int? = 0
        var MaxGeneralVoiceDuration: Int? = 0
        var MaxHomeWorkVoiceDuration: Int? = 0

        var StaffID: String? = null
        var SchoolID: String? = null
        var selectedFinalSectionList = ArrayList<SelecetedPreviewSection>()
        var selectedFinalStudentList = ArrayList<SelectedPreviewStudent>()
        var selectedFinalStandardList = ArrayList<AllStandardData>()
        var selectedFinalGroupsList = ArrayList<AllGroupsData>()
        var selectedFinalStaffsList = ArrayList<AllStaffsData>()
        var SelectedFinalSchoolsList = ArrayList<StaffDetailData>()
        var isSelectAll: Boolean? = false
        var AWSUploadedFilesList = ArrayList<AWSUploadedFiles>()
        var fileName: File? = null
        var filename: String? = null
        var SelcetedFileList = ArrayList<String>()
        var OTPScreenType: String? = null
        var RecipientsType: Int? = 0
        var Standard: Int? = 1
        var Group: Int? = 2
        var Staff: Int? = 3
        var StandardSection: Int? = 4
        var Students: Int? = 5
        var EntireSchool: Int? = 6
        var VoiceFilePath: String? = null
        var VoiceDuration: String? = null

        var uploadFilePath = ""
        var VideoFilePath: String? = null
        var Title: String? = null
        var Description: String? = null
        var Date: String? = ""
        var Hour: String? = ""
        var Minute: String? = ""
        var EndDate: String? = ""
        var EndHour: String? = ""
        var EndMinute: String? = ""
        var EventTime: String? = ""
        var ScheduleType: String? = null
        var SucesspopupWindow: PopupWindow? = null
        var EntireSchoolPopupWindow: PopupWindow? = null
        var Apifiletype: String? = ""
        var contentType: String? = null
        var contentTypePdf: String? = "application/pdf"
        var contentTypeImg: String? = "image/png"
        var contentTypeVoice: String? = ".mp3"
        var filetype: String? = "image"
        var filetypePdf: String? = "pdf"
        var filetypeVideo: String? = "video"

        var API_SEE_MORE: String? = "SEE_MORE"
        var API_NORMAL: String? = "NORMAL"
        var CLICKED_SEE_MORE: Boolean? = false

        var BottomMenuHome:Boolean?=true
        var extension: String? = null
        var selectedSubjectID: String? = null
        var selectedSubjectName: String? = null
        var SelectedStandardID: String? = null
        var SelectedSectionsForSubjects: String? = null
        var selectedSectionsListforSubjecject = ArrayList<Section>()
         var voiceHistoryList = ArrayList<VoiceHistoryData>()
         var ApproveLeaveList = ArrayList<ApproveLeaveData>()
        var ApproveLeaveTypeStatus: String? = null
        var ApproveLeaveId: String? = null

        var VoiceHeaderId:String?=null
        var VoiceHistoryFile:String?=null
        var VoiceHistorydescription:String?=null
        var VoiceHistorycreatedOn:String?=null
        var VoiceHistoryVoicefilepath:String?=null
        var VoiceHistoryFilename:String?=null

        internal lateinit var dialNumbersAdapter: ForgotDialNumbers

        fun customToast(activity: Activity?, linear: LinearLayout?) {
            val layout = activity?.layoutInflater?.inflate(custom_toast, linear)
            val myToast = Toast(activity)
            myToast.duration = Toast.LENGTH_SHORT
            myToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
            myToast.view = layout//setting the view of custom toast layout
            myToast.show()
        }

        fun normalToast(activity: Activity?, msg: String?) {
            Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
        }

        fun textBold(activity: Activity?, textView: TextView?) {
            textView?.setTypeface(textView.getTypeface(), Typeface.BOLD)
        }


        fun textBoldContext(activity: Context?, textView: TextView?) {
            textView?.setTypeface(textView.getTypeface(), Typeface.BOLD)
        }

        fun customSuccessAlert(activity: Activity?, message: String?) {
            val inflater: LayoutInflater =
                activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view: View = inflater.inflate(custom_alert_sucess, null)
            SucesspopupWindow = PopupWindow(
                view,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                true
            )
            SucesspopupWindow!!.setContentView(view)
            SucesspopupWindow!!.setTouchable(true)
            SucesspopupWindow!!.setFocusable(false)
            SucesspopupWindow!!.setOutsideTouchable(false)

            val lblClose = view.findViewById<TextView>(R.id.lblClose)
            val lblMessage = view.findViewById<TextView>(R.id.lblMessage)
            lblMessage.setText(message)

            lblClose.setOnClickListener {
                SucesspopupWindow!!.dismiss()
                val intent = Intent(activity, SchoolHomeScreen::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                activity?.startActivity(intent)
            }
            SucesspopupWindow!!.showAtLocation(view, Gravity.CENTER, 0, 0)
        }

        fun customFailureAlert(activity: Activity?, msg: String?) {
            val inflater: LayoutInflater =
                activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view: View = inflater.inflate(custom_alert_failure, null)
            val popupWindow = PopupWindow(
                view,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true
            )
            popupWindow.setContentView(view)
            popupWindow.setTouchable(true)
            popupWindow.setFocusable(false)
            popupWindow.setOutsideTouchable(false)

            val lblClose = view.findViewById<TextView>(R.id.lblClose)
            val lblMessage = view.findViewById<TextView>(R.id.lblMessage)
            lblMessage.setText(msg)
            lblClose.setOnClickListener {
                popupWindow.dismiss()
            }
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        }

        fun parentCustomFailureAlert(activity: Activity?, message: String, ApiType: String?) {
            val inflater: LayoutInflater =
                activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view: View = inflater.inflate(custom_alert_failure, null)
            val popupWindow = PopupWindow(
                view,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true
            )
            popupWindow.setContentView(view)
            popupWindow.setTouchable(true)
            popupWindow.setFocusable(false)
            popupWindow.setOutsideTouchable(false)

            val lblClose = view.findViewById<TextView>(R.id.lblClose)
            val lblMessage = view.findViewById<TextView>(R.id.lblMessage)
            lblMessage.setText(message)
            lblClose.setOnClickListener {
                if(ApiType.equals(API_NORMAL)){
                    popupWindow.dismiss()
                }
                else{
                    popupWindow.dismiss()
                    //activity.finish()
                }

            }
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        }

        fun LogoutOtherDeviceAlert(
            activity: Activity?,
            mobileNumber: String?,
            password: String?,
            message: String?
        ) {
            val inflater: LayoutInflater =
                activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view: View = inflater.inflate(custom_alert_failure, null)
            val popupWindow = PopupWindow(
                view,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true
            )
            popupWindow.setContentView(view)
            popupWindow.setTouchable(true)
            popupWindow.setFocusable(false)
            popupWindow.setOutsideTouchable(false)

            val lblClose = view.findViewById<TextView>(R.id.lblClose)
            val lblMessage = view.findViewById<TextView>(R.id.lblMessage)
            lblMessage.setText(message)
            lblClose.setOnClickListener {
                popupWindow.dismiss()
                SchoolAPIServices.logoutfromOtherDevice(activity, mobileNumber, password)
            }
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        }

        fun showEntireSchoolConfirmationAlert(activity: Activity?) {
            val inflater: LayoutInflater =
                activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view: View = inflater.inflate(confirmation_alert, null)
            EntireSchoolPopupWindow = PopupWindow(
                view,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                true
            )
            EntireSchoolPopupWindow!!.setContentView(view)
            EntireSchoolPopupWindow!!.setTouchable(true)
            EntireSchoolPopupWindow!!.setFocusable(false)
            EntireSchoolPopupWindow!!.setOutsideTouchable(false)

            val lblCancel = view.findViewById<TextView>(R.id.lblCancel)
            val lblOk = view.findViewById<TextView>(R.id.lblOk)
            val lblMessage = view.findViewById<TextView>(R.id.lblMessage)
            lblCancel.setOnClickListener {
                EntireSchoolPopupWindow!!.dismiss()
            }

            lblOk.setOnClickListener {
                EntireSchoolPopupWindow!!.dismiss()
                entireSchoolApi(activity)

            }
            EntireSchoolPopupWindow!!.animationStyle = R.style.AnimationPopup_fade
            EntireSchoolPopupWindow!!.showAtLocation(view, Gravity.CENTER, 0, 0)
        }

        private fun entireSchoolApi(activity: Activity) {
            if (MENU_TYPE == MENU_TEXT) {
                SchoolAPIServices.sendTextToEntireSchool(activity)
            } else if (MENU_TYPE == MENU_VOICE) {
                SchoolAPIServices.sendNonEmergencyVoiceToEntireSchool(activity)
            } else if ((MENU_TYPE == MENU_EVENTS) || (MENU_TYPE == MENU_IMGAE_PDF) || (MENU_TYPE == MENU_PDF_UPLOAD) || (MENU_TYPE == MENU_NOTICEBOARD)) {
                val baseActivity: BaseActivity
                baseActivity = BaseActivity()
                baseActivity?.awsFileUpload(activity, 0)
            }
        }

        fun forgotDialNumberPopup(activity: Activity?, otpData: OtpData) {
            val inflater: LayoutInflater =
                activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view: View = inflater.inflate(forgot_dial_popup, null)
            val popupWindow = PopupWindow(
                view,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                true
            )

            popupWindow.setContentView(view)
            popupWindow.setTouchable(true)
            popupWindow.setFocusable(true)
            popupWindow.setOutsideTouchable(false)
            val lblClickHere = view.findViewById<TextView>(R.id.lblClickHere)
            val lblMissedCallFrom = view.findViewById<TextView>(R.id.lblMissedCallFrom)
            val lblCancel = view.findViewById<TextView>(R.id.lblCancel)
            val recycleNumbers = view.findViewById<RecyclerView>(R.id.recycleNumbers)

            val txtEdit1 = view.findViewById<EditText>(R.id.txtEdit1)
            val txtEdit2 = view.findViewById<EditText>(R.id.txtEdit2)
            val txtEdit3 = view.findViewById<EditText>(R.id.txtEdit3)
            val txtEdit4 = view.findViewById<EditText>(R.id.txtEdit4)

            val imgNext = view.findViewById<ImageView>(R.id.imgNext)
            textWatcher(activity, txtEdit1, txtEdit2, txtEdit3, txtEdit4)

            lblClickHere.setText(otpData.more_info)
            lblMissedCallFrom.setText(otpData.otp_message)
            val dialNumber: Array<String> = otpData.dial_numbers.split(",".toRegex()).toTypedArray()

            dialNumbersAdapter = ForgotDialNumbers(dialNumber, activity, popupWindow)
            val mLayoutManager = LinearLayoutManager(activity)
            recycleNumbers.layoutManager = mLayoutManager
            recycleNumbers.itemAnimator = DefaultItemAnimator()
            recycleNumbers.adapter = dialNumbersAdapter
            dialNumbersAdapter.notifyDataSetChanged()

            lblClickHere.setOnClickListener {
                //openForgotPasswordScreen(activity)
            }

            imgNext.setOnClickListener {
                val MobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
                val otp: String? =
                    txtEdit1.text.toString() + txtEdit2.text.toString() + txtEdit3.text.toString() + txtEdit4.text.toString()
                if (!otp!!.isEmpty()) {
                    popupWindow.dismiss()
                    SchoolAPIServices.validateOTP(activity, MobileNumber, otp)
                } else {
                    normalToast(activity, "Enter the OTP")
                }

            }

            lblCancel.setOnClickListener {
                popupWindow.dismiss()
            }
            popupWindow.animationStyle = R.style.AnimationPopup
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        }

        fun viewMessagePopup(activity: Activity?, text_info: GetTextData) {
            val inflater: LayoutInflater =
                activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view: View = inflater.inflate(message_view_popup, null)
            val popupWindow = PopupWindow(
                view,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                true
            )

            popupWindow.setContentView(view)
            popupWindow.setTouchable(true)
            popupWindow.setFocusable(true)
            popupWindow.setOutsideTouchable(false)
            val lblMessage = view.findViewById<TextView>(R.id.lblMessage)
            val lblCreatedBy = view.findViewById<TextView>(R.id.lblCreatedBy)
            val imgClose = view.findViewById<ImageView>(R.id.imgClose)

            lblCreatedBy.setText("Created By  :  "+ text_info.created_by)
            lblMessage.setText(text_info.content)
            imgClose.setOnClickListener {
                popupWindow.dismiss()
            }
            popupWindow.animationStyle = R.style.AnimationPopup
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        }

        fun tokenExpiredAlert(activity: Activity?, message: String?) {
            val inflater: LayoutInflater =
                activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view: View = inflater.inflate(custom_alert_failure, null)
            val popupWindow = PopupWindow(
                view,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true
            )
            popupWindow.setContentView(view)
            popupWindow.setTouchable(true)
            popupWindow.setFocusable(false)
            popupWindow.setOutsideTouchable(false)

            val lblClose = view.findViewById<TextView>(R.id.lblClose)
            val lblMessage = view.findViewById<TextView>(R.id.lblMessage)
            lblMessage.setText(message)
            lblClose.setOnClickListener {
                popupWindow.dismiss()
                Util_shared_preferences.putLoginToken(activity, "")
                Util_shared_preferences.putFingerCheck(activity, false)
                openSignInScreen(activity)

            }
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        }

        fun exitApplicationAlert(activity: Activity, type: String) {

            val inflater: LayoutInflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view: View = inflater.inflate(exit_application_alert, null)
            val popupWindow = PopupWindow(
                view,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true
            )
            popupWindow.setContentView(view)
            popupWindow.setTouchable(true)
            popupWindow.setFocusable(false)
            popupWindow.setOutsideTouchable(false)

            val lblClose = view.findViewById<TextView>(R.id.lblClose)
            val lblCancel = view.findViewById<TextView>(R.id.lblCancel)
            val lblMessage = view.findViewById<TextView>(R.id.lblMessage)

            lblMessage.setText(activity.getString(R.string.exit_app))
            lblClose.setOnClickListener {

                if(type.equals("Logout")){
                    Util_shared_preferences.putUserLoggedIn(activity, false)
                    SchoolAPIServices.logoutFromSameDevice(activity)
                }
             else {
                    popupWindow.dismiss()
                    activity.moveTaskToBack(true);
                    exitProcess(-1)
                }

            }
            lblCancel.setOnClickListener {
                popupWindow.dismiss()
            }
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        }


        fun textWatcher(
            activity: Activity?,
            lblMpin1: TextView?,
            lblMpin2: TextView?,
            lblMpin3: TextView?,
            lblMpin4: TextView?
        ) {
            lblMpin1?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    if (lblMpin1.length() === 1) {
                        lblMpin1.requestFocus()
                    }
                    if (lblMpin4?.length() === 1 && lblMpin3?.length() === 1 && lblMpin2?.length() === 1 && lblMpin1?.length() === 0) {
                        lblMpin1.setCursorVisible(true)
                        lblMpin1.requestFocus()
                    }
                }

                override fun afterTextChanged(editable: Editable) {
                    if (lblMpin1?.length() === 1) {
                        lblMpin2?.requestFocus()
                        lblMpin2?.setCursorVisible(true)
                    }
                }
            })
            lblMpin2?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    if (lblMpin2.length() === 1) {
                        lblMpin2.requestFocus()
                    }
                    if (lblMpin4?.length() === 1 && lblMpin3?.length() === 1 && lblMpin2.length() === 0 && lblMpin1?.length() === 1) {
                        lblMpin2.setCursorVisible(true)
                        lblMpin2.requestFocus()
                    }
                    if (lblMpin2.length() === 0) {
                        lblMpin1?.setCursorVisible(true)
                        lblMpin1?.requestFocus()
                    }
                }

                override fun afterTextChanged(editable: Editable) {
                    if (lblMpin2.length() === 1) {
                        lblMpin3?.requestFocus()
                        lblMpin3?.setCursorVisible(true)
                    }
                }
            })
            lblMpin3?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    if (lblMpin3.length() === 1) {
                        lblMpin3.requestFocus()
                    }
                    if (lblMpin3.length() === 0) {
                        lblMpin2?.setCursorVisible(true)
                        lblMpin2?.requestFocus()
                    }
                    if (lblMpin4?.length() === 1 && lblMpin3.length() === 0 && lblMpin2?.length() === 1 && lblMpin1?.length() === 1) {
                        lblMpin3.setCursorVisible(true)
                        lblMpin3.requestFocus()
                    }
                }

                override fun afterTextChanged(editable: Editable) {
                    if (lblMpin3.length() === 1) {
                        lblMpin4?.requestFocus()
                        lblMpin4?.setCursorVisible(true)
                    }
                }
            })
            lblMpin4?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    if (lblMpin4?.length() === 1) {
                        lblMpin4.requestFocus()
                    }
                    if (lblMpin4?.length() === 0) {
                        lblMpin3?.setCursorVisible(true)
                        lblMpin3?.requestFocus()
                    }
                }

                override fun afterTextChanged(editable: Editable) {
                    if (lblMpin4?.length() === 1) {
                        lblMpin4.requestFocus()
                    }
                }
            })
        }

        fun handleErrorResponse(
            activity: Activity?,
            errorCode: Int?,
            errorResponseBody: StatusMessageResponse?
        ) {
            if (errorCode == 400) {
                if (errorResponseBody?.status == 0) {
                    customFailureAlert(activity, errorResponseBody.message)
                } else if (errorResponseBody?.status == 2) {
                    tokenExpiredAlert(activity, errorResponseBody.message)
                }
            } else if (errorCode == 500) {
                customFailureAlert(activity, errorResponseBody?.message)
            }
        }

        fun handleParentErrorResponse(
            activity: Activity?,
            errorCode: Int?,
            errorResponseBody: StatusMessageResponse?,
            ApiType: String?
        ) {
            if (errorCode == 400) {
                if (errorResponseBody?.status == 0) {
                    parentCustomFailureAlert(activity, errorResponseBody.message, ApiType)
                } else if (errorResponseBody?.status == 2) {
                    tokenExpiredAlert(activity, errorResponseBody.message)
                }
            } else if (errorCode == 500) {
                parentCustomFailureAlert(activity, errorResponseBody!!.message,ApiType)
            }
        }

        fun openForgotPasswordScreen(activity: Activity?) {
            val changePage = Intent(activity, ForgotPassword::class.java)
            activity?.startActivity(changePage)

        }

        fun openSignInScreen(activity: Activity?) {
            val changePage = Intent(activity, SignInScreen::class.java)
            activity?.startActivity(changePage)


        }

        fun openMobileNumberScreen(activity: Activity?) {
            val changePage = Intent(activity, MobileNumberScreen::class.java)
            activity?.startActivity(changePage)
        }

        fun openPasswordScreen(activity: Activity?) {
            val changePage = Intent(activity, PasswordScreen::class.java)
            activity?.startActivity(changePage)

        }

        fun openOTPScreen(activity: Activity?) {
            val changePage = Intent(activity, OTPScreen::class.java)
            activity?.startActivity(changePage)

        }

        fun combinationHomeScreen(activity: Activity?) {
            val changePage = Intent(activity, CombinationHomeScreen::class.java)
            changePage.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(changePage)
            activity?.finish()
        }

        fun schoolHomeScreen(activity: Activity?) {
            val changePage = Intent(activity, SchoolHomeScreen::class.java)
            changePage.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(changePage)

        }

        fun parentHomeScreen(activity: Activity?) {
            val changePage = Intent(activity, ParentHomeScreen::class.java)
            changePage.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(changePage)
        }

        fun StandardGroupsStaffsScreen(activity: Activity?) {
            val intent = Intent(activity, ChooseStandardGroupsStaffs::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)

        }

        fun SpecificSectionsScreen(activity: Activity?) {
            val intent = Intent(activity, ChooseSpecificSections::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)

        }

        fun SpecificStudentsScreen(activity: Activity?) {
            val intent = Intent(activity, ChooseSpecificStudents::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)

        }

        fun assignMentActivity(activity: Activity?) {
            val intent = Intent(activity, TeacherAssignmentActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun attendanceActivity(activity: Activity?) {
            val intent = Intent(activity, TeacherAttendance::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun circularActivity(activity: Activity?) {
            val intent = Intent(activity, TeacherCirculars::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun approveLeaveActivity(activity: Activity?) {
            val intent = Intent(activity, TeacherApproveLeave::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun chatActivity(activity: Activity?) {
            val intent = Intent(activity, TeacherChatScreen::class.java)
            intent.putExtra("type", true)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun homeWorkActivity(activity: Activity?) {
            val intent = Intent(activity, TeacherHomeWork::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun eventsActivity(activity: Activity?) {
            val intent = Intent(activity, TeacherEvents::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun noticeBoardActivity(activity: Activity?) {
            val intent = Intent(activity, TeacherNoticeBorad::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun OffersScreen(activity: Activity?) {
            val intent = Intent(activity, OfferNewProductOnlineBook::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun textMesageActivity(activity: Activity?) {
            val intent = Intent(activity, TeacherGenerealTextMessages::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }


        fun timeTableActivity(activity: Activity?) {
            val intent = Intent(activity, TimeTable::class.java)
            intent.putExtra("type", "1")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun voiceMessageActivity(activity: Activity?) {
            val intent = Intent(activity, TeacherVoiceMessages::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun imagesActivity(activity: Activity?) {
            val intent = Intent(activity, TeacherPhotosSreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun videoActivity(activity: Activity?) {
            val intent = Intent(activity, TeacherVideoUploadScreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun openSetNewPasswordScreen(activity: Activity?) {
            val intent = Intent(activity, SetNewPasswordScreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun parentApplyLeave(activity: Activity?) {
            val intent = Intent(activity, ParentApplyLeave::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }


        fun imgTeacherHomeIntent(activity: Activity?) {
            val intent = Intent(activity, SchoolHomeScreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            activity?.startActivity(intent)
        }

        fun imgHomeIntent(activity: Activity?) {
            val intent = Intent(activity, ParentHomeScreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            activity?.startActivity(intent)
        }

        fun imgProfileIntent(activity: Activity?) {
            val intent = Intent(activity, SettingScreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            activity?.startActivity(intent)
        }

        fun recipientsActivity(activity: Activity?) {
            val intent = Intent(activity, TeacherRecipientsScreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun parentAssignmentActivity(activity: Activity?) {
            val intent = Intent(activity, ParentAssignment::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)

        }

        fun parentchatActivity(activity: Activity?) {
            val intent = Intent(activity, ParentChatScreen::class.java)
            intent.putExtra("type", false)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun parentCircularActivity(activity: Activity?) {
            val intent = Intent(activity, ParentCircular::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)

        }

        fun parentEventsActivity(activity: Activity?) {
            val intent = Intent(activity, ParentEvents::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun parentHomeworkActivity(activity: Activity?) {
            val intent = Intent(activity, ParentHomeWork::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun parentImagesActivity(activity: Activity?) {
            val intent = Intent(activity, ParentImagesScreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun parentNoticeboardActivity(activity: Activity?) {
            val intent = Intent(activity, ParentNoticeBoard::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun parentSettingsActivity(activity: Activity?) {
            val intent = Intent(activity, SettingScreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun parentLibraryActivity(activity: Activity?) {
            val intent = Intent(activity, ParentLibraryScreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun parentExamScheduleActivity(activity: Activity?) {
            val intent = Intent(activity, ParentExamList::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun parentFeesDetailActivity(activity: Activity?) {
            val intent = Intent(activity, ParentFeesDetails::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun parentTimeTableActivty(activity: Activity?) {
            val intent = Intent(activity, ParentTimeTable::class.java)
            intent.putExtra("type", "0")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun parentVideoActivity(activity: Activity) {
            val intent = Intent(activity, ParentVideo::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity.startActivity(intent)
        }

        fun parentStaffActivity(activity: Activity?) {
            val intent = Intent(activity, ParentStaff::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }



        fun parentAttendanceActivity(activity: Activity?) {
            val intent = Intent(activity, ParentAttendance::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun parentExamResultActivity(activity: Activity?) {
            val intent = Intent(activity, ParentExamResult::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun parentChatMemberActivity(context: Context?) {
            val intent = Intent(context, ChatConversation::class.java)
            intent.putExtra("type", false)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context?.startActivity(intent)
        }

        fun parentCircularView(activity: Activity?, text_info: Text_Class) {
            val intent = Intent(activity, ParentCircularDetails::class.java)
            intent.putExtra("content", text_info.recipients)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun parentVoiceScreen(activity: Activity?) {
            val intent = Intent(activity, ParentVoiceMessageScreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun parentTextMessageView(activity: Activity?) {
            val intent = Intent(activity, ParentTextMessageScreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun parentRoleActivity(activity: Activity?) {
            val intent = Intent(activity, MenuScreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun parentEventsHistoryActivity(activity: Activity?) {
            val intent = Intent(activity, ParentEventsViewScreen::class.java)
            intent.putExtra("type", "0")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun parentExamScreen(activity: Activity?) {
            val intent = Intent(activity, FilterScreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun parentHomeworkHistoryActivityt(
            activity: Activity?,
            type: String,
            text_info: Leave_Class
        ) {
            val intent = Intent(activity, ParentHomeWorkViewScreen::class.java)
            intent.putExtra("type", type)
            intent.putExtra("content", text_info.status)
            intent.putExtra("homeworktype", text_info.leavetype)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun ImageViewScreen(activity: Activity?, type: Boolean, text_info: String) {
            val intent = Intent(activity, ViewFileScreen::class.java)
            intent.putExtra("contentfile", text_info)
            intent.putExtra("type", type)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun viewFileActivity(activity: Activity?, text_info: EventsImageClass, type: Boolean) {
            val intent = Intent(activity, ViewFileScreen::class.java)
            intent.putExtra("contentfile", text_info)
            intent.putExtra("type", type)
            activity?.startActivity(intent)
        }

        fun parentExamResultSubjectsActivity(activity: Activity?) {
            val intent = Intent(activity, ParentExamResultSubject::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun parentNoticeboardHistoryActivity(activity: Activity?) {
            val intent = Intent(activity, ParentNoticeboardViewScreen::class.java)
            intent.putExtra("type", "0")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun parentVideoViewActivity(activity: Activity?) {
            val intent = Intent(activity, ParentVideoView::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun teacherChatActivity(activity: Activity?) {
            val intent = Intent(activity, ChatConversation::class.java)
            intent.putExtra("type", true)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun schoolListScreen(activity: Activity?) {
            val intent = Intent(activity, TeacherSchoolsListScreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun teacherEventsActivity(activity: Activity?, REQUEST_GAllery1: Int) {
            val intent1 = Intent(activity, AlbumSelectActivity::class.java)
            intent1.putExtra("Events", "Images")
            intent1.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivityForResult(intent1, REQUEST_GAllery1)

        }

        fun teacherassignmentHistoryActivity(activity: Activity?, text_info: EventsImageClass) {
            val intent = Intent(activity, AssignmentViewScreen::class.java)
            intent.putExtra("type", false)
            intent.putExtra("contentfile", text_info)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun parentAssignmentView(activity: Activity?, item: EventsImageClass) {
            val intent = Intent(activity, AssignmentViewScreen::class.java)
            intent.putExtra("type", true)
            intent.putExtra("contentfile", item)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun teacherTextHistoryActivity(activity: Activity?) {
            val intent = Intent(activity, TeacherTextMessageViewScreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun teacherMarkattendaceActivity(activity: Activity?, text_info: DayCLass) {
            val intent = Intent(activity, TeacherMarkAttendance::class.java)
            intent.putExtra("AttendanceType", text_info.day)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun finalPreviewVoiceMessage(activity: Activity?, voicetype: Boolean) {
            val intent = Intent(activity, FinalPreviewVoiceMessage::class.java)
            intent.putExtra("Voicetype", voicetype)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun finalPreviewTextMessage(activity: Activity?) {
            val intent = Intent(activity, FinalPreviewTextMessage::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun finalPreviewAssignment(activity: Activity?) {
            val intent = Intent(activity, FinalPreviewAssignment::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun finalPreviewImages(activity: Activity?) {
            val intent = Intent(activity, FinalPreviewImages::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun finalPreviewEvents(activity: Activity?) {
            val intent = Intent(activity, FinalPreviewEvents::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun finalPreviewNoticeboard(activity: Activity?) {
            val intent = Intent(activity, FinalPreviewNoticeBoard::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun finalPreviewHomeWork(activity: Activity?) {
            val intent = Intent(activity, FinalPreviewHomework::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun finalPeviewCircular(activity: Activity?) {
            val intent = Intent(activity, FinalPreviewCircular::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun finalPreviewVideo(activity: Activity?) {
            val intent = Intent(activity, FinalPreviewVideo::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }

        fun previewScreens(activity: Activity) {
            if (MENU_TYPE == MENU_EMERGENCY) {
                finalPreviewVoiceMessage(activity, false)
            } else if (MENU_TYPE == MENU_VOICE) {
                finalPreviewVoiceMessage(activity, false)
            } else if (MENU_TYPE == MENU_TEXT) {
                finalPreviewTextMessage(activity)
            } else if (MENU_TYPE == MENU_IMGAE_PDF) {
                finalPreviewImages(activity)
            } else if (MENU_TYPE == MENU_ASSIGNMENT) {
                finalPreviewAssignment(activity)
            } else if (MENU_TYPE == MENU_VIDEO) {
                finalPreviewVideo(activity)
            } else if (MENU_TYPE == MENU_TEXT_HOMEWORK) {
                finalPreviewHomeWork(activity)
            } else if (MENU_TYPE == MENU_VOICE_HOMEWORK) {
                finalPreviewHomeWork(activity)

            } else if (MENU_TYPE == MENU_EVENTS) {
                finalPreviewEvents(activity)

            } else if (MENU_TYPE == MENU_NOTICEBOARD) {
                finalPreviewNoticeboard(activity)

            } else if (MENU_TYPE == MENU_PDF_UPLOAD) {
                finalPeviewCircular(activity)

            }
        }

        fun openSchoolMenuScreens(activity: Activity?) {

            if (MENU_TYPE == MENU_EMERGENCY) {
                voiceMessageActivity(activity)
            } else if (MENU_TYPE == MENU_VOICE) {
                voiceMessageActivity(activity)
            } else if (MENU_TYPE == MENU_TEXT) {
                textMesageActivity(activity)
            } else if (MENU_TYPE == MENU_IMGAE_PDF) {
                imagesActivity(activity)
            } else if (MENU_TYPE == MENU_ASSIGNMENT) {
                assignMentActivity(activity)
            } else if (MENU_TYPE == MENU_VIDEO) {
                videoActivity(activity)
            } else if (MENU_TYPE == MENU_TEXT_HOMEWORK) {
                homeWorkActivity(activity)
            } else if (MENU_TYPE == MENU_VOICE_HOMEWORK) {
                homeWorkActivity(activity)
            } else if (MENU_TYPE == MENU_ATTEDANCE_MARKING) {
                attendanceActivity(activity)
            } else if (MENU_TYPE == MENU_ABSENTEES_REPORT) {

            } else if (MENU_TYPE == MENU_EVENTS) {
                eventsActivity(activity)
            } else if (MENU_TYPE == MENU_NOTICEBOARD) {
                noticeBoardActivity(activity)

            } else if (MENU_TYPE == MENU_INTRACTION_WITH_STUDENT) {
                chatActivity(activity)
            } else if (MENU_TYPE == MENU_IMPORTANT_INFO) {
                OffersScreen(activity)
            } else if (MENU_TYPE == MENU_FEEDBACK) {

            } else if (MENU_TYPE == MENU_ONLINE_TEXT_BOOK) {
                OffersScreen(activity)
            } else if (MENU_TYPE == MENU_OTHER_SERVICES) {
                OffersScreen(activity)
            } else if (MENU_TYPE == MENU_LEAVE_REQUESTS) {
                approveLeaveActivity(activity)
            } else if (MENU_TYPE == MENU_MESSAGES_FROM_MANAGEMENT) {

            } else if (MENU_TYPE == MENU_SCHEDULE_EXAM) {

            } else if (MENU_TYPE == MENU_SCHOOL_STRENGTH) {

            } else if (MENU_TYPE == MENU_PDF_UPLOAD) {
                circularActivity(activity)
            }
        }

        fun openParentMenuScreens(activity: Activity?) {

            if (PARENT_MENU_TYPE == PARENT_MENU_VOICE) {
                parentVoiceScreen(activity)
            } else if (PARENT_MENU_TYPE == PARENT_MENU_TEXT) {
                parentTextMessageView(activity)

            } else if (PARENT_MENU_TYPE == PARENT_MENU_HOMEWORK) {
                parentHomeworkActivity(activity)
            } else if (PARENT_MENU_TYPE == PARENT_MENU_EXAM_TEST) {
                parentExamScheduleActivity(activity)
            } else if (PARENT_MENU_TYPE == PARENT_MENU_EXAM_MARKS) {
                parentExamResultActivity(activity)
            } else if (PARENT_MENU_TYPE == PARENT_MENU_CIRCULARS) {
                parentCircularActivity(activity)
            } else if (PARENT_MENU_TYPE == PARENT_MENU_NOTICEBOARD) {
                parentNoticeboardActivity(activity)
            } else if (PARENT_MENU_TYPE == PARENT_MENU_EVENTS) {
                parentEventsActivity(activity)
            } else if (PARENT_MENU_TYPE == PARENT_MENU_ATTEDANCE_REPORT) {
                parentAttendanceActivity(activity)
            } else if (PARENT_MENU_TYPE == PARENT_MENU_REQUEST_LEAVE) {
                parentApplyLeave(activity)
            } else if (PARENT_MENU_TYPE == PARENT_MENU_IMAGES) {
                parentImagesActivity(activity!!)
            } else if (PARENT_MENU_TYPE == PARENT_MENU_FEE_DETAILS) {
                parentFeesDetailActivity(activity!!)
            } else if (PARENT_MENU_TYPE == PARENT_MENU_ASSIGNMENT) {
                parentAssignmentActivity(activity)
            } else if (PARENT_MENU_TYPE == PARENT_MENU_VIDEO) {
                parentVideoActivity(activity!!)
            } else if (PARENT_MENU_TYPE == PARENT_MENU_LIBRARY) {
                parentLibraryActivity(activity)
            } else if (PARENT_MENU_TYPE == PARENT_MENU_INTRACTION_WITH_STAFF) {
                parentchatActivity(activity)

            } else if (PARENT_MENU_TYPE == PARENT_MENU_ONLINE_TEXT_BOOK) {

            }

        }


    }


}