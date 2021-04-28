package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.Group
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.RecipientsType
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.Staff
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.Standard
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.StandardSection
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.Students
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.activity_final_preview_text.*
import kotlinx.android.synthetic.main.scroll_preview_text.*

class FinalPreviewTextMessage : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scroll_preview_text)
        lblrecipient = findViewById<TextView>(R.id.lblrecipient)
        rcyleRecipients = findViewById<RecyclerView>(R.id.rcyleRecipients)
        enableCrashLytics()
        initializeActionBar()
        setTitle(getString(R.string.txt_Preview))
        enableSearch(false)



        lblTitle.text = UtilConstants.Title
        lblDescription.text = UtilConstants.Description
        btnTextPublish?.setOnClickListener(this)
        finalPreviewReceipientsAdpter(this)

        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnTextPublish -> {
                HideKeyboard_Fragment(this)
                if (RecipientsType == Students) {
                    SchoolAPIServices.sendTextToStandardGroupStaffSectionStudents(this)
                } else if (RecipientsType == Standard) {
                    SchoolAPIServices.sendTextToStandardGroupStaffSectionStudents(this)
                } else if (RecipientsType == StandardSection) {
                    SchoolAPIServices.sendTextToStandardGroupStaffSectionStudents(this)
                } else if (RecipientsType == Group) {
                    SchoolAPIServices.sendTextToStandardGroupStaffSectionStudents(this)
                } else if (RecipientsType == Staff) {
                    SchoolAPIServices.sendTextToStandardGroupStaffSectionStudents(this)
                }

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