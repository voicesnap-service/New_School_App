package com.vsnapnewschool.voicesnapmessenger.Activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import com.vsnapnewschool.voicesnapmessenger.Models.FilesImagePDF
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetImageFilesResponse
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetPdfFilesResponse
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetAssingmentResponse
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.ListFilesPdf
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_assignment_history.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.parent_circular_history.*
import java.util.ArrayList

class ParentCircularDetails : BaseActivity(), View.OnClickListener {

    var pdfFilelist = ArrayList<GetPdfFilesResponse.PdfData.FileArray>()
    var contentTitle:String?= null

    var PdfData: GetPdfFilesResponse.PdfData? = null
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.parent_circular_view)
        enableCrashLytics()
        scrollAdds(this, imageSlider)
        parentActionbar()
        enableSearch(false)

        RadioButtonClick(this, rbEnrol, signaturePad, btnClear, btnSave)
        btnSave?.setOnClickListener(this)
        btnClear?.setOnClickListener(this)
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)
        rytViewDownload?.setOnClickListener(this)


        PdfData = intent.getSerializableExtra("parentPdfData") as? GetPdfFilesResponse.PdfData?
        Log.d("createdon", PdfData!!.createdon)
        lblCreatedOn.text = PdfData!!.createdon
        lblContent.text = PdfData!!.description
        lblSendBy.text = PdfData!!.created_by
        contentTitle=PdfData!!.description
        pdfFilelist= PdfData!!.file_array as ArrayList<GetPdfFilesResponse.PdfData.FileArray>
        Log.d("pdfFilelist123",pdfFilelist.size.toString())

        setTitle(getString(R.string.title_Circular))
        UtilConstants.PDFHeaderID = PdfData!!.header_id
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
                pdfFilelist= PdfData!!.file_array as ArrayList<GetPdfFilesResponse.PdfData.FileArray>
                UtilConstants.ListFilesPdf.clear()
                pdfFilelist.forEach {
                    var path=it.file_path
                    var filename=it.original_file_name
                    UtilConstants.ListFilesPdf.add(FilesImagePDF(path, filename,"pdf",""))
                }
                UtilConstants.viewFileActivity(this, UtilConstants.ListFilesPdf,true,"Pdf")
            }
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
}


