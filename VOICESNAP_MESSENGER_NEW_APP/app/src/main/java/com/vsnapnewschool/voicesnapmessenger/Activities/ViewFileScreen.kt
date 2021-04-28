package com.vsnapnewschool.voicesnapmessenger.Activities

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.vsnapnewschool.voicesnapmessenger.Models.EventsImageClass
import com.vsnapnewschool.voicesnapmessenger.Models.Leave_Class
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.Utils.MyWebViewClient
import com.vsnapnewschool.voicesnapmessenger.Utils.downloadImageclass
import kotlinx.android.synthetic.main.view_file_imagepdf.*
import java.io.File


@Suppress("DEPRECATION")
@SuppressLint("SetJavaScriptEnabled")

class ViewFileScreen : BaseActivity(), View.OnClickListener {
    val FOLDER_NAME = "NewSchool/IMAGES"
    val path: String? = null
    var imageClass: EventsImageClass? = null
    var assignmentclass: Leave_Class? = null
    var FileType: String? = null
    var PathURL: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
       var  value = intent.extras!!.getBoolean("type")

        if(value){
            theme.applyStyle(R.style.AppThemeParent,true)

        }else{
            theme.applyStyle(R.style.AppTheme,true)

        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_file_imagepdf)
        enableCrashLytics()

        fab?.setOnClickListener(this)
        imageClass = intent.getSerializableExtra("contentfile") as? EventsImageClass
        var path = imageClass?.image

        if(value){

            parentActionbar()
            setTitle(getString(R.string.title_File))
            enableSearch(false)
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.clr_parent))

        }else{

            initializeActionBar()
            setTitle(getString(R.string.title_File))
            enableSearch(false)
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.app_color))

        }
        if (imageClass!!.filetype.equals("image")) {
            lnrImageView.visibility=View.VISIBLE
            Glide.with(this)
                .load("https://res.cloudinary.com/demo/image/upload/sample.jpg")
                .into(imgView)

        } else {

            PdfViewLayout.visibility=View.VISIBLE
            val progressDialog = ProgressDialog(this@ViewFileScreen)
            progressDialog.setMessage("Loading")
            progressDialog.setCancelable(false)
            pdfwebview.setWebChromeClient(object : WebChromeClient() {
                override fun onProgressChanged(view: WebView, progress: Int) {
                    progressDialog.show()
                    setProgress(progress * 100)
                    if (progress == 100) {
                        progressDialog.dismiss()
                    }
                }
            })
            pdfwebview.setWebViewClient(MyWebViewClient(this@ViewFileScreen))
            pdfwebview.getSettings().setLoadsImagesAutomatically(true)
            pdfwebview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY)
            val webSettings: WebSettings = pdfwebview.getSettings()
            pdfwebview.getSettings().setBuiltInZoomControls(true)
            webSettings.javaScriptEnabled = true

            val url = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"
            pdfwebview.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=$url")


        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fab -> {
                downloadFile()
            }

        }
    }
    fun downloadFile(){
        if (FileType.equals("image")) {
            PathURL = "https://res.cloudinary.com/demo/image/upload/sample.jpg"
        } else {
            PathURL = "https://s3.amazonaws.com/appsdeveloperblog/Micky.jpg"
        }

        var path = Environment.getExternalStorageDirectory().toString() + File.separator + "NewSchool/IMAGES/" + PathURL!!.substring(PathURL!!.lastIndexOf('/') + 1)
        val fileName: String = PathURL!!.substring(PathURL!!.lastIndexOf('/') + 1, PathURL!!.length)
        val savefile: File = File(path)

        if (!savefile.exists()) {
            downloadImageclass.downloadSampleFile(this, PathURL!!, FOLDER_NAME, fileName)
        } else {
            Toast.makeText(this, "File Already exists!", Toast.LENGTH_SHORT).show()
        }

    }

}
