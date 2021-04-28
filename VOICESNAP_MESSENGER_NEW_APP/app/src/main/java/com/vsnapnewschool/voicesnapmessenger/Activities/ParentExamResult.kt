package com.vsnapnewschool.voicesnapmessenger.Activities

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentExamResultAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.examResultListener
import com.vsnapnewschool.voicesnapmessenger.Models.DayCLass
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.parent_exam_result.*
import java.util.*

class ParentExamResult : BaseActivity(), AdapterView.OnItemSelectedListener,View.OnClickListener {
    private val menulist = ArrayList<DayCLass>()
    internal lateinit var ExamResultAdapter: ParentExamResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.parent_exam_result)
        enableCrashLytics()
        parentActionbar()
        setTitle(getString(R.string.title_Exam_Result))
        enableSearch(true)
        Day()
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)
        scrollAdds(this,imageSlider)

        val adapter = ArrayAdapter.createFromResource(this, R.array.ExamType, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SpinExamResult.adapter = adapter
        SpinExamResult.onItemSelectedListener = this

        ExamResultAdapter = ParentExamResultAdapter(menulist, this, object : examResultListener {
            override fun onexamResultClick(holder: ParentExamResultAdapter.MyViewHolder, text_info: DayCLass) {

                holder.lbldetails.setOnClickListener({

                    UtilConstants.parentExamResultSubjectsActivity(this@ParentExamResult)

                })

            }
        })
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@ParentExamResult) as RecyclerView.LayoutManager
        rcyResults.setLayoutManager(mLayoutManager)
        rcyResults.setItemAnimator(DefaultItemAnimator())
        rcyResults.setAdapter(ExamResultAdapter)
        ExamResultAdapter.notifyDataSetChanged()
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
        menus = DayCLass("Sat")
        menulist.add(menus)
        menus = DayCLass("Sat")
        menulist.add(menus)
        menus = DayCLass("Sat")
        menulist.add(menus)
        menus = DayCLass("Sat")
        menulist.add(menus)
        menus = DayCLass("Sat")
        menulist.add(menus)

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val text: String = parent?.getItemAtPosition(position).toString()
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
        }    }
}