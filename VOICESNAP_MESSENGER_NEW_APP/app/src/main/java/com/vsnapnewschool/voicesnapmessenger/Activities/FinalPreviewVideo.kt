package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.scroll_preview_video.*

class FinalPreviewVideo : BaseActivity() , View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scroll_preview_video)
        lblrecipient = findViewById<TextView>(R.id.lblrecipient)
        rcyleRecipients = findViewById<RecyclerView>(R.id.rcyleRecipients)
        enableCrashLytics()
        initializeActionBar()
        setTitle(getString(R.string.txt_Preview))
        enableSearch(false)
        finalPreviewReceipientsAdpter(this)
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)
        btnPublishVideo?.setOnClickListener(this)

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
            R.id.btnPublishVideo ->{
                SchoolAPIServices.videosizereducer(this)
            }
        }
    }

}