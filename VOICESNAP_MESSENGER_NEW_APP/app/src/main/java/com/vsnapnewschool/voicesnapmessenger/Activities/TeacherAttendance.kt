package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.michalsvec.singlerowcalendar.calendar.CalendarChangesObserver
import com.michalsvec.singlerowcalendar.calendar.CalendarViewManager
import com.michalsvec.singlerowcalendar.calendar.SingleRowCalendarAdapter
import com.michalsvec.singlerowcalendar.selection.CalendarSelectionManager
import com.michalsvec.singlerowcalendar.utils.DateUtils
import com.vsnapnewschool.voicesnapmessenger.Adapters.AttendanceMainAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.markattendanceListener
import com.vsnapnewschool.voicesnapmessenger.Models.DayCLass
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.attendance.*
import kotlinx.android.synthetic.main.calendar_item.view.*
import java.text.SimpleDateFormat
import java.util.*


class TeacherAttendance : BaseActivity(), View.OnClickListener {
    private val menulist = ArrayList<DayCLass>()
    private val attendancelist = ArrayList<DayCLass>()
    private var currentposition = 0
    internal lateinit var Attendanceadapter: AttendanceMainAdapter
    private val calendar = Calendar.getInstance()
    private var currentMonth = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.attendance)
        enableCrashLytics()

        initializeActionBar()
        setTitle(getString(R.string.title_Attendance))
        enableSearch(true)

        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)

        Day()
        Day1()
        calendar.time = Date()
        currentMonth = calendar[Calendar.MONTH]
        val formatter = SimpleDateFormat("d")
        val currentdate = Calendar.getInstance().time
        currentposition=formatter.format(currentdate).toInt()
        val myCalendarViewManager = object : CalendarViewManager {
            override fun setCalendarViewResourceId(position: Int, date: Date, isSelected: Boolean): Int {
                val cal = Calendar.getInstance()
                cal.time = date

                if(isSelected==true){
                    Attendanceadapter = AttendanceMainAdapter(attendancelist,this@TeacherAttendance,object :
                        markattendanceListener {
                        override fun onmarkAttendanceClick (holder: AttendanceMainAdapter.MyViewHolder, text_info: DayCLass) {
                            if(text_info.day.equals("Attendance")) {
                                holder.btnattendance.setOnClickListener({
                                    UtilConstants.teacherMarkattendaceActivity(this@TeacherAttendance,text_info)

                                })
                            }
                            else{
                                holder. btnattendance.setOnClickListener({
                                    UtilConstants.teacherMarkattendaceActivity(this@TeacherAttendance,text_info)


                                })
                            }
                        }
                    })
                    val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@TeacherAttendance)
                    rcyclass.setLayoutManager(mLayoutManager)
                    rcyclass.setItemAnimator(DefaultItemAnimator())
                    rcyclass.setAdapter(Attendanceadapter)
                    Attendanceadapter.notifyDataSetChanged()
                }

                return if (isSelected)

                    when (cal[Calendar.DAY_OF_WEEK]) {
                        else -> R.layout.selected_calendar_item
                    }
                else
                    when (cal[Calendar.DAY_OF_WEEK]) {
                        else -> R.layout.calendar_item
                    }


            }

            override fun bindDataToCalendarView(holder: SingleRowCalendarAdapter.CalendarViewHolder, date: Date, position: Int, isSelected: Boolean) {
                holder.itemView.tv_date_calendar_item.text = DateUtils.getDayNumber(date)
                holder.itemView.tv_day_calendar_item.text = DateUtils.getDay3LettersName(date)
            }
        }

        val myCalendarChangesObserver = object :
            CalendarChangesObserver {
            override fun whenSelectionChanged(isSelected: Boolean, position: Int, date: Date) {

                super.whenSelectionChanged(isSelected, position, date)
            }


        }
        val mySelectionManager = object : CalendarSelectionManager {
            override fun canBeItemSelected(position: Int, date: Date): Boolean {
                val cal = Calendar.getInstance()
                cal.time = date
                return when (cal[Calendar.DAY_OF_WEEK]) {
//                    Calendar.SATURDAY -> false
                    Calendar.SUNDAY -> false
                    else -> true
                }
            }
        }
        val singleRowCalendar = rcyDay.apply {
            calendarViewManager = myCalendarViewManager
            calendarChangesObserver = myCalendarChangesObserver
            calendarSelectionManager = mySelectionManager
            setDates(getFutureDatesOfCurrentMonth())
            init()
        }
    }
    private fun Day() {
        var menus = DayCLass("Mon")
        menulist.add(menus)

        menus = DayCLass("Tue")
        menulist.add(menus)

        menus = DayCLass("Wed")
        menulist.add(menus)

        menus = DayCLass("Thu")
        menulist.add(menus)

        menus = DayCLass("Fri")
        menulist.add(menus)

        menus = DayCLass("Sat")
        menulist.add(menus)

    }
    private fun Day1() {
        var menus = DayCLass("Show Attendance")
        attendancelist.add(menus)
        menus = DayCLass("Attendance")
        attendancelist.add(menus)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun getDatesOfNextMonth(): List<Date> {
        currentMonth++ // + because we want next month
        if (currentMonth == 12) {
            calendar.set(Calendar.YEAR, calendar[Calendar.YEAR] + 1)
            currentMonth = 0 // 0 == january
        }
        return getDates(mutableListOf())
    }

    private fun getDatesOfPreviousMonth(): List<Date> {
        currentMonth-- // - because we want previous month
        if (currentMonth == -1) {
            calendar.set(Calendar.YEAR, calendar[Calendar.YEAR] - 1)
            currentMonth = 11 // 11 == december
        }
        return getDates(mutableListOf())
    }

    private fun getFutureDatesOfCurrentMonth(): List<Date> {
        currentMonth = calendar[Calendar.MONTH]
        return getDates(mutableListOf())
    }


    private fun getDates(list: MutableList<Date>): List<Date> {
        calendar.set(Calendar.MONTH, currentMonth)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        list.add(calendar.time)
        while (currentMonth == calendar[Calendar.MONTH]) {
            calendar.add(Calendar.DATE, +1)
            if (calendar[Calendar.MONTH] == currentMonth)
                list.add(calendar.time)
        }
        calendar.add(Calendar.DATE, -1)
        return list
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