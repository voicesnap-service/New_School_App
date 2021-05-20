package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentNoticeBoardAdapter
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetNoticeBoardCallBack
import com.vsnapnewschool.voicesnapmessenger.Interfaces.noticeboardListener
import com.vsnapnewschool.voicesnapmessenger.Models.Image_Class
import com.vsnapnewschool.voicesnapmessenger.Network.StudentAPIServices
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.EventsData
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetNoticeBoardResponse
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.UpcomingEventsResponse
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetTextData
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.*

class ParentNoticeBoard : BaseActivity(), View.OnClickListener, GetNoticeBoardCallBack {
    internal lateinit var NoticeBoardAdapter: ParentNoticeBoardAdapter

    var noticeBoardList = ArrayList<GetNoticeBoardResponse.NoticeData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        enableCrashLytics()
        scrollAdds(this, imageSlider)
        parentActionbar()
        setTitle(getString(R.string.title_notice_board))
        enableSearch(true)
        recyle_parent_bottom_layout.visibility = View.VISIBLE
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)



        StudentAPIServices.getNoticeBoard(this,this)

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

    override fun callBackNoticeBoard(responseBody: GetNoticeBoardResponse) {

        noticeBoardList.clear()
        noticeBoardList= responseBody.data as ArrayList<GetNoticeBoardResponse.NoticeData>
        NoticeBoardAdapter = ParentNoticeBoardAdapter(noticeBoardList, this, "0", object : noticeboardListener {
                override fun noticeboardClick(
                    holder: ParentNoticeBoardAdapter.MyViewHolder,
                    item: GetNoticeBoardResponse.NoticeData
                ) {
                    holder.rytHistory.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(v: View) {

                            val pos = holder.adapterPosition
                            // val context=holder.rytHistory.context
                            UtilConstants.parentNoticeboardHistoryActivity(this@ParentNoticeBoard,item,pos)


                        }
                    })
                }
            })
        val mLayoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = mLayoutManager
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = NoticeBoardAdapter

    }



}