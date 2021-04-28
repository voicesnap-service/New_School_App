package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentImageAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.imageListener
import com.vsnapnewschool.voicesnapmessenger.Models.EventsImageClass
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.SelcetedFileList
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.activity_final_preview_events.*
import kotlinx.android.synthetic.main.activity_final_preview_images.*
import kotlinx.android.synthetic.main.activity_image_grid.*
import kotlinx.android.synthetic.main.activity_image_grid.recyleImages
import kotlinx.android.synthetic.main.scroll_preview_images.*

class FinalPreviewImages : BaseActivity(), View.OnClickListener {
    var imageadapter: ParentImageAdapter? ? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scroll_preview_images)
        lblrecipient = findViewById<TextView>(R.id.lblrecipient)
        rcyleRecipients = findViewById<RecyclerView>(R.id.rcyleRecipients)
        enableCrashLytics()
        initializeActionBar()
        setTitle(getString(R.string.txt_Preview))
        enableSearch(false)
        finalPreviewReceipientsAdpter(this)
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)
        btnImagePublish?.setOnClickListener(this)

        lblImageTitle.text = UtilConstants.Title

        Log.d("selectedlist",SelcetedFileList.size.toString())
        recyleImage.layoutManager = GridLayoutManager(this, 3)

        imageadapter = ParentImageAdapter(SelcetedFileList, this, object : imageListener {
            override fun onimageClick(holder: ParentImageAdapter.MyViewHolder, text_info: String) {

                holder.imgGrid.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View) {
                        UtilConstants.ImageViewScreen(this@FinalPreviewImages, true, text_info)

                    }
                })
            }
        })
        recyleImage.adapter = imageadapter


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
            R.id.btnImagePublish -> {
                if (UtilConstants.RecipientsType == UtilConstants.Standard) {
                    awsFileUpload(this, 0)
                } else if (UtilConstants.RecipientsType == UtilConstants.StandardSection) {
                    awsFileUpload(this, 0)
                } else if (UtilConstants.RecipientsType == UtilConstants.Students) {
                    awsFileUpload(this, 0)
                } else if (UtilConstants.RecipientsType == UtilConstants.Group) {
                    awsFileUpload(this, 0)
                } else if (UtilConstants.RecipientsType == UtilConstants.Staff) {
                    awsFileUpload(this, 0)
                }
            }

        }
    }
}