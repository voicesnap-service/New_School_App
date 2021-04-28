package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentHomeworkAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.homeworkListener
import com.vsnapnewschool.voicesnapmessenger.Models.Leave_Class
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.*

    class ParentHomeWork : BaseActivity(), View.OnClickListener  {
        private val menulist = ArrayList<Leave_Class>()
        internal lateinit var HomeWorkAdapter: ParentHomeworkAdapter
        var type:String="0"

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.recyclerview_layout)
            enableCrashLytics()
            parentActionbar()
            setTitle(getString(R.string.title_Homework))
            enableSearch(true)
            ImageLength()
            scrollAdds(this,imageSlider)
            imgchat?.setOnClickListener(this)
            imgHomeMenu?.setOnClickListener(this)
            imgSettings?.setOnClickListener(this)
            parent_bottom_layout.visibility=View.VISIBLE

            HomeWorkAdapter = ParentHomeworkAdapter(menulist, this,"0", object : homeworkListener {
                override fun onhomeworkClick (holder: ParentHomeworkAdapter.MyViewHolder, text_info: Leave_Class) {

                    if (type.equals("0")) {
                        holder.rytDetails1.setOnClickListener({
                            UtilConstants.parentHomeworkHistoryActivityt(this@ParentHomeWork,"0",text_info)
                        })
                    }
                }
            })
            val mLayoutManager = LinearLayoutManager(this)
            recyclerview.layoutManager = mLayoutManager
            recyclerview.itemAnimator = DefaultItemAnimator()
            recyclerview.adapter = HomeWorkAdapter
        }
        private fun ImageLength() {
            var menus = Leave_Class("Record Submission", "10 Dec", "Physics", "Sent on Jun 05 at 10.30 am", "", "text")
            menulist.add(menus)
            menus = Leave_Class("Algebra ", "05 Aug", "Maths", "Sent on Oct 05 at 10.30 am", "", "text")
            menulist.add(menus)
            menus = Leave_Class("Periodic Table ", "09 Jan", "Chemistry", "Sent on Jul 10 at 10.30 am", "", "voice")
            menulist.add(menus)
            menus = Leave_Class("Linear Equations ", "09 Jan", "Chemistry", "Sent on Jul 10 at 10.30 am", "", "text")
            menulist.add(menus)
        }
        fun filter(s: String) {

            val assignment: ArrayList<Leave_Class> = ArrayList<Leave_Class>()
            for (d in menulist) {
                val value: String = d.status!!.toLowerCase() + d.lblstartdate!!.toLowerCase() + d.leavetype!!.toLowerCase()
                if (value.contains(s.toLowerCase())) {
                    assignment.add(d)
//                lblNoRecordsFound.setVisibility(View.GONE)
                } else if (!value.contains(s) && assignment.size == 0) {
//                lblNoRecordsFound.setVisibility(View.VISIBLE)
                }
            }
            if (menulist.size != 0)
                HomeWorkAdapter.update(assignment);
        }

        override fun onClick(v: View?) {
            when (v!!.id) {
                R.id.imgchat -> {
                    //  setChatClick(imgChat?, imgHome?, imgProfile?)
                }
                R.id.imgHomeMenu -> {
                    UtilConstants.imgHomeIntent(this)
                }
                R.id.imgSettings -> {
                    UtilConstants.imgProfileIntent(this)
                }
            }
        }
    }