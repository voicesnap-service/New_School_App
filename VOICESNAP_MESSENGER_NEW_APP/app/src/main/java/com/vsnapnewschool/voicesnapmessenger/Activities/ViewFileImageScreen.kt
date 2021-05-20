package com.vsnapnewschool.voicesnapmessenger.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.FilesAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentPdfAdapter
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetParentSubmittedAssignmentCallback
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetViewSubmittedAssignmentFiles
import com.vsnapnewschool.voicesnapmessenger.Interfaces.filesImagesPdfListener
import com.vsnapnewschool.voicesnapmessenger.Models.FilesImagePDF
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.Network.StudentAPIServices
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetParentAssingmentFiles
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetAssingmentSubmittedFiles
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.ListFilesImages
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.PARENT_MENU_TYPE
import com.vsnapnewschool.voicesnapmessenger.Utils.downloadImageclass
import kotlinx.android.synthetic.main.view_file_imagepdf.*
import java.io.File
import java.util.ArrayList

class ViewFileImageScreen : BaseActivity(), View.OnClickListener, GetViewSubmittedAssignmentFiles,
    GetParentSubmittedAssignmentCallback {
    val FOLDER_NAME = "NewSchool/IMAGES"
    val path: String? = null
    var filepath: String? = null
    var fileName: String? = null
    var description: String? = null
    var value: Boolean? = null
    var fileTitle: String? = null
    var FileType: String? = null
    var PathURL: String? = null

    var imageadapter: FilesAdapter? = null
    var ViewFileList = ArrayList<GetAssingmentSubmittedFiles.SubmittedFilesData>()
    var ParentSubmittedList = ArrayList<GetParentAssingmentFiles.FilesData>()
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


        if (UtilConstants.MENU_TYPE == UtilConstants.MENU_ASSIGNMENT) {
            Log.d("testSchool","test")
            SchoolAPIServices.getSubmittedViewAssingmentFiles(this, this)

        }

        if (UtilConstants.PARENT_MENU_TYPE == UtilConstants.PARENT_MENU_ASSIGNMENT) {
            Log.d("testParent",PARENT_MENU_TYPE.toString())

            StudentAPIServices.getParentSubmittedAssingmentFiles(this, this)

        }


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
        UtilConstants.ListFilesImages.clear()
        ViewFileList.forEach {
            filepath = it.file_path
            fileName = it.original_name
            description = it.description
            if (filepath == null || fileName == null) {
                UtilConstants.normalToast(this, "No Records Found")

            } else {
                UtilConstants.ListFilesImages.add(FilesImagePDF(filepath!!, fileName!!, "image", description!!))
            }
        }


        if (UtilConstants.ListFilesImages.size > 0) {
            GridImageLayout.visibility = View.VISIBLE
            recycleFiles.layoutManager = GridLayoutManager(this, 3)
            imageadapter = FilesAdapter(UtilConstants.ListFilesImages, this, object : filesImagesPdfListener {
                override fun onFilesClick(
                    holder: FilesAdapter.MyViewHolder,
                    image: FilesImagePDF
                ) {

                    holder.imgGrid.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(v: View) {
                            UtilConstants.FileViewPopUPImagePdf(
                                this@ViewFileImageScreen, image.filepath,
                                image.contentype!!, image.title
                            )

                        }
                    })
                }

                override fun onPdfFilesClick(
                    holder: ParentPdfAdapter.MyViewHolder,
                    pdf: FilesImagePDF
                ) {

                }
            })
            recycleFiles.itemAnimator = DefaultItemAnimator()
            recycleFiles.adapter = imageadapter
        } else {
            UtilConstants.customFailureAlert(this, "No Records Found")
        }



    }

    override fun callBackViewSubmittedFiles(responseBody: GetParentAssingmentFiles) {
        ParentSubmittedList.clear()
        ParentSubmittedList = responseBody.data as ArrayList<GetParentAssingmentFiles.FilesData>
        UtilConstants.ListFilesImages.clear()
        ParentSubmittedList.forEach {
            filepath = it.file_path
            fileName = it.original_name
            if (filepath == null || fileName == null) {
                UtilConstants.normalToast(this, "No Records Found")

            } else {
                UtilConstants.ListFilesImages.add(FilesImagePDF(filepath!!, fileName!!, "image", ""))
            }

        }


        if (UtilConstants.ListFilesImages.size > 0) {
            GridImageLayout.visibility = View.VISIBLE
            recycleFiles.layoutManager = GridLayoutManager(this, 3)
            imageadapter = FilesAdapter(UtilConstants.ListFilesImages, this, object : filesImagesPdfListener {
                override fun onFilesClick(
                    holder: FilesAdapter.MyViewHolder,
                    image: FilesImagePDF
                ) {

                    holder.imgGrid.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(v: View) {
                            UtilConstants.FileViewPopUPImagePdf(
                                this@ViewFileImageScreen, image.filepath,
                                image.contentype!!, image.title
                            )

                        }
                    })
                }

                override fun onPdfFilesClick(
                    holder: ParentPdfAdapter.MyViewHolder,
                    pdf: FilesImagePDF
                ) {

                }
            })
            recycleFiles.itemAnimator = DefaultItemAnimator()
            recycleFiles.adapter = imageadapter
        } else {
            UtilConstants.customFailureAlert(this, "No Records Found")
        }


    }

}

//https://voicesnap.filecloudonline.com/app/websharepro/share?path\u003d/SHARED/ramadevi/oI9EeOgvFWUD5zeH
