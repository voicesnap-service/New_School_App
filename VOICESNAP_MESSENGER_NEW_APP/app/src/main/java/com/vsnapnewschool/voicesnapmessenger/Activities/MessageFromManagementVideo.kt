package com.vsnapnewschool.voicesnapmessenger.Activities

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Rest.APIClient
import com.vsnapnewschool.voicesnapmessenger.Adapters.ManagementTextAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.ManagementVideoAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.VideoviewallAdapter
import com.vsnapnewschool.voicesnapmessenger.CallBacks.MsgFromManagementVideoCallBack
import com.vsnapnewschool.voicesnapmessenger.CallBacks.ReadStatusCallBacak
import com.vsnapnewschool.voicesnapmessenger.Interfaces.managementTextListener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.managementVideoListener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.videoViewListener
import com.vsnapnewschool.voicesnapmessenger.Network.ApiInterface
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.Network.StudentAPIServices
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.ParentVideoDetail
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.ParentVideoResponse
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.MessageFromManagementTextResponse
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.MessageFromManagementVideoResponse
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StatusMessageResponse
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import com.vsnapnewschool.voicesnapmessenger.Util_Common.GifLoading
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.activity_parent_video.*
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import retrofit2.Call
import retrofit2.Response
import java.util.ArrayList

class MessageFromManagementVideo : BaseActivity(), View.OnClickListener,
    MsgFromManagementVideoCallBack, ReadStatusCallBacak {

    internal lateinit var videoViewAllAdapter: ManagementVideoAdapter

    var videolist = ArrayList<MessageFromManagementVideoResponse.VideoData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_video)
        enableCrashLytics()
        initializeActionBar()
        setTitle(getString(R.string.title_Video))
        enableSearch(true)
        TeacherBottomLayout.visibility=View.VISIBLE

        lblSeeMore?.setOnClickListener(this)
        lblSeeMore.setTextColor(Color.parseColor("#fb6b22"))
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)


        UtilConstants.CLICKED_SEE_MORE =false
        SchoolAPIServices.getMessageFromManagementVideoMessage(this, this,
            UtilConstants.API_NORMAL,recyclerview,shimmerFrameLayout)


    }

    override fun onResume() {
        super.onResume()
        shimmerFrameLayout.startShimmerAnimation()
    }

    override fun onPause() {
        shimmerFrameLayout.stopShimmerAnimation()
        super.onPause()
    }
    override fun onCreateContextMenu(
        menu: ContextMenu, v: View,
        menuInfo: ContextMenu.ContextMenuInfo?) {
        menu.add(0, v.id, 0, "Copy")
        val textView = v as TextView
        val manager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", textView.text)
        manager.setPrimaryClip(clipData)
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
            R.id.lblSeeMore -> {
                SchoolAPIServices.getMessageFromManagementVideoMessage(
                    this,
                    this,
                    UtilConstants.API_SEE_MORE,
                    recyclerview,
                    shimmerFrameLayout
                )
            }
        }
    }




    private fun getVideoMessagesAfterReadList() {
        SchoolAPIServices.getMessageFromManagementVideoMessage(
            this,
            this,
            UtilConstants.API_NORMAL,
            recyclerview,
            shimmerFrameLayout
        )
    }

    override fun callBackReadStatus(updateStatus: Boolean?) {
        if(updateStatus == true){
            getVideoMessagesAfterReadList()
        }
    }

    override fun callBackManagementVideo(responseBody: MessageFromManagementVideoResponse) {

        videolist.clear()
        videolist= responseBody.data as ArrayList<MessageFromManagementVideoResponse.VideoData>


        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        rcyVideo.setLayoutManager(mLayoutManager)
        rcyVideo.setItemAnimator(DefaultItemAnimator())

        videoViewAllAdapter = ManagementVideoAdapter(videolist,
            this, object : managementVideoListener {
                override fun onManagementVideoClick(holder: ManagementVideoAdapter.MyViewHolder, item: MessageFromManagementVideoResponse.VideoData) {
                    holder.video.setOnClickListener({
                        UtilConstants.managementVideoViewActivity(this@MessageFromManagementVideo, item)

                        if(item.is_archive == 0){
                            StudentAPIServices.updateReadStatus(this@MessageFromManagementVideo,item.header_id,item.detail_id, UtilConstants.API_NORMAL,this@MessageFromManagementVideo)
                        }
                        else{
                            StudentAPIServices.updateReadStatus(this@MessageFromManagementVideo,item.header_id,item.detail_id, UtilConstants.API_SEE_MORE,this@MessageFromManagementVideo)
                        }
                    })
                }


            })
        Log.d("size",videolist.size.toString())
        rcyVideo.setAdapter(videoViewAllAdapter)
        videoViewAllAdapter.notifyDataSetChanged()


        if(UtilConstants.CLICKED_SEE_MORE == true){
            SchoolAPIServices.getMessageFromManagementVideoMessage(
                this@MessageFromManagementVideo,
                this,
                UtilConstants.API_SEE_MORE,
                recyclerview,
                shimmerFrameLayout
            )
        }
    }

    override fun callBackManagementVideo_Archive(responseBody: MessageFromManagementVideoResponse) {
        UtilConstants.CLICKED_SEE_MORE =true
        lblSeeMore.visibility= View.GONE
        videolist.addAll(responseBody.data)
        videoViewAllAdapter.notifyDataSetChanged()
    }


}
