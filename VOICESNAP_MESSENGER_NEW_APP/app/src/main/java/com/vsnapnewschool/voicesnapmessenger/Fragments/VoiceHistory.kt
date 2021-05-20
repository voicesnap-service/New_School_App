package com.vsnapnewschool.voicesnapmessenger.Fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Rest.APIClient
import com.vsnapnewschool.voicesnapmessenger.Adapters.AllStandardAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.VoiceHistoryAdapter
import com.vsnapnewschool.voicesnapmessenger.CallBacks.TeacherVoiceHistoryCallBack
import com.vsnapnewschool.voicesnapmessenger.Interfaces.voiceHistoryListener
import com.vsnapnewschool.voicesnapmessenger.Models.Text_Class
import com.vsnapnewschool.voicesnapmessenger.Network.ApiInterface
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetVoiceHistory
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StatusMessageResponse
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.VoiceHistoryData
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.TabPosition
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import com.vsnapnewschool.voicesnapmessenger.Util_Common.GifLoading
import kotlinx.android.synthetic.main.recyclerview_layout.*
import retrofit2.Call
import retrofit2.Response
import java.util.*

class VoiceHistory : Fragment(), TeacherVoiceHistoryCallBack {
    var voiceHistoryList = ArrayList<VoiceHistoryData>()
    var voiceadapter: VoiceHistoryAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recyclerview_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val Tabposition: Int? = Util_shared_preferences.getTabPosition(activity)
        Log.d("activitytabposition",Tabposition.toString())

        if (Tabposition == 1) {
            Log.d("tab",Tabposition.toString())
            SchoolAPIServices.getVoiceHistoryListApi(activity,this)
            //  getVoiceHistoryListApi()
            adds_layout.visibility = View.GONE
            lblSeeMore.visibility = View.GONE
            recyclerview.visibility = View.VISIBLE
        }
    }



    override fun callBackHistoryList(responseBody: GetVoiceHistory) {
        voiceHistoryList.clear()
        voiceHistoryList = responseBody.data as ArrayList<VoiceHistoryData>

        voiceadapter = VoiceHistoryAdapter(
            voiceHistoryList,
            (context as Activity?)!!,
            object : voiceHistoryListener {
                override fun voiceHistoryClick(
                    holder: VoiceHistoryAdapter.MyViewHolder,
                    item: VoiceHistoryData
                ) {

                    holder.imgsendvoice.setOnClickListener({
                        UtilConstants.finalPreviewVoiceMessage((context as Activity?)!!, true)
                    })

                }
            })


        val mLayoutManager = LinearLayoutManager(activity)
        recyclerview.layoutManager = mLayoutManager
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = voiceadapter
        voiceadapter?.notifyDataSetChanged()



    }



}



