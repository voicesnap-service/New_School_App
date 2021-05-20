package com.vsnapnewschool.voicesnapmessenger.Activities

import android.content.ClipData
import android.content.ClipboardManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.CircularHistoryAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.ManagementImageAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.ManagementPdfAdapter
import com.vsnapnewschool.voicesnapmessenger.CallBacks.MsgFromManagementImageCallBack
import com.vsnapnewschool.voicesnapmessenger.CallBacks.MsgFromManagementPdfCallBack
import com.vsnapnewschool.voicesnapmessenger.CallBacks.ReadStatusCallBacak
import com.vsnapnewschool.voicesnapmessenger.Interfaces.circularHistorylistener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.managementImageListener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.managementPdfListener
import com.vsnapnewschool.voicesnapmessenger.Models.FilesImagePDF
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.Network.StudentAPIServices
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetPdfFilesResponse
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.MessageFromManagementPdfResponse
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.MessageFromMangementImageResponse
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.ArrayList

class MessageFromManagementPdf : BaseActivity(),
    View.OnClickListener, MsgFromManagementPdfCallBack, ReadStatusCallBacak {
    var pdfAdapter: ManagementPdfAdapter? = null
    var pdfList = ArrayList<MessageFromManagementPdfResponse.PdfData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        enableCrashLytics()
        scrollAdds(this, imageSlider)
        initializeActionBar()
        enableSearch(true)
        setTitle(getString(R.string.title_Pdf))
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)
        lblSeeMore?.setOnClickListener(this)
        lblSeeMore.setTextColor(Color.parseColor("#fb6b22"))
        UtilConstants.CLICKED_SEE_MORE =false
        TeacherBottomLayout.visibility=View.VISIBLE
        SchoolAPIServices.getMessageFromManagementPdfMessage(
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
                SchoolAPIServices.getMessageFromManagementPdfMessage(
                    this,
                    this,
                    UtilConstants.API_SEE_MORE,
                    recyclerview,
                    shimmerFrameLayout
                )
            }
        }
    }

    override fun callBackManagementPdf(responseBody: MessageFromManagementPdfResponse) {

        pdfList.clear()
        pdfList = responseBody.data as ArrayList<MessageFromManagementPdfResponse.PdfData>
        Log.d("pdflist", pdfList.size.toString())

        pdfAdapter = ManagementPdfAdapter(pdfList, this, "0", object : managementPdfListener {
                override fun onpdfclickListener(
                    holder: ManagementPdfAdapter.MyViewHolder,
                    item: MessageFromManagementPdfResponse.PdfData
                ) {
                    holder.CircularLayout.setOnClickListener({
                        UtilConstants.managementPdfViewScreen(this@MessageFromManagementPdf, item)

                        if (item.is_archive == 0) {
                            StudentAPIServices.updateReadStatus(
                                this@MessageFromManagementPdf,
                                item.header_id,
                                item.detail_id,
                                UtilConstants.API_NORMAL, this@MessageFromManagementPdf
                            )
                        } else {
                            StudentAPIServices.updateReadStatus(
                                this@MessageFromManagementPdf,
                                item.header_id,
                                item.detail_id,
                                UtilConstants.API_SEE_MORE,
                                this@MessageFromManagementPdf
                            )
                        }
                    })
                }
            })

        val mLayoutManager2 = LinearLayoutManager(this)
        recyclerview.layoutManager = mLayoutManager2
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = pdfAdapter

        if (UtilConstants.CLICKED_SEE_MORE == true) {
            SchoolAPIServices.getMessageFromManagementPdfMessage(
                this,
                this,
                UtilConstants.API_SEE_MORE,
                recyclerview,
                shimmerFrameLayout
            )
        }
    }


    override fun callBackManagementPdf_Archive(responseBody: MessageFromManagementPdfResponse) {

        UtilConstants.CLICKED_SEE_MORE = true
        lblSeeMore.visibility = View.GONE
        pdfList.addAll(responseBody.data)
        pdfAdapter!!.notifyDataSetChanged()
    }

    override fun callBackReadStatus(updateStatus: Boolean?) {
        if (updateStatus == true) {
            getPdfAfterReadList()
        }
    }

    private fun getPdfAfterReadList() {
        SchoolAPIServices.getMessageFromManagementPdfMessage(
            this,
            this,
            UtilConstants.API_NORMAL,
            recyclerview,
            shimmerFrameLayout
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

    override fun onCreateContextMenu(
        menu: ContextMenu, v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu.add(0, v.id, 0, "Copy")
        val textView = v as TextView
        val manager = getSystemService(AppCompatActivity.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", textView.text)
        manager.setPrimaryClip(clipData)
    }
}


