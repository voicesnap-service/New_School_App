package com.vsnapnewschool.voicesnapmessenger.Fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.VoiceHistoryAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.voiceHistoryListener
import com.vsnapnewschool.voicesnapmessenger.Models.Text_Class
import com.vsnapnewschool.voicesnapmessenger.Network.APIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.VoiceHistoryData
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.*

class VoiceHistory : Fragment() {
    private val menulist = ArrayList<Text_Class>()
    internal lateinit var voiceadapter: VoiceHistoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.recyclerview_layout, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        APIServices.getVoiceHistoryListApi(context as Activity?)
        adds_layout.visibility=View.GONE
        voiceadapter = VoiceHistoryAdapter(UtilConstants.voiceHistoryList, activity, object : voiceHistoryListener {
            override fun voiceHistoryClick(holder: VoiceHistoryAdapter.MyViewHolder, text_info: VoiceHistoryData) {

                holder.imgsendvoice.setOnClickListener({
                    UtilConstants.finalPreviewVoiceMessage((context as Activity?)!!,true)
                })

            }
        })

        val mLayoutManager = LinearLayoutManager(activity)
        recyclerview.layoutManager = mLayoutManager
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = voiceadapter

    }
    private fun ImageLength() {
            var menus = Text_Class(
                "https://voicesnap-school-files.s3.amazonaws.com/File_20200907160702857.mp3",
                " 4233 Recipients ",
                " Sent Successfully ",
                "Sent on Jun 05 at 10.30 am"
            )
            menulist.add(menus)

            menus = Text_Class(
                "https://voicesnap-school-files.s3.amazonaws.com/File_20200907160702857.mp3",
                " 4233 Recipients ",
                " Sent Successfully ",
                "Sent on Jun 05 at 10.30 am"
            )
            menulist.add(menus)

            menus = Text_Class(
                "https://voicesnap-school-files.s3.amazonaws.com/File_20200907160702857.mp3",
                " 4233 Recipients ",
                " Sent Successfully ",
                "Sent on Jun 05 at 10.30 am"
            )
            menulist.add(menus)

            menus = Text_Class(
                "https://voicesnap-school-files.s3.amazonaws.com/File_20200907160702857.mp3",
                " 4233 Recipients ",
                " Sent Successfully ",
                "Sent on Jun 05 at 10.30 am"
            )
            menulist.add(menus)

            menus = Text_Class( "https://voicesnap-school-files.s3.amazonaws.com/File_20200907160702857.mp3",

                " 4233 Recipients ",
                " Sent Successfully ",
                "Sent on Jun 05 at 10.30 am"
            )
            menulist.add(menus)
    }

}



