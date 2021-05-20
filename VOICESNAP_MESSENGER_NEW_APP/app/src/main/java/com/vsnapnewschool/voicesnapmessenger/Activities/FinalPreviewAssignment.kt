package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.SelcetedFileList
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.activity_final_preview_assignment.*
import kotlinx.android.synthetic.main.activity_final_preview_assignment.lblSubject
import kotlinx.android.synthetic.main.activity_final_preview_homework.*
import kotlinx.android.synthetic.main.scroll_preview_assignment.*

class FinalPreviewAssignment : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scroll_preview_assignment)
        lblrecipient = findViewById<TextView>(R.id.lblrecipient)
        rcyleRecipients = findViewById<RecyclerView>(R.id.rcyleRecipients)

        enableCrashLytics()
        initializeActionBar()
        setTitle(getString(R.string.txt_Preview))
        enableSearch(false)
     //   finalPreviewReceipientsAdpter(this)
        lblRecipient!!.setText(getString(R.string.lbl_selectedSubject))

        lblselectSubject.text = UtilConstants.selectedSubjectName
        lblAssignmentDescription.text = UtilConstants.Description
        lblAssignmentTitle.text = UtilConstants.Title
        lblSubject.text = UtilConstants.selectedSubjectName

        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)
        btnAssignmentPublish?.setOnClickListener(this)
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
            R.id.btnAssignmentPublish -> {

                awsFileUpload(this,0)


           }
        }
    }


}
