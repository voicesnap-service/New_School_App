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
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentChatMembersAdapter
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetStudentStaffChatCallBack
import com.vsnapnewschool.voicesnapmessenger.Interfaces.chatmemberListener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.parentchatmemberListener
import com.vsnapnewschool.voicesnapmessenger.Network.ApiInterface
import com.vsnapnewschool.voicesnapmessenger.Network.StudentAPIServices
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.StaffChatDetails
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.StudentStaffChatResponse
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.*
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import com.vsnapnewschool.voicesnapmessenger.Util_Common.GifLoading
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import retrofit2.Call
import retrofit2.Response
import java.util.*

class ParentChatScreen : BaseActivity(),View.OnClickListener ,GetStudentStaffChatCallBack{
    internal lateinit var chatMembersAdapter: ParentChatMembersAdapter
    var StudentStaffDetails = ArrayList<StaffChatDetails>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.recyclerview_layout)
        enableCrashLytics()
        scrollAdds(this,imageSlider)
        parentActionbar()
        setTitle(getString(R.string.title_Chat))
        enableSearch(true)
        recyle_parent_bottom_layout.visibility= View.VISIBLE
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)
        StudentAPIServices.StudentStaffforChat(this,this)

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


    override fun callbackstudentstaffchat(responseBody: StudentStaffChatResponse) {
        StudentStaffDetails.clear()
        StudentStaffDetails= responseBody.data as ArrayList<StaffChatDetails>
        chatMembersAdapter = ParentChatMembersAdapter(StudentStaffDetails, this, false,object :
            parentchatmemberListener {
            override fun onchatclickListener(
                holder: ParentChatMembersAdapter.MyViewHolder,
                item: StaffChatDetails
            ) {
                holder.overall.setOnClickListener({
                    val context=holder.overall.context
                    UtilConstants.parentChatMemberActivity(context,item)
                })
            }

        })
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerview.layoutManager = mLayoutManager
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = chatMembersAdapter
        chatMembersAdapter.notifyDataSetChanged()
    }

}
