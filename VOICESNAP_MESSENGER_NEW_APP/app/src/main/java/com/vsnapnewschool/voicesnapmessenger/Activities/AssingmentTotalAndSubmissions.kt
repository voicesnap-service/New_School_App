package com.vsnapnewschool.voicesnapmessenger.Activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.AssignmentHistoryAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.ExamScheduleAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.TotalSubmissionsAdapter
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetAssingmentMemberSubmissionCallback
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetParentSubmittedAssignmentCallback
import com.vsnapnewschool.voicesnapmessenger.Interfaces.AssingmentHistoryListener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.examScheduleListener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.totalsubmissionsListener

import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.Network.StudentAPIServices
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetParentAssingmentFiles
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetAssingmentMemberSubmissions

import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.AssignmentStudentID
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_ASSIGNMENT
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_TYPE
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.PARENT_MENU_ASSIGNMENT
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.PARENT_MENU_TYPE
import kotlinx.android.synthetic.main.activity_assingment_total_and_submissions.*
import kotlinx.android.synthetic.main.activity_assingment_total_and_submissions.TeacherBottomLayout
import kotlinx.android.synthetic.main.activity_assingment_total_and_submissions.lblSeeMore
import kotlinx.android.synthetic.main.activity_assingment_total_and_submissions.recyclerview
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_activity_examschedule.*
import kotlinx.android.synthetic.main.parent_assignment_history.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.ArrayList

class AssingmentTotalAndSubmissions : BaseActivity(), View.OnClickListener,
    GetAssingmentMemberSubmissionCallback{
    var value: Boolean? = null
    internal lateinit var submissionsAdapter: TotalSubmissionsAdapter
    var assignmenMemberSubmissiontList =
        ArrayList<GetAssingmentMemberSubmissions.MemberSubmmisionsData>()
    var ViewFilePopUp: PopupWindow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        value = intent.extras!!.getBoolean("type")
        if (value as Boolean) {
            theme.applyStyle(R.style.AppThemeParent, true)

        } else {
            theme.applyStyle(R.style.AppTheme, true)

        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        enableCrashLytics()
        scrollAdds(this, imageSlider)
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)



        if (value as Boolean) {
            parentActionbar()

            setTitle(getString(R.string.title_Assignment))
            enableSearch(false)
            recyle_parent_bottom_layout.visibility = View.VISIBLE
            getWindow().setStatusBarColor(
                ContextCompat.getColor(
                    getApplicationContext(),
                    R.color.clr_parent
                )
            )
            lblSeeMore.visibility = View.GONE

        } else {
            initializeActionBar()
            enableSearch(false)
            TeacherBottomLayout.visibility = View.VISIBLE
            setTitle(getString(R.string.title_Assignment))

            getWindow().setStatusBarColor(
                ContextCompat.getColor(
                    getApplicationContext(),
                    R.color.app_color
                )
            )
            lblSeeMore.visibility = View.GONE

        }
        rytViewImage?.setOnClickListener(this)
        setContentView(R.layout.activity_assingment_total_and_submissions)

            SchoolAPIServices.getAssingmentMemberList(this, this)

    }

    override fun callbackSubmissions(responseBody: GetAssingmentMemberSubmissions) {
        assignmenMemberSubmissiontList.clear()
        assignmenMemberSubmissiontList =
            responseBody.data as ArrayList<GetAssingmentMemberSubmissions.MemberSubmmisionsData>
        submissionsAdapter = TotalSubmissionsAdapter(assignmenMemberSubmissiontList, this,
            object : totalsubmissionsListener {
                override fun onAssingmentSubmissionsClick(
                    holder: TotalSubmissionsAdapter.MyViewHolder,
                    item: GetAssingmentMemberSubmissions.MemberSubmmisionsData
                ) {
                    holder.btnView.setOnClickListener {
                        AssignmentStudentID = item.member_id
                        UtilConstants.PopupViewFiles(this@AssingmentTotalAndSubmissions, value!!)

                    }
                }
            })
        val mLayoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = mLayoutManager
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = submissionsAdapter
    }

//    fun PopupViewFiles() {
//        val inflater: LayoutInflater =
//            this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val view: View = inflater.inflate(R.layout.file_view_screen, null)
//        ViewFilePopUp = PopupWindow(
//            view,
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            true
//        )
//        ViewFilePopUp!!.setContentView(view)
//        ViewFilePopUp!!.setTouchable(true)
//        ViewFilePopUp!!.setFocusable(false)
//        ViewFilePopUp!!.setOutsideTouchable(false)
//
//        val btnViewImage = view.findViewById<TextView>(R.id.btnViewImage)
//        val btnViewPDF = view.findViewById<TextView>(R.id.btnViewPDF)
//        val imgClose = view.findViewById<ImageView>(R.id.imgClose)
//        imgClose.setOnClickListener({
//            ViewFilePopUp!!.dismiss()
//        })
//
//        btnViewImage.setOnClickListener {
//            UtilConstants.AssignmentFiletype="IMAGE"
//
//            UtilConstants.FileImageViewScreen(this, value!!)
//
//        }
//        btnViewPDF.setOnClickListener {
//            UtilConstants.AssignmentFiletype="PDF"
//            UtilConstants.FileViewScreen(this,value!!)
//
//
//
//        }
//        ViewFilePopUp!!.showAtLocation(view, Gravity.CENTER, 0, 0)
//    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.imgchat -> {

            }

            R.id.imgHomeMenu -> {
                UtilConstants.imgHomeIntent(this)

            }
            R.id.imgSettings -> {

                UtilConstants.imgProfileIntent(this)
            }

            R.id.imgTeacherChat -> {

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