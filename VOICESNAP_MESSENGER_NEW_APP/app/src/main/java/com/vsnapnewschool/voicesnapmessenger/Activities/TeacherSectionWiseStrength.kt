package com.vsnapnewschool.voicesnapmessenger.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.ClassWiseStrengthAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.SectionWiseStrengthAdapter
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetClassWiseStrengthCallBack
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetSectionWiseCallBack
import com.vsnapnewschool.voicesnapmessenger.Interfaces.classStrengthListener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.sectionStrengthListener
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetClassWiseStrength
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetSectionWiseStrength
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.activity_teacher_school_strength.*
import kotlinx.android.synthetic.main.activity_teacher_section_wise_strength.*
import java.util.ArrayList

class TeacherSectionWiseStrength : BaseActivity(), View.OnClickListener,
    GetSectionWiseCallBack {

    internal lateinit var sectionAdapter: SectionWiseStrengthAdapter

    var sectionList = ArrayList<GetSectionWiseStrength.SectionStrengthData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_section_wise_strength)
        enableCrashLytics()
        initializeActionBar()
        setTitle(getString(R.string.title_school_strength))
        enableSearch(false)
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)
        SchoolAPIServices.getSectionWiseStrength(this, this)


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


    override fun callBackSectionStrength(responseBody: GetSectionWiseStrength) {
        sectionList.clear()
        sectionList = responseBody.data as ArrayList<GetSectionWiseStrength.SectionStrengthData>
        sectionAdapter = SectionWiseStrengthAdapter(sectionList, this, "0", object :
            sectionStrengthListener {
            override fun onSectionStrength(
                holder: SectionWiseStrengthAdapter.MyViewHolder,
                item: GetSectionWiseStrength.SectionStrengthData
            ) {
                holder.LayoutClick.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View) {


                    }
                })
            }
        })
        val mLayoutManager = LinearLayoutManager(this)
        rycleSectionStrength.layoutManager = mLayoutManager
        rycleSectionStrength.itemAnimator = DefaultItemAnimator()
        rycleSectionStrength.adapter = sectionAdapter
        sectionAdapter.notifyDataSetChanged()
    }
}