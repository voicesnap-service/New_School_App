package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.Group
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.RecipientsType
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.Staff
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.Standard
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.StandardSection
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.Students
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.activity_final_preview_circular.*
import kotlinx.android.synthetic.main.scroll_preview_circular.*

class FinalPreviewCircular : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scroll_preview_circular)

        lblrecipient = findViewById<TextView>(R.id.lblrecipient)
        rcyleRecipients = findViewById<RecyclerView>(R.id.rcyleRecipients)

        enableCrashLytics()
        initializeActionBar()
        setTitle(getString(R.string.txt_Preview))
        enableSearch(false)
        finalPreviewReceipientsAdpter(this)

        lblCircularTitle.text = UtilConstants.Description
        lblCircularDescription.text = UtilConstants.Title

        layoutViewPDF.visibility=View.GONE

        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)
        btnCircularPublish?.setOnClickListener(this)
        layoutViewPDF?.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imgTeacherChat -> {
                //  setChatClick(imgChat?, imgHome?, imgProfile?)
            }
            R.id.imgTeacherHomeMenu -> {
                UtilConstants.imgTeacherHomeIntent(this)
            }
            R.id.imgTeacherSettings -> {
                UtilConstants.imgProfileIntent(this)
            }
            R.id.layoutViewPDF -> {
              //  UtilConstants.viewFileActivity(this)
            }
            R.id.btnCircularPublish -> {
                if (RecipientsType == Standard) {
                    awsFileUpload(this, 0)
                } else if (RecipientsType == StandardSection) {
                    awsFileUpload(this, 0)
                } else if (RecipientsType == Students) {
                    awsFileUpload(this, 0)
                } else if (RecipientsType == Group) {
                    awsFileUpload(this, 0)
                } else if (RecipientsType == Staff) {
                    awsFileUpload(this, 0)
                }
            }
        }
    }


}