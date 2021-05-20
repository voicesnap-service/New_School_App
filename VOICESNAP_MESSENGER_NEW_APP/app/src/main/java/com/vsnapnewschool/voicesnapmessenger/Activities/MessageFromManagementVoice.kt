package com.vsnapnewschool.voicesnapmessenger.Activities

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.ManagementTextAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.ManagementVoiceAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentVoiceAdapter
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetVoiceMessageCallBack
import com.vsnapnewschool.voicesnapmessenger.CallBacks.MsgFromManagementCallBack
import com.vsnapnewschool.voicesnapmessenger.CallBacks.MsgFromManagementVoiceCallBack
import com.vsnapnewschool.voicesnapmessenger.CallBacks.ReadStatusCallBacak
import com.vsnapnewschool.voicesnapmessenger.Interfaces.VoiceMessagesClickListener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.managementTextListener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.managementVoiceListener
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.Network.StudentAPIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetVoiceData
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetVoiceMessages
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.MessageFromManagementTextResponse
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.MessageFromManagementVoiceResponse
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.ArrayList

class MessageFromManagementVoice : BaseActivity(), View.OnClickListener,
    MsgFromManagementVoiceCallBack, ReadStatusCallBacak {
    internal lateinit var managementVoiceAdapter: ManagementVoiceAdapter
    var voiceMessageList = ArrayList<MessageFromManagementVoiceResponse.VoiceData>()
    private var mediaPlayer = MediaPlayer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        enableCrashLytics()
        scrollAdds(this, imageSlider)
        teacherActionbarforVoice(mediaPlayer)
        enableSearch(true)
        setTitle(getString(R.string.title_Voice))
        TeacherBottomLayout.visibility=View.VISIBLE
        lblSeeMore?.setOnClickListener(this)
        lblSeeMore.setTextColor(Color.parseColor("#fb6b22"))
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)
        lblSeeMore?.setOnClickListener(this)
        lblSeeMore?.setOnClickListener(this)
        lblSeeMore.setTextColor(Color.parseColor("#fb6b22"))

        UtilConstants.CLICKED_SEE_MORE = false
        SchoolAPIServices.getMessageFromManagementVoiceMessage(
            this, this,
            UtilConstants.API_NORMAL, recyclerview, shimmerFrameLayout
        )

    }

    override fun onResume() {
        super.onResume()
        shimmerFrameLayout.startShimmerAnimation()
    }

    override fun onPause() {
        shimmerFrameLayout.stopShimmerAnimation()
        super.onPause()
    }

    override fun callBackManagementVoice(responseBody: MessageFromManagementVoiceResponse) {
        voiceMessageList.clear()
        voiceMessageList = responseBody!!.data as ArrayList<MessageFromManagementVoiceResponse.VoiceData>
        managementVoiceAdapter = ManagementVoiceAdapter(voiceMessageList, this,
            object : managementVoiceListener {
                override fun onManagementVoiceClick(
                    holder: ManagementVoiceAdapter.MyViewHolder,
                    text_info: MessageFromManagementVoiceResponse.VoiceData
                ) {
                    holder.rytContainer.setOnClickListener({

                        //UtilConstants.viewMessagePopup(this@ParentVoiceMessages, text_info)

                        if (text_info.is_archive == 0) {
                            StudentAPIServices.updateReadStatus(
                                this@MessageFromManagementVoice,
                                text_info.header_id,
                                text_info.detail_id,
                                UtilConstants.API_NORMAL,
                                this@MessageFromManagementVoice
                            )
                        } else {
                            StudentAPIServices.updateReadStatus(
                                this@MessageFromManagementVoice,
                                text_info.header_id,
                                text_info.detail_id,
                                UtilConstants.API_SEE_MORE,
                                this@MessageFromManagementVoice
                            )
                        }

                    })
                }

//                override fun onrefresh() {
//                    recyclerview.adapter = managementVoiceAdapter
//
//                }

            })
        val mLayoutManager2 = LinearLayoutManager(this)
        recyclerview.layoutManager = mLayoutManager2
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = managementVoiceAdapter

        if (UtilConstants.CLICKED_SEE_MORE == true) {
            SchoolAPIServices.getMessageFromManagementVoiceMessage(
                this,
                this,
                UtilConstants.API_SEE_MORE,
                recyclerview,
                shimmerFrameLayout
            )
        }
    }

    override fun callBackManagementVoice_Archive(responseBody: MessageFromManagementVoiceResponse) {
        UtilConstants.CLICKED_SEE_MORE = true
        lblSeeMore.visibility = View.GONE
        voiceMessageList.addAll(responseBody.data)
        managementVoiceAdapter.notifyDataSetChanged()
    }

    override fun callBackReadStatus(updateStatus: Boolean?) {
        if (updateStatus == true) {
            getVoiceMessagesAfterReadList()
        }
    }

    private fun getVoiceMessagesAfterReadList() {
        SchoolAPIServices.getMessageFromManagementVoiceMessage(
            this,
            this,
            UtilConstants.API_NORMAL,
            recyclerview,
            shimmerFrameLayout
        )
    }

    override fun onBackPressed() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        super.onBackPressed()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imgchat -> {
            }
            R.id.imgHomeMenu -> {
                UtilConstants.imgHomeIntent(this)
            }
            R.id.imgSettings -> {
                UtilConstants.imgProfileIntent(this)
            }
            R.id.lblSeeMore -> {
                SchoolAPIServices.getMessageFromManagementVoiceMessage(
                    this,
                    this,
                    UtilConstants.API_SEE_MORE,
                    recyclerview,
                    shimmerFrameLayout
                )
            }
        }
    }
}