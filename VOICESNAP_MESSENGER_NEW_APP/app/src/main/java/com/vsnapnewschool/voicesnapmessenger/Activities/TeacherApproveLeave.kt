package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.ApproveLeaveAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.ApproveLeaveListener
import com.vsnapnewschool.voicesnapmessenger.Models.EventsImageClass
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.ApproveLeaveData
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.ApproveLeaveId
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.approve_leave_adapter.*
import kotlinx.android.synthetic.main.approve_leave_request.*
import java.util.*

class TeacherApproveLeave : BaseActivity(), View.OnClickListener {
    private val menulist = ArrayList<EventsImageClass>()
    internal lateinit var approveLeaveAdapter: ApproveLeaveAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.approve_leave_request)
        enableCrashLytics()

        initializeActionBar()
        setTitle(getString(R.string.title_Approve_leave))
        enableSearch(true)
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)

        SchoolAPIServices.getApproveLeaveList(this)

        approveLeaveAdapter = ApproveLeaveAdapter(UtilConstants.ApproveLeaveList, this, object : ApproveLeaveListener {
            override fun onapproveleveClick(holder: ApproveLeaveAdapter.MyViewHolder, item: ApproveLeaveData) {

                holder.btnApprove.setOnClickListener {
                    //Approve=1

                    ApproveLeaveId=item.leave_id
                    UtilConstants.ApproveLeaveTypeStatus="1"

                    SchoolAPIServices.approveLeaveStatus(this@TeacherApproveLeave)


                    Toast.makeText(this@TeacherApproveLeave, "Button Approve Click", Toast.LENGTH_SHORT
                    ).show()
                }
                holder.btnReject.setOnClickListener({
                    ApproveLeaveId=item.leave_id
                    UtilConstants.ApproveLeaveTypeStatus="2"

                    SchoolAPIServices.approveLeaveStatus(this@TeacherApproveLeave)


                    Toast.makeText(
                        this@TeacherApproveLeave,
                        btnReject.text.toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                })
            }
        })

        val mLayoutManager = LinearLayoutManager(this)
        rcyleApproveleave.layoutManager = mLayoutManager
        rcyleApproveleave.adapter = approveLeaveAdapter

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
        }
    }
}

