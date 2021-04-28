package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.SchoolListAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.checkSchoolListListener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.schoolClickListener
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StaffDetailData
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.notice_public.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import kotlinx.android.synthetic.main.recyclerview_layout.recyclerview
import kotlinx.android.synthetic.main.school_list_recycle.*

class TeacherSchoolsListScreen : BaseActivity(), checkSchoolListListener,View.OnClickListener {
    internal lateinit var schoolsAdapter: SchoolListAdapter
    private var SelectedSchoolsList = ArrayList<StaffDetailData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.school_list_recycle)
        enableCrashLytics()
        initializeActionBar()
        setTitle("Schools List")
        enableSearch(false)
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)
        btnNext?.setOnClickListener(this)
        if(UtilConstants.MENU_TYPE == UtilConstants.MENU_EMERGENCY){
            btnNext.visibility=View.VISIBLE
        }
        else{
            btnNext.visibility=View.GONE
        }
        btnNext.setOnClickListener(this)
        schoolsAdapter = SchoolListAdapter(UtilConstants.SchoolListDetails,this, this,object : schoolClickListener {
            override fun onclickListener(holder: SchoolListAdapter.MyViewHolder, schools_info: StaffDetailData) {
                holder.constSchools.setOnClickListener({
                    if(UtilConstants.MENU_TYPE != UtilConstants.MENU_EMERGENCY) {
                        UtilConstants.SchoolID = schools_info.school_id
                        Log.d("adapterschool",schools_info.school_id!!)

                        Log.d("Schoollistscholl",UtilConstants.SchoolID!!)
                      //  UtilConstants.StaffID = schools_info.staff_id
                        Log.d("SchoollistStaff",UtilConstants.StaffID!!)
                        Log.d("adapterschool",schools_info.staff_id!!)

                        UtilConstants.openSchoolMenuScreens(this@TeacherSchoolsListScreen)
                    }
                })
            }
        })
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerview.layoutManager = mLayoutManager
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = schoolsAdapter
        schoolsAdapter.notifyDataSetChanged()

    }

    override fun add(schools: StaffDetailData?) {
        SelectedSchoolsList.add(schools!!)
        setButtonEnable()

    }
    override fun remove(schools: StaffDetailData?) {
        SelectedSchoolsList.remove(schools!!)
        setButtonEnable()
    }

    private fun setButtonEnable() {
        if(SelectedSchoolsList.size>0){
            btnNext.isEnabled=true
        }
        else{
            btnNext.isEnabled=false

        }
    }
     override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btnNext -> {
                UtilConstants.SelectedFinalSchoolsList=SelectedSchoolsList
              SchoolAPIServices.sendEmergencyVoiceToSchools(this)
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
