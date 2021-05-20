package com.vsnapnewschool.voicesnapmessenger.Activities

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Rest.APIClient
import com.vsnapnewschool.voicesnapmessenger.Adapters.ChatMembersAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.VideoviewallAdapter
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetStaffClassesChatCallBack
import com.vsnapnewschool.voicesnapmessenger.Interfaces.chatmemberListener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.videoViewListener
import com.vsnapnewschool.voicesnapmessenger.Network.ApiInterface
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StaffChatClassDetail
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StaffChatClassResponse
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StatusMessageResponse
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import com.vsnapnewschool.voicesnapmessenger.Util_Common.GifLoading
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.activity_parent_video.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import kotlinx.android.synthetic.main.recyclerview_layout.adds_layout
import retrofit2.Call
import retrofit2.Response
import java.util.*

class TeacherChatScreen : BaseActivity(),View.OnClickListener, GetStaffClassesChatCallBack {
    internal lateinit var ChatMemberAdapter: ChatMembersAdapter
    var StaffClassDetails = ArrayList<StaffChatClassDetail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        enableCrashLytics()
        initializeActionBar()
        setTitle(getString(R.string.title_Chat))
        enableSearch(true)

        SchoolAPIServices.getStaffClassesforChat(this,this)

        TeacherBottomLayout.visibility=View.VISIBLE
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)
        TeacherBottomLayout.visibility= View.VISIBLE
        recyle_parent_bottom_layout.visibility= View.GONE
        adds_layout.visibility= View.GONE



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


    override fun callbackstaffclasseschat(responseBody: StaffChatClassResponse) {
        StaffClassDetails.clear()
        StaffClassDetails= responseBody.data as ArrayList<StaffChatClassDetail>
        ChatMemberAdapter = ChatMembersAdapter(StaffClassDetails, this, false,object : chatmemberListener {
            override fun onchatclickListener(holder: ChatMembersAdapter.MyViewHolder, text_info: StaffChatClassDetail) {
                holder.overall.setOnClickListener({
                    val context=holder.overall.context
                    UtilConstants.teacherChatActivity(this@TeacherChatScreen,text_info)

                })
            }
        })
        val mLayoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = mLayoutManager
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = ChatMemberAdapter
        ChatMemberAdapter.notifyDataSetChanged()
    }
}
