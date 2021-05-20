package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentImageAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.imageListener
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.SelcetedFileList
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.activity_final_preview_assignment.*
import kotlinx.android.synthetic.main.activity_final_preview_circular.*
import kotlinx.android.synthetic.main.activity_final_preview_events.*
import kotlinx.android.synthetic.main.activity_image_grid.recyleImages
import kotlinx.android.synthetic.main.scroll_preview_events.*
import kotlinx.android.synthetic.main.scroll_preview_voice.*

class FinalPreviewEvents : BaseActivity(), View.OnClickListener {
    var imageadapter: ParentImageAdapter? = null
    private val menulist = ArrayList<String>()
    var pathind: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scroll_preview_events)
        lblrecipient = findViewById<TextView>(R.id.lblrecipient)
        rcyleRecipients = findViewById<RecyclerView>(R.id.rcyleRecipients)
        enableCrashLytics()
        initializeActionBar()
        setTitle(getString(R.string.txt_Preview))
        enableSearch(false)
        finalPreviewReceipientsAdpter(this)
        btnEventPublish?.setOnClickListener(this)
        lblSendOn.visibility = View.INVISIBLE
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)

        lblEventTitle.text = UtilConstants.Title
        lblEventDetails.text = UtilConstants.Description

        if(SelcetedFileList.size==0){
            recyleImages.visibility=View.GONE
        }else{
            recyleImages.visibility=View.VISIBLE

        }
        recyleImages.layoutManager = GridLayoutManager(this, 3)

        Log.d("selectedEventslist",SelcetedFileList.size.toString())

        imageadapter = ParentImageAdapter(SelcetedFileList, this, object : imageListener {
            override fun onimageClick(holder: ParentImageAdapter.MyViewHolder, text_info: String) {

                holder.imgGrid.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View) {
                        UtilConstants.ImageViewScreen(this@FinalPreviewEvents, true, text_info)

                    }
                })
            }
        })
        recyleImages.adapter = imageadapter


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
            R.id.btnEventPublish -> {

                awsFileUpload(this, pathind)
//
//                if (UtilConstants.RecipientsType == UtilConstants.Standard) {
//                    awsFileUpload(this, pathind)
//                } else if (UtilConstants.RecipientsType == UtilConstants.StandardSection) {
//                    awsFileUpload(this, pathind)
//                } else if (UtilConstants.RecipientsType == UtilConstants.Students) {
//                    awsFileUpload(this, pathind)
//                } else if (UtilConstants.RecipientsType == UtilConstants.Group) {
//                    awsFileUpload(this, pathind)
//                } else if (UtilConstants.RecipientsType == UtilConstants.Staff) {
//                    awsFileUpload(this, pathind)
//                }
            }

        }
    }

}