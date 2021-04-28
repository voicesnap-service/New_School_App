package com.vsnapnewschool.voicesnapmessenger.Activities

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.ExamScheduleAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.examScheduleListener
import com.vsnapnewschool.voicesnapmessenger.Models.ImageClass
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_activity_examschedule.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import java.util.*

class ParentExamList : BaseActivity(), AdapterView.OnItemSelectedListener,View.OnClickListener {
    private val examlist = ArrayList<ImageClass>()
    var examadapter:ExamScheduleAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.parent_activity_examschedule)
        enableCrashLytics()
        parentActionbar()
        setTitle(getString(R.string.title_Exam_Schedule))
        enableSearch(true)
        ImageLength()
        scrollAdds(this,imageSlider)
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)
        examadapter = ExamScheduleAdapter(examlist, this, object : examScheduleListener {
            override fun onexamScheduleclick (holder: ExamScheduleAdapter.MyViewHolder, text_info: ImageClass) {
                holder.ExamSchedule.setOnClickListener {

                }
            }
        })
        val mLayoutManager = LinearLayoutManager(this)
        rcyleExamSchedule.layoutManager = mLayoutManager
        rcyleExamSchedule.itemAnimator = DefaultItemAnimator()
        rcyleExamSchedule.adapter = examadapter

        val adapter = ArrayAdapter.createFromResource(this, R.array.ExamType, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SpinExamType.adapter = adapter
        SpinExamType.onItemSelectedListener = this

    }
    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val text: String = parent?.getItemAtPosition(position).toString()
        (view as TextView).setTextColor(Color.WHITE)

    }
    private fun ImageLength() {
        var movieModel = ImageClass(R.drawable.home_events,"04","JUL","MATHEMATICS","10.00AM-11.00AM")
        examlist.add(movieModel)
        movieModel = ImageClass(R.drawable.home_events,"07","AUG","PHYSICS","11.00AM - 12.00AM")
        examlist.add(movieModel)
        movieModel = ImageClass( R.drawable.home_events,"08","SEP","COMPUTER SCIENCE","1.00AM - 2.00AM")
        examlist.add(movieModel)
        movieModel = ImageClass( R.drawable.home_events,"09","OCT","CHEMISTRY","2.00AM - 3.00AM")
        examlist.add(movieModel)
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
