package com.vsnapnewschool.voicesnapmessenger.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.AllStaffsAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.ConferenceAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.SchoolListAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.SectionWiseStrengthAdapter
import com.vsnapnewschool.voicesnapmessenger.CallBacks.ConferenceCallBack
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetSectionWiseCallBack
import com.vsnapnewschool.voicesnapmessenger.Interfaces.checkConferenceListener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.sectionStrengthListener
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.AllStaffsData
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.ConferenceStaffResponse
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetSectionWiseStrength
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.activity_teacher_conference_screen.*
import kotlinx.android.synthetic.main.activity_teacher_section_wise_strength.*
import kotlinx.android.synthetic.main.choose_specific_sections.*
import kotlinx.android.synthetic.main.choose_specific_sections.recycleSections
import kotlinx.android.synthetic.main.choose_standard_group_staffs.*
import kotlinx.android.synthetic.main.choose_standard_group_staffs.chooseSelectAll
import java.util.ArrayList

class TeacherConferenceScreen : BaseActivity(), View.OnClickListener, checkConferenceListener,
    ConferenceCallBack {
    internal lateinit var conferencestaffAdapter: ConferenceAdapter
    var stafflist = ArrayList<ConferenceStaffResponse.ConferenceData>()
    private var SelectedStaffsList = ArrayList<ConferenceStaffResponse.ConferenceData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_conference_screen)

        initializeActionBar()
        setTitle(getString(R.string.title_staff_conference))
        enableSearch(false)
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)
        btnConferenceNext?.setOnClickListener(this)
        chooseSelectAll?.setOnClickListener(this)
        UtilConstants.isSelectAll = false

        SchoolAPIServices.getStaffForConference(this, this)

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
            R.id.btnConferenceNext -> {
                UtilConstants.SelectedConferenceList = SelectedStaffsList
                SchoolAPIServices.sendConfernceCall(this)

            }
            R.id.chooseSelectAll -> {

                if (chooseSelectAll.isChecked) {
                    SelectedStaffsList.clear()
                    SelectedStaffsList.addAll(stafflist)
                    UtilConstants.isSelectAll = true
                    setAllConferenceStaffsAdapter()
                    setNextBtnEnable()
                } else {
                    SelectedStaffsList.clear()
                    UtilConstants.isSelectAll = false
                    setAllConferenceStaffsAdapter()
                    setNextBtnEnable()

                }


            }
        }
    }

    private fun setNextBtnEnable() {
        if (SelectedStaffsList.size > 0) {
            btnConferenceNext.isEnabled = true
        } else {
            btnConferenceNext.isEnabled = false
        }
        if (stafflist.size == SelectedStaffsList.size) {
            chooseSelectAll.setChecked(true)
        } else {
            chooseSelectAll.setChecked(false)
        }


    }

    override fun callBackConference(responseBody: ConferenceStaffResponse) {
        SelectedStaffsList.clear()

        stafflist.clear()
        stafflist = responseBody.data as ArrayList<ConferenceStaffResponse.ConferenceData>
        setAllConferenceStaffsAdapter()

//        conferencestaffAdapter = ConferenceAdapter(stafflist, this, "0", object :
//            checkConferenceListener {
//            override fun onSectionStrength(
//                holder: SectionWiseStrengthAdapter.MyViewHolder,
//                item: GetSectionWiseStrength.SectionStrengthData
//            ) {
//                holder.LayoutClick.setOnClickListener(object : View.OnClickListener {
//                    override fun onClick(v: View) {
//
//
//
//                    }
//                })
//            }
//        })
//        val mLayoutManager = LinearLayoutManager(this)
//        rycleSectionStrength.layoutManager = mLayoutManager
//        rycleSectionStrength.itemAnimator = DefaultItemAnimator()
//        rycleSectionStrength.adapter = sectionAdapter
//        sectionAdapter.notifyDataSetChanged()
    }

    private fun setAllConferenceStaffsAdapter() {
        conferencestaffAdapter =
            ConferenceAdapter(this, this, stafflist, UtilConstants.isSelectAll!!)
        val mLayoutManager = LinearLayoutManager(this)
        recycleSections.layoutManager = mLayoutManager
        recycleSections.itemAnimator = DefaultItemAnimator()
        recycleSections.adapter = conferencestaffAdapter

        conferencestaffAdapter.notifyDataSetChanged()
    }

    override fun addStaff(data: ConferenceStaffResponse.ConferenceData?) {
        SelectedStaffsList.add(data!!)
        Log.d("SelectedStaffsList", SelectedStaffsList.size.toString())
        setNextBtnEnable()

    }

    override fun removeStaff(data: ConferenceStaffResponse.ConferenceData?) {
        SelectedStaffsList.remove(data!!)
        Log.d("SelectedStaffsList", SelectedStaffsList.size.toString())
        setNextBtnEnable()
    }


}