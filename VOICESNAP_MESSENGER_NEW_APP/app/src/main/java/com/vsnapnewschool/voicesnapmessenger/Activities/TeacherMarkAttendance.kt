package com.vsnapnewschool.voicesnapmessenger.Activities

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.PopupWindow
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.MarkAttendanceAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.AttendanceCheckListener
import com.vsnapnewschool.voicesnapmessenger.Models.AttendanceClass
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.mark_attendance.*
import kotlinx.android.synthetic.main.popup_markabsentees_attendance.view.*
import java.util.*

class TeacherMarkAttendance : BaseActivity(), AttendanceCheckListener,View.OnClickListener {
    var spinlist = ArrayList<String>()
    var spinlist1 = ArrayList<String>()
    private val menulist = ArrayList<AttendanceClass>()
    var Absentpopup: PopupWindow? = null
    internal lateinit var markAttendanceAdapter: MarkAttendanceAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mark_attendance)
        enableCrashLytics()

        initializeActionBar()
        setTitle(getString(R.string.title_mark_attendace))
        enableSearch(true)
        Day()

        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)

        val AttendanceType: String = intent.getStringExtra("AttendanceType")!!
        spinlist.add("1st Standard")
        spinlist.add("2nd Standard")
        spinlist.add("3rd Standard")
        spinlist.add("4th Standard")
        spinlist.add("5th Standard")

        val CourseAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this@TeacherMarkAttendance,
            android.R.layout.simple_spinner_item,
            spinlist
        )
        CourseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinstd.setAdapter(CourseAdapter)
        spinstd.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long) {}

            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        })

        spinlist1.add("A Section")
        spinlist1.add("B Section")
        spinlist1.add("C Section")
        spinlist1.add("D Section")
        spinlist1.add("E Section")

        val CourseAdapter1: ArrayAdapter<String> = ArrayAdapter<String>(this@TeacherMarkAttendance, android.R.layout.simple_spinner_item, spinlist1)
        CourseAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinsec.setAdapter(CourseAdapter1)
        spinsec.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
            }
            override fun onNothingSelected(arg0: AdapterView<*>?) {

            }
        })

        markAttendanceAdapter = MarkAttendanceAdapter(menulist, this@TeacherMarkAttendance,AttendanceType,this)
        val mLayoutManager = LinearLayoutManager(this)
        rcyStudents.setLayoutManager(mLayoutManager)
        rcyStudents.layoutManager = mLayoutManager
        rcyStudents.setItemAnimator(DefaultItemAnimator())
        rcyStudents.setAdapter(markAttendanceAdapter)
        markAttendanceAdapter.notifyDataSetChanged()

        if(AttendanceType.equals("Show Attendance")){
            lbltxt.visibility=View.GONE
            AttendanceHistorylayout.visibility=View.VISIBLE
        }else{
            lnrSpinner.visibility=View.VISIBLE
        }

        btnMarkAbsent?.setOnClickListener(this)

    }
    private fun popupMarkAbsent() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialog: View = inflater.inflate(R.layout.popup_markabsentees_attendance, null)
        Absentpopup = PopupWindow(
            dialog, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)
        Absentpopup!!.showAtLocation(dialog, Gravity.CENTER, 0, 0)
        Absentpopup!!.setContentView(dialog)
        val container = Absentpopup!!.getContentView().getParent() as View
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val p = container.layoutParams as WindowManager.LayoutParams
        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        p.dimAmount = 0.6f
        wm.updateViewLayout(container, p)

        dialog.btnCancel.setOnClickListener(View.OnClickListener {
            Absentpopup!!.dismiss()
        })
    }

    private fun Day() {
        var menus = AttendanceClass("1. Abhi",true)
        menulist.add(menus)
        menus = AttendanceClass("2. Banu",true)
        menulist.add(menus)
        menus = AttendanceClass("3. Bavi",false)
        menulist.add(menus)
        menus = AttendanceClass("4. Bala",false)
        menulist.add(menus)
        menus = AttendanceClass("5. kavi",false)
        menulist.add(menus)
        menus = AttendanceClass("6. Sanju",true)
        menulist.add(menus)
        menus = AttendanceClass("7. Shiva",false)
        menulist.add(menus)
        menus = AttendanceClass("8. Sam",false)
        menulist.add(menus)
        menus = AttendanceClass("9. Raj",true)
        menulist.add(menus)
        menus = AttendanceClass("10. Vino Vino Vino ",false)
        menulist.add(menus)
        menus = AttendanceClass("11. Vicky",false)
        menulist.add(menus)

    }

    override fun check(menu: AttendanceClass?) {


    }
    override fun uncheck(menu: AttendanceClass?) {


    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.btnMarkAbsent -> {
                HideKeyboard_Fragment(this)
                popupMarkAbsent()
            }
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