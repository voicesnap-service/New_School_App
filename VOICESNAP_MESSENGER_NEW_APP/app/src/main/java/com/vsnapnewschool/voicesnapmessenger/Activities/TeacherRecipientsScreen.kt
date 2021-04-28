package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.*
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.EntireSchool
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.Group
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_ASSIGNMENT
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_NOTICEBOARD
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_TEXT_HOMEWORK
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_TYPE
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_VOICE_HOMEWORK
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.RecipientsType
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.Staff
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.Standard
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.StandardSection
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.Students
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.recipient_main.*

class TeacherRecipientsScreen : BaseActivity(), View.OnClickListener {

    var LoginType:String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipient_main)
        enableCrashLytics()
        initializeActionBar()
        setTitle(getString(R.string.title_ChooseRecipients))
        enableSearch(false)
        HideKeyboard_Fragment(this)
        btnEntireSchool.setOnClickListener(this)
        btnSpecificStandards.setOnClickListener(this)
        btnSpecificGroups.setOnClickListener(this)
        btnSpecificSections.setOnClickListener(this)
        btnSpecificStudents.setOnClickListener(this)
        btnSpecificStaffs.setOnClickListener(this)

        LoginType=Util_shared_preferences.getMemberType(this)

        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)

        if(LoginType.equals("3")){   //staff
            btnEntireSchool.visibility=View.GONE
            btnSpecificStandards.visibility=View.GONE
            btnSpecificGroups.visibility=View.GONE
            btnSpecificStaffs.visibility=View.GONE
        } else if(LoginType.equals("2")){  //Principal
            if(MENU_TYPE== MENU_NOTICEBOARD){
                btnSpecificSections.visibility=View.GONE
                btnSpecificStudents.visibility=View.GONE
                btnSpecificStaffs.visibility=View.GONE
            }else if (MENU_TYPE== MENU_ASSIGNMENT){
                btnEntireSchool.visibility=View.GONE
                btnSpecificStandards.visibility=View.GONE
                btnSpecificGroups.visibility=View.GONE
                btnSpecificStaffs.visibility=View.GONE
            }
        } else if(LoginType.equals("6")){  //admin
            btnSpecificSections.visibility=View.GONE
            btnSpecificStudents.visibility=View.GONE
            btnSpecificStaffs.visibility=View.GONE
        } else if(LoginType.equals("1")){  //GroupHead
            btnSpecificStandards.visibility=View.GONE
            btnSpecificGroups.visibility=View.GONE
            btnSpecificSections.visibility=View.GONE
            btnSpecificStudents.visibility=View.GONE
            btnSpecificStaffs.visibility=View.GONE
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnEntireSchool -> {
                    RecipientsType=EntireSchool
                    UtilConstants.showEntireSchoolConfirmationAlert(this)
            }
            R.id.btnSpecificStandards -> {
               RecipientsType=Standard
                UtilConstants.StandardGroupsStaffsScreen(this)
            }
            R.id.btnSpecificGroups -> {
                RecipientsType=Group
                UtilConstants.StandardGroupsStaffsScreen(this)
            }
            R.id.btnSpecificSections -> {
               RecipientsType=StandardSection
                UtilConstants.SpecificSectionsScreen(this)
            }
            R.id.btnSpecificStudents -> {
               RecipientsType= Students
                UtilConstants.SpecificStudentsScreen(this)
            }
            R.id.btnSpecificStaffs -> {
               RecipientsType=Staff
                UtilConstants.StandardGroupsStaffsScreen(this)

            }
            R.id.imgTeacherChat -> {
                //  setChatClick(imgChat?, imgHome?, imgProfile?)
            }
            R.id.imgTeacherHomeMenu -> {
                UtilConstants.imgTeacherHomeIntent(this)
            }
            R.id.imgTeacherSettings -> {
                UtilConstants.imgProfileIntent(this)
            }


        }
    }
}