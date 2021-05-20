package com.vsnapnewschool.voicesnapmessenger.Activities

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.CircularHistoryAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentImageListAdapter
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetPdfListCallBack
import com.vsnapnewschool.voicesnapmessenger.CallBacks.ReadStatusCallBacak
import com.vsnapnewschool.voicesnapmessenger.Interfaces.circularHistorylistener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.parentImageListListener
import com.vsnapnewschool.voicesnapmessenger.Models.FilesImagePDF
import com.vsnapnewschool.voicesnapmessenger.Models.Text_Class
import com.vsnapnewschool.voicesnapmessenger.Network.StudentAPIServices
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetImageFilesResponse
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetPdfFilesResponse
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.ListFilesPdf
import kotlinx.android.synthetic.main.activity_image_grid.*
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.*


class ParentCircular:BaseActivity(), View.OnClickListener, GetPdfListCallBack, ReadStatusCallBacak {
    var circularHistoryadapter: CircularHistoryAdapter? = null
    var pdfList = ArrayList<GetPdfFilesResponse.PdfData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        enableCrashLytics()
        scrollAdds(this, imageSlider)
        parentActionbar()
        enableSearch(true)
        setTitle(getString(R.string.title_Circular))
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)
        lblSeeMore?.setOnClickListener(this)
        recyle_parent_bottom_layout.visibility = View.VISIBLE


        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)

        UtilConstants.CLICKED_SEE_MORE = false
        StudentAPIServices.getPdfFiles(
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
            } R.id.lblSeeMore -> {
            StudentAPIServices.getPdfFiles(
                this,
                this,
                UtilConstants.API_SEE_MORE,
                recyclerview,
                shimmerFrameLayout
            )
        }
        }
    }

    override fun callBackPdfFiles(responseBody: GetPdfFilesResponse) {

        pdfList.clear()
        pdfList = responseBody.data as ArrayList<GetPdfFilesResponse.PdfData>
        Log.d("pdflist",pdfList.size.toString())

        circularHistoryadapter = CircularHistoryAdapter(pdfList, this,"0", object : circularHistorylistener {
            override fun oncircularclickListener (holder: CircularHistoryAdapter.MyViewHolder, item: GetPdfFilesResponse.PdfData) {
                holder.CircularLayout.setOnClickListener({
                    UtilConstants.parentCircularView(this@ParentCircular,item)

                    if (item.is_archive == 0) {
                        StudentAPIServices.updateReadStatus(
                            this@ParentCircular,
                            item.header_id,
                            item.detail_id,
                            UtilConstants.API_NORMAL, this@ParentCircular
                        )
                    } else {
                        StudentAPIServices.updateReadStatus(
                            this@ParentCircular,
                            item.header_id,
                            item.detail_id,
                            UtilConstants.API_SEE_MORE,
                            this@ParentCircular
                        )
                    }
                })
            }
        })

        val mLayoutManager2 = LinearLayoutManager(this)
        recyclerview.layoutManager = mLayoutManager2
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = circularHistoryadapter

        if (UtilConstants.CLICKED_SEE_MORE == true) {
            StudentAPIServices.getPdfFiles(
                this,
                this,
                UtilConstants.API_SEE_MORE,
                recyclerview,
                shimmerFrameLayout
            )
        }
    }


    override fun callBackPdfFilesArchive(responseBody: GetPdfFilesResponse) {

        UtilConstants.CLICKED_SEE_MORE = true
        lblSeeMore.visibility = View.GONE
        pdfList.addAll(responseBody.data)
        circularHistoryadapter!!.notifyDataSetChanged()
    }

    override fun callBackReadStatus(updateStatus: Boolean?) {
        if (updateStatus == true) {
            getPdfAfterReadList()
        }
    }

    private fun getPdfAfterReadList() {
        StudentAPIServices.getPdfFiles(
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
        menuInfo: ContextMenu.ContextMenuInfo?) {
        menu.add(0, v.id, 0, "Copy")
        val textView = v as TextView
        val manager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", textView.text)
        manager.setPrimaryClip(clipData)
    }
}


