package com.vsnapnewschool.voicesnapmessenger.Activities

import android.annotation.SuppressLint
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
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetViewSubmittedAssignmentFiles
import com.vsnapnewschool.voicesnapmessenger.Interfaces.filesImagesPdfListener
import com.vsnapnewschool.voicesnapmessenger.Models.FilesImagePDF
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetNoticeBoardResponse
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetAssingmentSubmittedFiles
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.AssignmentFiletype
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.ListFilesImages
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.ListFilesPdf
import com.vsnapnewschool.voicesnapmessenger.Utils.downloadImageclass
import kotlinx.android.synthetic.main.view_file_imagepdf.*
import java.io.File
import java.util.ArrayList


@Suppress("DEPRECATION")
@SuppressLint("SetJavaScriptEnabled")

class ImagePdfFilesListScreen : BaseActivity(), View.OnClickListener,
    GetViewSubmittedAssignmentFiles {
    val FOLDER_NAME = "NewSchool/IMAGES"
    val path: String? = null
    var Menu: String? = null
    var value: String? = null
    var position: Int? = null
    var imageadapter: FilesAdapter? = null
    private var pdfFileAdapter: ParentPdfAdapter? = null
    var ViewFileList = ArrayList<GetAssingmentSubmittedFiles.SubmittedFilesData>()


    var FileType: String? = null
    var PathURL: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        var value = intent.extras!!.getBoolean("type")
        Menu = intent.extras!!.getString("Menu")

        if (value) {
            theme.applyStyle(R.style.AppThemeParent, true)

        } else {
            theme.applyStyle(R.style.AppTheme, true)

        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_file_imagepdf)
        enableCrashLytics()

        fab?.setOnClickListener(this)


        if (value) {

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


        if (Menu.equals("Pdf")) {
            if (ListFilesPdf.size > 0) {
                LayoutparentPdf.visibility = View.VISIBLE
                pdfFileAdapter =
                    ParentPdfAdapter(ListFilesPdf, this, object : filesImagesPdfListener {
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
                                        this@ImagePdfFilesListScreen,
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
        } else if (Menu.equals("Images")) {
            if (ListFilesImages.size > 0) {
                GridImageLayout.visibility = View.VISIBLE
                recycleFiles.layoutManager = GridLayoutManager(this, 3)
                imageadapter = FilesAdapter(ListFilesImages, this, object : filesImagesPdfListener {
                    override fun onFilesClick(
                        holder: FilesAdapter.MyViewHolder,
                        image: FilesImagePDF
                    ) {

                        holder.imgGrid.setOnClickListener(object : View.OnClickListener {
                            override fun onClick(v: View) {
                                Log.d("test","click")
                                UtilConstants.FileViewPopUPImagePdf(this@ImagePdfFilesListScreen, image.filepath, image.contentype!!, image.title)

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

//        if (imageClass!!.filetype.equals("image")) {
//            lnrImageView.visibility=View.VISIBLE
//            Glide.with(this)
//                .load("https://res.cloudinary.com/demo/image/upload/sample.jpg")
//                .into(imgView)
//
//        } else {
//
//            PdfViewLayout.visibility=View.VISIBLE
//            val progressDialog = ProgressDialog(this@ViewFileScreen)
//            progressDialog.setMessage("Loading")
//            progressDialog.setCancelable(false)
//            pdfwebview.setWebChromeClient(object : WebChromeClient() {
//                override fun onProgressChanged(view: WebView, progress: Int) {
//                    progressDialog.show()
//                    setProgress(progress * 100)
//                    if (progress == 100) {
//                        progressDialog.dismiss()
//                    }
//                }
//            })
//            pdfwebview.setWebViewClient(MyWebViewClient(this@ViewFileScreen))
//            pdfwebview.getSettings().setLoadsImagesAutomatically(true)
//            pdfwebview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY)
//            val webSettings: WebSettings = pdfwebview.getSettings()
//            pdfwebview.getSettings().setBuiltInZoomControls(true)
//            webSettings.javaScriptEnabled = true
//
//            val url = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"
//            pdfwebview.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=$url")
//
//
//        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fab -> {
                downloadFile()
            }

        }
    }

    fun downloadFile() {
        if (FileType.equals("image")) {
            PathURL = "https://res.cloudinary.com/demo/image/upload/sample.jpg"
        } else {
            PathURL = "https://s3.amazonaws.com/appsdeveloperblog/Micky.jpg"
        }

        var path = Environment.getExternalStorageDirectory()
            .toString() + File.separator + "NewSchool/IMAGES/" + PathURL!!.substring(
            PathURL!!.lastIndexOf(
                '/'
            ) + 1
        )
        val fileName: String = PathURL!!.substring(PathURL!!.lastIndexOf('/') + 1, PathURL!!.length)
        val savefile: File = File(path)

        if (!savefile.exists()) {
            downloadImageclass.downloadSampleFile(this, PathURL!!, FOLDER_NAME, fileName)
        } else {
            Toast.makeText(this, "File Already exists!", Toast.LENGTH_SHORT).show()
        }

    }

    override fun callBackViewSubmittedFiles(responseBody: GetAssingmentSubmittedFiles) {

    }

}

//https://voicesnap.filecloudonline.com/app/websharepro/share?path\u003d/SHARED/ramadevi/oI9EeOgvFWUD5zeH
