package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentExamResultSubjectAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.DayCLass
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.parent_exam_result.*
import java.util.*

class ParentExamResultSubject : BaseActivity(),View.OnClickListener {
    private val menulist = ArrayList<DayCLass>()

    internal lateinit var ExamSubjectAdapter: ParentExamResultSubjectAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.parent_exam_result)
        enableCrashLytics()
        scrollAdds(this,imageSlider)
        Day()
        parentActionbar()
        setTitle(getString(R.string.title_Exam_Result))
        enableSearch(true)
        consExamResult.visibility=View.GONE
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)

        ExamSubjectAdapter = ParentExamResultSubjectAdapter(menulist, this@ParentExamResultSubject)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@ParentExamResultSubject)
        rcyResults.setLayoutManager(mLayoutManager)
        rcyResults.setItemAnimator(DefaultItemAnimator())
        rcyResults.setAdapter(ExamSubjectAdapter)
        ExamSubjectAdapter.notifyDataSetChanged()
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
        menus = DayCLass("Sat")
        menulist.add(menus)

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