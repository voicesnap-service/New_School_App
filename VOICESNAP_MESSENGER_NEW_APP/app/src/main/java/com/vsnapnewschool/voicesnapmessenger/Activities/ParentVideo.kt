package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.VideoviewallAdapter
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetParentVideoCallBack
import com.vsnapnewschool.voicesnapmessenger.Interfaces.videoViewListener
import com.vsnapnewschool.voicesnapmessenger.Network.StudentAPIServices
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.ParentVideoDetail
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.ParentVideoResponse
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.activity_parent_video.*
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import java.util.*

class ParentVideo : BaseActivity(),View.OnClickListener,GetParentVideoCallBack{

    internal lateinit var videoViewAllAdapter: VideoviewallAdapter

    var ParentVideoDetails = ArrayList<ParentVideoDetail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_video)
        enableCrashLytics()
        parentActionbar()
        setTitle(getString(R.string.title_Video))
        enableSearch(true)
        scrollAdds(this,imageSlider)
        StudentAPIServices.ParentVideos(this,this)
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)





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


    override fun callbackvideo(responseBody: ParentVideoResponse) {


        ParentVideoDetails.clear()
        ParentVideoDetails= responseBody.data as ArrayList<ParentVideoDetail>
        videoViewAllAdapter = VideoviewallAdapter(
            ParentVideoDetails,
            this@ParentVideo,
            object : videoViewListener {
                override fun videoViewClick(
                    holder: VideoviewallAdapter.MyViewHolder,
                    item: ParentVideoDetail
                ) {
                    holder.video.setOnClickListener({
                        UtilConstants.parentVideoViewActivity(this@ParentVideo, item)
                    })
                }

            })
        val mLayoutManager = LinearLayoutManager(this)
        rcyVideo.layoutManager = mLayoutManager
        rcyVideo.itemAnimator = DefaultItemAnimator()
        rcyVideo.adapter = videoViewAllAdapter
        videoViewAllAdapter.notifyDataSetChanged()


    }

}
