package com.vsnapnewschool.voicesnapmessenger.Activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetFilesAssingmentCallback
import com.vsnapnewschool.voicesnapmessenger.Models.FilesImagePDF
import com.vsnapnewschool.voicesnapmessenger.Network.StudentAPIServices
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetParentAssignmentResponse
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetParentAssingmentFiles
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetPdfFilesResponse
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetAssingmentResponse
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.ParentAssingmentFileType
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_assignment_history.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import java.util.ArrayList

class ParentAssingmentViewFileScreen : BaseActivity(), View.OnClickListener,GetFilesAssingmentCallback {
    var ViewFilePopUp: PopupWindow? = null
    var value: Boolean? = null
    var imagefilelist = ArrayList<GetParentAssignmentResponse.AssingmentDueData.ImageArray>()
    var pdfFileList = ArrayList<GetParentAssignmentResponse.AssingmentDueData.PdfArray>()
    var ParentAssingmentDueData: GetParentAssignmentResponse.AssingmentDueData? = null
    var filepath: String? = null
    var fileName: String? = null
    var description: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        value = intent.extras!!.getBoolean("type")
        if (value as Boolean) {
            theme.applyStyle(R.style.AppThemeParent, true)

        } else {
            theme.applyStyle(R.style.AppTheme, true)

        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.parent_assignment_history)
        enableCrashLytics()
        scrollAdds(this, imageSlider)

        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)
        btnTotal?.setOnClickListener(this)
        btnSubmissions?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)
        btnDelete?.setOnClickListener(this)


        if (value as Boolean) {
            parentActionbar()
            rytViewImage.setBackgroundResource(R.drawable.parent_blue_bg)
            LayoutDueTime.visibility = View.VISIBLE
            setTitle(getString(R.string.title_Assignment))
            enableSearch(false)
            ParentLayoutButtons.visibility = View.VISIBLE
            TeacherLayoutButtons.visibility = View.GONE
            rytDaymonth.visibility = View.GONE
            rytRecipients.visibility = View.GONE
            Parentbottomlayout.visibility = View.VISIBLE
            getWindow().setStatusBarColor(
                ContextCompat.getColor(
                    getApplicationContext(),
                    R.color.clr_parent
                )
            )

        } else {
            initializeActionBar()
            LayoutDueTime.setBackgroundResource(R.drawable.rectangle_orange)
            setTitle(getString(R.string.title_Assignment))
            enableSearch(false)
            TeacherLayoutButtons.visibility = View.VISIBLE
            ParentLayoutButtons.visibility = View.VISIBLE
            btnSubmit.visibility = View.GONE
            btnSubmissions.visibility = View.VISIBLE
            TeacherBottomLayout.visibility = View.VISIBLE
            getWindow().setStatusBarColor(
                ContextCompat.getColor(
                    getApplicationContext(),
                    R.color.app_color
                )
            )

        }
        rytViewImage?.setOnClickListener(this)
        ParentAssingmentDueData = intent.getSerializableExtra("ParentAssingmentData") as? GetParentAssignmentResponse.AssingmentDueData
        Log.d("test", ParentAssingmentDueData!!.created_on)
        lblDueTime.text = getString(R.string.lbl_dueon) + " " + ParentAssingmentDueData!!.end_date
        lblSubject.text = ParentAssingmentDueData!!.subject
        lblAssingmentTitle.text = ParentAssingmentDueData!!.title
        lblAssignmentDescription.text = ParentAssingmentDueData!!.description
        UtilConstants.ParentAssignmentHeaderID = ParentAssingmentDueData!!.header_id
        UtilConstants.ParentAssignmentDetailID = ParentAssingmentDueData!!.detail_id
        UtilConstants.ParentAssignmentIsArchieve = ParentAssingmentDueData!!.is_archive.toString()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rytViewImage -> {

                popUpImagePdf()

            }

            R.id.btnSubmit -> {

                UtilConstants.ParentAssingnmentSubmit(this)


            }
            R.id.btnSubmissions -> {
                UtilConstants.PopupViewFiles(this, value!!)

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


    fun popUpImagePdf() {
        val inflater: LayoutInflater =
            this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.file_view_screen, null)
        ViewFilePopUp = PopupWindow(
            view,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            true
        )
        ViewFilePopUp!!.setContentView(view)
        ViewFilePopUp!!.setTouchable(true)
        ViewFilePopUp!!.setFocusable(false)
        ViewFilePopUp!!.setOutsideTouchable(false)

        val btnViewImage = view.findViewById<TextView>(R.id.btnViewImage)
        val btnViewPDF = view.findViewById<TextView>(R.id.btnViewPDF)
        val imgClose = view.findViewById<ImageView>(R.id.imgClose)
        btnViewImage.setBackgroundResource(R.drawable.parent_blue_bg)
        btnViewPDF.setBackgroundResource(R.drawable.parent_blue_bg)

        imgClose.setOnClickListener({
            ViewFilePopUp!!.dismiss()
        })


        imagefilelist = ParentAssingmentDueData!!.image_array as ArrayList<GetParentAssignmentResponse.AssingmentDueData.ImageArray>
        pdfFileList = ParentAssingmentDueData!!.pdf_array as ArrayList<GetParentAssignmentResponse.AssingmentDueData.PdfArray>

        btnViewImage.setOnClickListener {
            if (imagefilelist.isNullOrEmpty()) {
                UtilConstants.normalToast(this, "No Records Found")
            } else {


                UtilConstants.ListFilesImages.clear()
                imagefilelist.forEach {
                    filepath = it.file_path
                    fileName = it.original_file_name
                    if (filepath == null || fileName == null) {
                        UtilConstants.normalToast(this, "No Records Found")

                    } else {
                        UtilConstants.ListFilesImages.add(FilesImagePDF(filepath!!, fileName!!,
                            "image",
                            ParentAssingmentDueData!!.title
                        )
                        )
                    }

                }
                UtilConstants.viewFileActivity(this, UtilConstants.ListFilesImages, true, "Images")

            }

        }
        btnViewPDF.setOnClickListener {
            if (pdfFileList.isNullOrEmpty()) {
                UtilConstants.normalToast(this, "No Records Found")
            } else {
                UtilConstants.ListFilesPdf.clear()
                pdfFileList.forEach {
                    var path = it.file_path
                    var filename = it.original_file_name
                    UtilConstants.ListFilesPdf.add(
                        FilesImagePDF(
                            path,
                            filename,
                            "pdf",
                            ParentAssingmentDueData!!.title
                        )
                    )
                }
                UtilConstants.viewFileActivity(this, UtilConstants.ListFilesPdf, true, "Pdf")
            }

        }
        ViewFilePopUp!!.showAtLocation(view, Gravity.CENTER, 0, 0)
    }

    override fun callFilesAssignment(responseBody: GetParentAssingmentFiles) {

    }

}

