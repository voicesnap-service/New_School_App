package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Adapters.TimeTableListAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.TimeTableDayAdapter
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetDateForTimeTableCallBack
import com.vsnapnewschool.voicesnapmessenger.CallBacks.TimeTableCallBack
import com.vsnapnewschool.voicesnapmessenger.Models.DayCLass
import com.vsnapnewschool.voicesnapmessenger.Network.StudentAPIServices
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetDaysTimeTable
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetTimeTableList
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import kotlinx.android.synthetic.main.timetable.*
import java.util.*

class ParentTimeTable : BaseActivity(), View.OnClickListener, GetDateForTimeTableCallBack,
    TimeTableCallBack {
    var dateTimetablelist = ArrayList<GetDaysTimeTable.DateData>()
    var StudentTimeTablelist = ArrayList<GetTimeTableList.TimeTableData>()
    internal lateinit var timetabledayAdapter: TimeTableDayAdapter
    internal lateinit var timetablelistAdapter: TimeTableListAdapter
    var type:String="0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        enableCrashLytics()
        parentActionbar()
        setTitle(getString(R.string.title_timetable))
        enableSearch(true)
        scrollAdds(this, imageSlider)
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)
        recyle_parent_bottom_layout.visibility = View.VISIBLE

        StudentAPIServices.getDatesForTimetable(this, this)
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

    override fun callBackDatesTimeTable(responseBody: GetDaysTimeTable) {
        dateTimetablelist.clear()
        dateTimetablelist= responseBody.data as ArrayList<GetDaysTimeTable.DateData>
        timetabledayAdapter = TimeTableDayAdapter(dateTimetablelist, this,object : TimeTableDayAdapter.BtnClickListener {
            override fun onBtnClick(position: Int,datelist: GetDaysTimeTable.DateData) {
                StudentTimeTablelist.clear()
                UtilConstants.DateIdTimeTable=datelist.day_id
                StudentAPIServices.getTimeTable(this@ParentTimeTable,this@ParentTimeTable)
            }

        })
        val mLayoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL ,false)
        rcyhorizontal.layoutManager = mLayoutManager
        rcyhorizontal.itemAnimator = DefaultItemAnimator()
        rcyhorizontal.adapter = timetabledayAdapter
        timetabledayAdapter.notifyDataSetChanged()
    }

    override fun callbackTimeTable(responseBody: GetTimeTableList) {
        StudentTimeTablelist.clear()
        StudentTimeTablelist= responseBody.data as ArrayList<GetTimeTableList.TimeTableData>
        timetablelistAdapter = TimeTableListAdapter(StudentTimeTablelist, this,"0")
        val mLayoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = mLayoutManager
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = timetablelistAdapter
        timetablelistAdapter.notifyDataSetChanged()
    }
}



