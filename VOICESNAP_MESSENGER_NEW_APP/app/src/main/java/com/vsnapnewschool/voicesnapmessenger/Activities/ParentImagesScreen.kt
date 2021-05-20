package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentImageListAdapter
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetImageListCallBack
import com.vsnapnewschool.voicesnapmessenger.CallBacks.ReadStatusCallBacak
import com.vsnapnewschool.voicesnapmessenger.Interfaces.parentImageListListener
import com.vsnapnewschool.voicesnapmessenger.Models.FilesImagePDF
import com.vsnapnewschool.voicesnapmessenger.Network.StudentAPIServices
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetImageFilesResponse
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.ListFilesImages

import kotlinx.android.synthetic.main.activity_image_grid.parent_bottom_layout
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.ArrayList


class ParentImagesScreen : BaseActivity(), View.OnClickListener, GetImageListCallBack,
    ReadStatusCallBacak {
    var imageadapter: ParentImageListAdapter? = null
    var imageList = ArrayList<GetImageFilesResponse.GetImageData>()
    var imageFilelist = ArrayList<GetImageFilesResponse.GetImageData.FileArray>()
    var path:String?=null
    var filename:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        enableCrashLytics()
        scrollAdds(this, imageSlider)
        parentActionbar()
        enableSearch(true)
        setTitle(getString(R.string.title_Images))
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)
        lblSeeMore?.setOnClickListener(this)
        recyle_parent_bottom_layout.visibility = View.VISIBLE


        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)
        UtilConstants.CLICKED_SEE_MORE = false
        StudentAPIServices.getImageFiles(
            this,
            this,
            UtilConstants.API_NORMAL,
            recyclerview,
            shimmerFrameLayout
        )

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
            R.id.lblSeeMore -> {
                StudentAPIServices.getImageFiles(
                    this,
                    this,
                    UtilConstants.API_SEE_MORE,
                    recyclerview,
                    shimmerFrameLayout
                )
            }
        }
    }

    override fun callBackImageFiles(responseBody: GetImageFilesResponse) {

        imageList.clear()
        imageList = responseBody!!.data as ArrayList<GetImageFilesResponse.GetImageData>
        imageadapter = ParentImageListAdapter(imageList, this,
            object : parentImageListListener {
                override fun onImageListClick(
                    holder: ParentImageListAdapter.MyViewHolder,
                    item: GetImageFilesResponse.GetImageData
                ) {
                    holder.lblImageCount.setOnClickListener {

                        imageFilelist= item.file_array as ArrayList<GetImageFilesResponse.GetImageData.FileArray>
                        ListFilesImages.clear()
                        imageFilelist.forEach {
                            path = it.file_path
                             filename = it.original_file_name
                            ListFilesImages.add(FilesImagePDF(path!!, filename, "image",""))

                        }
                        if(imageFilelist.size==1){
                            Log.d("1 image",imageFilelist.size.toString())
                            UtilConstants.FileViewPopUPImagePdf(
                                this@ParentImagesScreen,path!!,
                                "image",""
                            )
                        }else{

                            UtilConstants.viewFileActivity(
                                this@ParentImagesScreen,
                                ListFilesImages,
                                true,"Images"
                            )
                        }


                        if (item.is_archive == 0) {
                            StudentAPIServices.updateReadStatus(
                                this@ParentImagesScreen,
                                item.header_id,
                                item.detail_id,
                                UtilConstants.API_NORMAL, this@ParentImagesScreen
                            )
                        } else {
                            StudentAPIServices.updateReadStatus(
                                this@ParentImagesScreen,
                                item.header_id,
                                item.detail_id,
                                UtilConstants.API_SEE_MORE,
                                this@ParentImagesScreen
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
            StudentAPIServices.getImageFiles(
                this,
                this,
                UtilConstants.API_SEE_MORE,
                recyclerview,
                shimmerFrameLayout
            )
        }
    }

    override fun callBackImageFiles_Archive(responseBody: GetImageFilesResponse) {

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
        StudentAPIServices.getImageFiles(
            this,
            this,
            UtilConstants.API_NORMAL,
            recyclerview,
            shimmerFrameLayout
        )
    }

}
