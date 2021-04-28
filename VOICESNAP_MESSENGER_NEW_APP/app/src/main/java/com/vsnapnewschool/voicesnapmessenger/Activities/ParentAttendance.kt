package com.vsnapnewschool.voicesnapmessenger.Activities

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.login_screen.*
import kotlinx.android.synthetic.main.parent_attendance_percent.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*


class ParentAttendance : BaseActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {
    private var i = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.parent_attendance_percent)
        enableCrashLytics()
        scrollAdds(this,imageSlider)
        parentActionbar()
        setTitle(getString(R.string.title_Attendance))
        enableSearch(false)
        btnApplyLeave?.setOnClickListener(this)

        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)

        AssignmentsetTextStyle(lblDays, lblTotalDays, lblAttendend, lbldaysAttended, lblOnLeave, lblOnleavedays)
        lblPassword?.setTypeface(Typeface.DEFAULT_BOLD)
        i = progressBar!!.progress
        progressBar.max = 100
        progressBar.progress = 20
        lblPercent.setText(progressBar.progress.toString() + "%")
        val adapter = ArrayAdapter.createFromResource(this, R.array.Leavetype, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinattendance.adapter = adapter
        spinattendance.onItemSelectedListener = this
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//        val text: String = parent?.getItemAtPosition(position).toString()
        (view as TextView).setTextColor(Color.WHITE)
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