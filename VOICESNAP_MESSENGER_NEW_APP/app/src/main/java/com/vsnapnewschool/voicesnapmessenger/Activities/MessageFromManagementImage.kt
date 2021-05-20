package com.vsnapnewschool.voicesnapmessenger.Activities

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.ManagementImageAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentImageListAdapter
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetImageListCallBack
import com.vsnapnewschool.voicesnapmessenger.CallBacks.MsgFromManagementImageCallBack
import com.vsnapnewschool.voicesnapmessenger.CallBacks.ReadStatusCallBacak
import com.vsnapnewschool.voicesnapmessenger.Interfaces.managementImageListener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.parentImageListListener
import com.vsnapnewschool.voicesnapmessenger.Models.FilesImagePDF
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.Network.StudentAPIServices
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetImageFilesResponse
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.MessageFromMangementImageResponse
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.ArrayList

class MessageFromManagementImage : BaseActivity(), View.OnClickListener,
    MsgFromManagementImageCallBack,
    ReadStatusCallBacak {
    var imageadapter: ManagementImageAdapter? = null
    var imageList = ArrayList<MessageFromMangementImageResponse.ImageData>()
    var imageFilelist = ArrayList<MessageFromMangementImageResponse.ImageData.FileArray>()
    var path:String?=null
    var filename:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        enableCrashLytics()
        scrollAdds(this, imageSlider)
        initializeActionBar()
        enableSearch(true)
        setTitle(getString(R.string.title_Images))
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)
        lblSeeMore?.setOnClickListener(this)

        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)

        lblSeeMore?.setOnClickListener(this)
        lblSeeMore.setTextColor(Color.parseColor("#fb6b22"))
        UtilConstants.CLICKED_SEE_MORE =false
        TeacherBottomLayout.visibility=View.VISIBLE
        SchoolAPIServices.getMessageFromManagementImageMessage(
            this,
            this,
            UtilConstants.API_NORMAL,
            recyclerview,
            shimmerFrameLayout
        )

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
                SchoolAPIServices.getMessageFromManagementImageMessage(
                    this,
                    this,
                    UtilConstants.API_SEE_MORE,
                    recyclerview,
                    shimmerFrameLayout
                )
            }
        }
    }

    override fun callBackManagementImage(responseBody: MessageFromMangementImageResponse) {

        imageList.clear()
        imageList = responseBody!!.data as ArrayList<MessageFromMangementImageResponse.ImageData>
        imageadapter = ManagementImageAdapter(imageList, this,
            object : managementImageListener {
                override fun onManagementImageClick(
                    holder: ManagementImageAdapter.MyViewHolder,
                    item: MessageFromMangementImageResponse.ImageData
                ) {
                    holder.lblImageCount.setOnClickListener {

                        imageFilelist= item.file_array as ArrayList<MessageFromMangementImageResponse.ImageData.FileArray>
                        UtilConstants.ListFilesImages.clear()
                        imageFilelist.forEach {
                            path = it.file_path
                            filename = it.original_file_name
                            UtilConstants.ListFilesImages.add(FilesImagePDF(path!!, filename, "image",""))
                        }


                        if(imageFilelist.size==1){
                            UtilConstants.FileViewPopUPImagePdf(
                                this@MessageFromManagementImage,path!!,
                                "image",""
                            )
                        }else{
                            UtilConstants.viewFileActivity(
                                this@MessageFromManagementImage,
                                UtilConstants.ListFilesImages,
                                true,"Images"
                            )
                        }


                        if (item.is_archive == 0) {
                            StudentAPIServices.updateReadStatus(
                                this@MessageFromManagementImage,
                                item.header_id,
                                item.detail_id,
                                UtilConstants.API_NORMAL, this@MessageFromManagementImage
                            )
                        } else {
                            StudentAPIServices.updateReadStatus(
                                this@MessageFromManagementImage,
                                item.header_id,
                                item.detail_id,
                                UtilConstants.API_SEE_MORE,
                                this@MessageFromManagementImage
                            )
                        }
                    }
                }

            })
        val mLayoutManager2 = LinearLayoutManager(this)
        recyclerview.layoutManager = mLayoutManager2
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = imageadapter

        if (UtilConstants.CLICKED_SEE_MORE == true) {
            SchoolAPIServices.getMessageFromManagementImageMessage(
                this,
                this,
                UtilConstants.API_SEE_MORE,
                recyclerview,
                shimmerFrameLayout
            )
        }
    }

    override fun callBackManagementImage_Archive(responseBody: MessageFromMangementImageResponse) {

        UtilConstants.CLICKED_SEE_MORE = true
        lblSeeMore.visibility = View.GONE
        imageList.addAll(responseBody.data)
        imageadapter!!.notifyDataSetChanged()
    }

    override fun callBackReadStatus(updateStatus: Boolean?) {
        if (updateStatus == true) {
            getImagesAfterReadList()
        }
    }

    private fun getImagesAfterReadList() {
        SchoolAPIServices.getMessageFromManagementImageMessage(
            this,
            this,
            UtilConstants.API_NORMAL,
            recyclerview,
            shimmerFrameLayout
        )
    }

}
