package com.vsnapnewschool.voicesnapmessenger.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.ClassWiseStrengthAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentNoticeBoardAdapter
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetClassWiseStrengthCallBack
import com.vsnapnewschool.voicesnapmessenger.Interfaces.classStrengthListener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.noticeboardListener
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetNoticeBoardResponse
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetClassWiseStrength
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.ClassStrengthID
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.activity_message_from_mangement_menu_screen.*
import kotlinx.android.synthetic.main.activity_teacher_school_strength.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.ArrayList

class TeacherSchoolStrength : BaseActivity(), View.OnClickListener, GetClassWiseStrengthCallBack {

    internal lateinit var classWiseStrengthAdapter: ClassWiseStrengthAdapter

    var classList = ArrayList<GetClassWiseStrength.ClassStrengthData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_school_strength)

        enableCrashLytics()
        initializeActionBar()
        setTitle(getString(R.string.title_school_strength))
        enableSearch(false)
        SchoolAPIServices.getOverAllStrength(this)

        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)


        val teacher = Util_shared_preferences.getTeacherCount(this)
        val student = Util_shared_preferences.getStudentCount(this)
        val girls = Util_shared_preferences.getGirlsCount(this)
        val boys = Util_shared_preferences.getBoysCount(this)
        val unspecified = Util_shared_preferences.getUnspecifiedCount(this)

        lblTeacherStrength.text = teacher
        lblStaffCount.text = student
        lblGirlsCount.text = girls
        lblBoysCount.text = boys
        lblUnSpecified.text = unspecified

        SchoolAPIServices.getClassWiseStrength(this, this)

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

    override fun callBackClassStrength(responseBody: GetClassWiseStrength) {
        classList.clear()
        classList= responseBody.data as ArrayList<GetClassWiseStrength.ClassStrengthData>
        classWiseStrengthAdapter = ClassWiseStrengthAdapter(classList, this, "0", object : classStrengthListener {
            override fun onClassStrength(
                holder: ClassWiseStrengthAdapter.MyViewHolder,
                item: GetClassWiseStrength.ClassStrengthData
            ) {
                holder.LayoutClick.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View) {

                        val pos = holder.adapterPosition
                        UtilConstants.ClassStrengthID = item.class_id
                        Log.d("ClassStrengthID", ClassStrengthID!!)
                        UtilConstants.sectionWiseStrength(this@TeacherSchoolStrength)


                    }
                })
            }
        })
        val mLayoutManager = LinearLayoutManager(this)
        rycleClassStrength.layoutManager = mLayoutManager
        rycleClassStrength.itemAnimator = DefaultItemAnimator()
        rycleClassStrength.adapter = classWiseStrengthAdapter
    }
}
