package com.vsnapnewschool.voicesnapmessenger.Activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.FilesAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentPdfAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.TotalSubmissionsAdapter
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetParentSubmittedAssignmentCallback
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetViewSubmittedAssignmentFiles
import com.vsnapnewschool.voicesnapmessenger.Interfaces.filesImagesPdfListener
import com.vsnapnewschool.voicesnapmessenger.Models.FilesImagePDF
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.Network.StudentAPIServices
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetNoticeBoardResponse
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetParentAssingmentFiles
import com.vsnapnewschool.voicesnapmessenger.R


import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetAssingmentSubmittedFiles
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.AssignmentFiletype
import com.vsnapnewschool.voicesnapmessenger.Utils.downloadImageclass
import kotlinx.android.synthetic.main.view_file_imagepdf.*
import java.io.File
import java.util.ArrayList

class ViewFileScreen : BaseActivity(), View.OnClickListener,GetViewSubmittedAssignmentFiles,GetParentSubmittedAssignmentCallback {
    val FOLDER_NAME = "NewSchool/IMAGES"
    val path: String? = null
    var filepath: String? = null

    var fileName: String? = null
    var description: String? = null
    var value: Boolean? = null


    private var pdfFileAdapter: ParentPdfAdapter? = null
    var ViewFileList = ArrayList<GetAssingmentSubmittedFiles.SubmittedFilesData>()
    var parentSubmitedList = ArrayList<GetParentAssingmentFiles.FilesData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        value = intent.extras!!.getBoolean("type")

        if (value as Boolean) {
            theme.applyStyle(R.style.AppThemeParent, true)

        } else {
            theme.applyStyle(R.style.AppTheme, true)

        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_file_imagepdf)
        enableCrashLytics()

        fab?.setOnClickListener(this)


        if (value as Boolean) {

            parentActionbar()
            setTitle(getString(R.string.title_File))
            enableSearch(false)
            getWindow().setStatusBarColor(
                ContextCompat.getColor(
                    getApplicationContext(),
                    R.color.clr_parent
                )
            )

        } else {
            initializeActionBar()
            setTitle(getString(R.string.title_File))
            enableSearch(false)
            getWindow().setStatusBarColor(
                ContextCompat.getColor(
                    getApplicationContext(),
                    R.color.app_color
                )
            )

        }

        StudentAPIServices.getParentSubmittedAssingmentFiles(this, this)

    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fab -> {

            }

        }
    }




    override fun callBackViewSubmittedFiles(responseBody: GetAssingmentSubmittedFiles) {
        ViewFileList.clear()
        ViewFileList = responseBody.data as ArrayList<GetAssingmentSubmittedFiles.SubmittedFilesData>
        UtilConstants.ListFilesPdf.clear()
        ViewFileList.forEach {
            filepath = it.file_path
            fileName = it.original_name
            description = it.description
            if (filepath == null || fileName == null) {
                UtilConstants.normalToast(this, "No Records Found")

            } else {
                UtilConstants.ListFilesPdf.add(FilesImagePDF(filepath!!, fileName!!, "image", description!!))
            }

        }

            if (UtilConstants.ListFilesPdf.size > 0) {
                LayoutparentPdf.visibility = View.VISIBLE
                pdfFileAdapter = ParentPdfAdapter(UtilConstants.ListFilesPdf, this, object : filesImagesPdfListener {
                        override fun onFilesClick(
                            holder: FilesAdapter.MyViewHolder,
                            image: FilesImagePDF
                        ) {
                            TODO("Not yet implemented")
                        }

                        override fun onPdfFilesClick(
                            holder: ParentPdfAdapter.MyViewHolder, pdf: FilesImagePDF
                        ) {

                            holder.pdfpath.setOnClickListener(object : View.OnClickListener {
                                override fun onClick(v: View) {
                                    UtilConstants.FileViewPopUPImagePdf(
                                        this@ViewFileScreen,
                                        pdf.filepath,
                                        pdf.contentype!!,
                                        pdf.title
                                    )

                                }
                            })
                        }


                    })

                val mLayoutManager = LinearLayoutManager(this)
                recycleParentpdf.layoutManager = mLayoutManager
                recycleParentpdf.itemAnimator = DefaultItemAnimator()
                recycleParentpdf.adapter = pdfFileAdapter


            }


    }

    override fun callBackViewSubmittedFiles(responseBody: GetParentAssingmentFiles) {
        parentSubmitedList.clear()
        parentSubmitedList = responseBody.data as ArrayList<GetParentAssingmentFiles.FilesData>
        UtilConstants.ListFilesPdf.clear()
        parentSubmitedList.forEach {
            filepath = it.file_path
            fileName = it.original_name
            if (filepath == null || fileName == null) {
                UtilConstants.normalToast(this, "No Records Found")

            } else {
                UtilConstants.ListFilesPdf.add(FilesImagePDF(filepath!!, fileName!!, "image", ""))
            }

        }

            if (UtilConstants.ListFilesPdf.size > 0) {
                LayoutparentPdf.visibility = View.VISIBLE
                pdfFileAdapter = ParentPdfAdapter(UtilConstants.ListFilesPdf, this, object : filesImagesPdfListener {
                        override fun onFilesClick(
                            holder: FilesAdapter.MyViewHolder,
                            image: FilesImagePDF
                        ) {
                            TODO("Not yet implemented")
                        }

                        override fun onPdfFilesClick(
                            holder: ParentPdfAdapter.MyViewHolder, pdf: FilesImagePDF
                        ) {

                            holder.pdfpath.setOnClickListener(object : View.OnClickListener {
                                override fun onClick(v: View) {
                                    UtilConstants.FileViewPopUPImagePdf(
                                        this@ViewFileScreen,
                                        pdf.filepath,
                                        pdf.contentype!!,
                                        pdf.title
                                    )

                                }
                            })
                        }


                    })

                val mLayoutManager = LinearLayoutManager(this)
                recycleParentpdf.layoutManager = mLayoutManager
                recycleParentpdf.itemAnimator = DefaultItemAnimator()
                recycleParentpdf.adapter = pdfFileAdapter


            }


    }

}

//https://voicesnap.filecloudonline.com/app/websharepro/share?path\u003d/SHARED/ramadevi/oI9EeOgvFWUD5zeH
