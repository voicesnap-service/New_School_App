package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.ApproveLeaveAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.ApproveLeaveListener
import com.vsnapnewschool.voicesnapmessenger.Models.EventsImageClass
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
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
        ImageLength()
        approveLeaveAdapter = ApproveLeaveAdapter(menulist, this, object : ApproveLeaveListener {
            override fun onapproveleveClick(
                holder: ApproveLeaveAdapter.MyViewHolder,
                text_info: EventsImageClass
            ) {

                holder.btnApprove.setOnClickListener {
                    Toast.makeText(
                        this@TeacherApproveLeave,
                        "Button Approve Click",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                holder.btnReject.setOnClickListener({
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

    private fun ImageLength() {
        var movieModel = EventsImageClass(
            R.drawable.man,
            "Student 1",
            "234",
            "2 days sick leave",
            "Due To Feeling Not well", ""
        )
        menulist.add(movieModel)
        movieModel = EventsImageClass(
            R.drawable.album6,
            "Student 2",
            "005",
            "10 days casual leave",
            "Main Audiotorium", ""
        )
        menulist.add(movieModel)
        movieModel = EventsImageClass(
            R.drawable.album9,
            "Student 3",
            "8955",
            "1 day festive leave",
            "Due To Deewali", ""
        )
        menulist.add(movieModel)
        movieModel = EventsImageClass(
            R.drawable.event4,
            "Student 4",
            "475",
            "2 days rain leave",
            "Leave ue to Rain", ""
        )
        menulist.add(movieModel)
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

