package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentHomeworkAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentHomeworkDayAdapter
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetDatesHomeWorkCallBack
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetHomeWorkCallBack
import com.vsnapnewschool.voicesnapmessenger.Interfaces.homeworkListener
import com.vsnapnewschool.voicesnapmessenger.Network.StudentAPIServices
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.*
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.*

class ParentHomeWork : BaseActivity(), View.OnClickListener,GetDatesHomeWorkCallBack ,GetHomeWorkCallBack {
    var datehomeworklist = ArrayList<GetDatesHomeWorkListResponse.GetDatelist>()
    var homeworklist = ArrayList<GetHomeWorkListResponse.ParentHomeworklist>()
    internal lateinit var parenthomeworkdayadapter: ParentHomeworkDayAdapter
    internal lateinit var homeworkAdapter: ParentHomeworkAdapter
    var type:String="0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        enableCrashLytics()
        parentActionbar()
        setTitle(getString(R.string.title_Homework))
        enableSearch(true)
        scrollAdds(this, imageSlider)
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)
        recyle_parent_bottom_layout.visibility = View.VISIBLE

        StudentAPIServices.getDatesHomework(this, this)
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

    override fun callBackDatesHomework(responseBody: GetDatesHomeWorkListResponse) {
        datehomeworklist.clear()
        datehomeworklist= responseBody.data as ArrayList<GetDatesHomeWorkListResponse.GetDatelist>
        parenthomeworkdayadapter = ParentHomeworkDayAdapter(datehomeworklist, this,object : ParentHomeworkDayAdapter.BtnClickListener {
            override fun onBtnClick(position: Int,datelist: GetDatesHomeWorkListResponse.GetDatelist) {
                homeworklist.clear()
                UtilConstants.Datehomework=datelist.homework_date
                StudentAPIServices.getHomework(this@ParentHomeWork,this@ParentHomeWork)
            }

        })
        val mLayoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL ,false)
        rcyhorizontal.layoutManager = mLayoutManager
        rcyhorizontal.itemAnimator = DefaultItemAnimator()
        rcyhorizontal.adapter = parenthomeworkdayadapter
        parenthomeworkdayadapter.notifyDataSetChanged()
    }

    override fun callBackHomework(responseBody: GetHomeWorkListResponse) {
        homeworklist.clear()
        homeworklist= responseBody.data as ArrayList<GetHomeWorkListResponse.ParentHomeworklist>
        homeworkAdapter = ParentHomeworkAdapter(homeworklist, this,"0",object: homeworkListener{
            override fun onhomeworkClick(
                holder: ParentHomeworkAdapter.MyViewHolder,
                item: GetHomeWorkListResponse.ParentHomeworklist
            ) {
                UtilConstants.parentHomeworkHistoryActivityt(this@ParentHomeWork,"0",item)

            }


        })
        val mLayoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = mLayoutManager
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = homeworkAdapter
        homeworkAdapter.notifyDataSetChanged()
    }
}