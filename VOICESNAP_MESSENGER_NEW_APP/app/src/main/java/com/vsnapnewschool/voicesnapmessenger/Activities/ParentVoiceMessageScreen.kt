package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentVoiceAdapter
import com.vsnapnewschool.voicesnapmessenger.CallBacks.ReadStatusCallBacak
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetVoiceMessageCallBack
import com.vsnapnewschool.voicesnapmessenger.Interfaces.VoiceMessagesClickListener
import com.vsnapnewschool.voicesnapmessenger.Network.StudentAPIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetVoiceData
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetVoiceMessages
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.ArrayList

class ParentVoiceMessageScreen : BaseActivity(), View.OnClickListener, GetVoiceMessageCallBack, ReadStatusCallBacak {
    internal lateinit var parentVoiceMessageAdapter: ParentVoiceAdapter
    var voiceMessageList = ArrayList<GetVoiceData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        enableCrashLytics()
        scrollAdds(this, imageSlider)
        parentActionbar()
        enableSearch(true)
        setTitle(getString(R.string.title_Communication))
        parent_bottom_layout.visibility = View.VISIBLE
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)
        lblSeeMore?.setOnClickListener(this)

        UtilConstants.CLICKED_SEE_MORE = false
        StudentAPIServices.getVoiceMessages(
            this@ParentVoiceMessageScreen, this,
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

    override fun callBackVoiceMessages(responseBody: GetVoiceMessages) {
        voiceMessageList.clear()
        voiceMessageList= responseBody!!.data as ArrayList<GetVoiceData>
        parentVoiceMessageAdapter = ParentVoiceAdapter(
            voiceMessageList,
            this,
            object : VoiceMessagesClickListener {
                override fun onVoiceClick(
                    holder: ParentVoiceAdapter.MyViewHolder,
                    text_info: GetVoiceData) {
                    holder.rytContainer.setOnClickListener({

                        //UtilConstants.viewMessagePopup(this@ParentVoiceMessages, text_info)

                        if(text_info.is_archive == 0){
                            StudentAPIServices.updateReadStatus(this@ParentVoiceMessageScreen,text_info.header_id,text_info.detail_id, UtilConstants.API_NORMAL,this@ParentVoiceMessageScreen)
                        }
                        else{
                            StudentAPIServices.updateReadStatus(this@ParentVoiceMessageScreen,text_info.header_id,text_info.detail_id, UtilConstants.API_SEE_MORE,this@ParentVoiceMessageScreen)
                        }
                    })
                }

            })
        val mLayoutManager2 = LinearLayoutManager(this)
        recyclerview.layoutManager = mLayoutManager2
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = parentVoiceMessageAdapter

        if(UtilConstants.CLICKED_SEE_MORE == true){
            StudentAPIServices.getVoiceMessages(
                this@ParentVoiceMessageScreen,
                this,
                UtilConstants.API_SEE_MORE,
                recyclerview,
                shimmerFrameLayout
            )
        }
    }
    override fun callBackVoiceMessages_Archive(responseBody: GetVoiceMessages) {
        UtilConstants.CLICKED_SEE_MORE =true
        lblSeeMore.visibility=View.GONE
        voiceMessageList.addAll(responseBody.data)
        parentVoiceMessageAdapter.notifyDataSetChanged()
    }

    override fun callBackReadStatus(updateStatus: Boolean?) {
        if(updateStatus == true){
            getVoiceMessagesAfterReadList()
        }
    }

    private fun getVoiceMessagesAfterReadList() {
        StudentAPIServices.getVoiceMessages(
            this@ParentVoiceMessageScreen,
            this,
            UtilConstants.API_NORMAL,
            recyclerview,
            shimmerFrameLayout
        )
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
                StudentAPIServices.getVoiceMessages(
                    this@ParentVoiceMessageScreen,
                    this,
                    UtilConstants.API_SEE_MORE,
                    recyclerview,
                    shimmerFrameLayout
                )
            }
        }
    }
}