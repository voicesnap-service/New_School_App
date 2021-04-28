package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Adapters.TimeClassAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.TimeDayAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.DayCLass
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.timetable.*
import java.util.*

class ParentTimeTable : BaseActivity(),View.OnClickListener {
    private val menulist = ArrayList<DayCLass>()
    internal lateinit var dayAdapter: TimeDayAdapter
    internal lateinit var timeAdapter: TimeClassAdapter
    private  val row_index: Int = -1
    private val subjectlist = ArrayList<DayCLass>()
    private val daylist = ArrayList<DayCLass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.parent_timetable)
        enableCrashLytics()
        scrollAdds(this,imageSlider)
        parentActionbar()
        setTitle(getString(R.string.title_timetable))
        enableSearch(true)
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)
        val type:String = intent.getStringExtra("type")!!

        Day()
        dayAdapter = TimeDayAdapter(menulist,this,type,row_index, object : TimeDayAdapter.BtnClickListener {

            override fun onBtnClick(position: Int) {
                if (position == 0) {
                    ClassTime()
                    timeAdapter = TimeClassAdapter(subjectlist,this@ParentTimeTable, true)

                } else if (position == 1) {
                    ClassSubjects()
                    timeAdapter = TimeClassAdapter(menulist, this@ParentTimeTable, true)

                } else {
                    ClassTime()
                    timeAdapter = TimeClassAdapter(subjectlist, this@ParentTimeTable, true)

                }
                val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@ParentTimeTable)
                rcyclass.setLayoutManager(mLayoutManager)
                rcyclass.setItemAnimator(DefaultItemAnimator())
                rcyclass.setAdapter(timeAdapter)
                timeAdapter.notifyDataSetChanged()
            }
        })
        rcyDay.setItemAnimator(DefaultItemAnimator())
        rcyDay.setLayoutManager(LinearLayoutManager(this@ParentTimeTable, LinearLayoutManager.HORIZONTAL, false))
        rcyDay.setAdapter(dayAdapter)
        dayAdapter.notifyDataSetChanged()

    }
    private fun Day() {
        menulist.clear()
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

    private fun ClassSubjects() {
        daylist.clear()
        var menus = DayCLass("Tamil")
        daylist.add(menus)
        menus = DayCLass("English")
        daylist.add(menus)
        menus = DayCLass("French")
        daylist.add(menus)
        menus = DayCLass("Maths")
        daylist.add(menus)
        menus = DayCLass("Chemistry")
        daylist.add(menus)
        menus = DayCLass("Science")
        daylist.add(menus)

    } private fun ClassTime() {
        subjectlist.clear()

        var menus = DayCLass("Biology")
        subjectlist.add(menus)

        menus = DayCLass("French")
        subjectlist.add(menus)

        menus = DayCLass("Zoology")
        subjectlist.add(menus)
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