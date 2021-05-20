package com.vsnapnewschool.voicesnapmessenger.Activities

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import com.vsnapnewschool.voicesnapmessenger.Models.FilesImagePDF
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetPdfFilesResponse
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.MessageFromManagementPdfResponse
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.parent_circular_history.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.ArrayList

class MessageMangementPdfViewScreen: BaseActivity(), View.OnClickListener {

    var pdfFilelist = ArrayList<MessageFromManagementPdfResponse.PdfData.FileArray>()
    var contentTitle:String?= null

    var ManagementPdfData: MessageFromManagementPdfResponse.PdfData? = null
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.parent_circular_view)
        enableCrashLytics()
        scrollAdds(this, imageSlider)
        initializeActionBar()
        enableSearch(false)

        RadioButtonClick(this, rbEnrol, signaturePad, btnClear, btnSave)
        btnSave?.setOnClickListener(this)
        btnClear?.setOnClickListener(this)
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)

        rytViewDownload?.setOnClickListener(this)
        rytViewDownload.setBackgroundResource(R.drawable.rectangle_orange)

        ManagementPdfData = intent.getSerializableExtra("PdfData") as? MessageFromManagementPdfResponse.PdfData?
        Log.d("createdon", ManagementPdfData!!.createdon)
        lblCreatedOn.text = ManagementPdfData!!.createdon
        lblContent.text = ManagementPdfData!!.description
        lblSendBy.text = ManagementPdfData!!.created_by
        contentTitle=ManagementPdfData!!.description
        pdfFilelist= ManagementPdfData!!.file_array as ArrayList<MessageFromManagementPdfResponse.PdfData.FileArray>

        setTitle(getString(R.string.title_Pdf))
        UtilConstants.PDFHeaderID = ManagementPdfData!!.header_id
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnSave -> {
                btnSignature()
            }
            R.id.btnClear -> {
                signaturePad.clear()
            }
            R.id.rytViewDownload -> {
                pdfFilelist= ManagementPdfData!!.file_array as ArrayList<MessageFromManagementPdfResponse.PdfData.FileArray>
                UtilConstants.ListFilesPdf.clear()
                pdfFilelist.forEach {
                    var path=it.file_path
                    var filename=it.original_file_name
                    UtilConstants.ListFilesPdf.add(FilesImagePDF(path, filename,"pdf",""))
                }
                UtilConstants.viewFileActivity(this, UtilConstants.ListFilesPdf,true,"Pdf")
            }
            R.id.imgTeacherChat -> {
                //  setChatClick(imgChat?, imgHome?, imgProfile?)
            }
            R.id.imgTeacherHomeMenu -> {
                UtilConstants.imgTeacherHomeIntent(this)
            }
            R.id.imgTeacherSettings -> {
                UtilConstants.imgProfileIntent(this)
            }
        }
    }
}


