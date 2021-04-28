package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.activity_final_preview_noticeboard.*
import kotlinx.android.synthetic.main.scroll_preview_noticeboard.*

class FinalPreviewNoticeBoard : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scroll_preview_noticeboard)
        enableCrashLytics()
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)
        btnNoticePublish?.setOnClickListener(this)
        lblrecipient = findViewById<TextView>(R.id.lblRecipients)
        rcyleRecipients = findViewById<RecyclerView>(R.id.rcyleRecipients)
        initializeActionBar()
        setTitle(getString(R.string.txt_Preview))
        enableSearch(false)

        UtilConstants.textBold(this, lblNoticeTitle)

        lblNoticeTitle.text = UtilConstants.Title
        lblNoticeDescription.text = UtilConstants.Description
        finalPreviewReceipientsAdpter(this)


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
            R.id.btnNoticePublish -> {
                if (UtilConstants.RecipientsType == UtilConstants.Standard) {
                    awsFileUpload(this, 0)
                } else if (UtilConstants.RecipientsType == UtilConstants.Group) {
                    awsFileUpload(this, 0)
                }
            }
        }
    }
}

