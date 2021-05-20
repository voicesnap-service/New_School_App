@file:Suppress("DEPRECATION")

package com.vsnapnewschool.voicesnapmessenger.Activities

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Typeface
import android.media.MediaPlayer
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.gcacace.signaturepad.views.SignaturePad
import com.google.firebase.analytics.FirebaseAnalytics
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.vsnapnewschool.voicesnapmessenger.AWS.S3Uploader
import com.vsnapnewschool.voicesnapmessenger.AWS.S3Utils
import com.vsnapnewschool.voicesnapmessenger.Adapters.AlbumImageAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.ImageSliderAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.RecipientsAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.AWSUploadedFiles
import com.vsnapnewschool.voicesnapmessenger.Models.SelectedFilesClass
import com.vsnapnewschool.voicesnapmessenger.Models.SliderItem
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.Network.StudentAPIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.AWSUploadedFilesList
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.Apifiletype
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_ASSIGNMENT
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_EVENTS
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_IMGAE_PDF
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_NOTICEBOARD
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_PDF_UPLOAD
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_TEXT_HOMEWORK
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_TYPE
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_VOICE_HOMEWORK
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.PARENT_MENU_ASSIGNMENT
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.PARENT_MENU_TYPE
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.ParentAssingmentFileType
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.SelcetedFileList
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.contentType
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.contentTypeImg
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.contentTypePdf
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.contentTypeVoice
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.extension
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.fileName
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.filename
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.filetype
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.filetypePdf
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.filetypeVideo
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.pathlist
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.selectedFinalStudentList
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.uploadFilePath
import com.vsnapnewschool.voicesnapmessenger.Util_Common.GifLoading
import com.vsnapnewschool.voicesnapmessenger.albumImage.AlbumSelectActivity
import com.vsnapnewschool.voicesnapmessenger.videoalbum.AlbumVideoSelectVideoActivity
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.parent_circular_history.*
import kotlinx.android.synthetic.main.popup_filechoose.view.*
import java.io.*
import java.sql.Time
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


open class BaseActivity : AppCompatActivity() {

    var slideadapter: ImageSliderAdapter? = null
    var sliderItemList: MutableList<SliderItem> = ArrayList()
    var cal = Calendar.getInstance()
     var fromdate: String?=null
     var Todate: String?=null
    var searchView: SearchView? = null
    var cart: ImageButton? = null
    var title: TextView? = null
    var lblAttachment: TextView? = null
    var lblAddFile: TextView? = null
    var lblAddImage: TextView? = null
    var lblAssingmentAddFile: TextView? = null
    var lblAddPDF: TextView? = null
    var rcyleSelectedFiles: RecyclerView? = null
    var lblrecipient: TextView? = null
    var datefrom: Date? = null
    val myCalendar = Calendar.getInstance()

    val SelectedReceipientsList = ArrayList<String>()
    var recipientsAdapter: RecipientsAdapter? = null
    var rcyleRecipients: RecyclerView? = null
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    var res: String? = null
    var FilePopup: PopupWindow? = null
    val REQUEST_Camera = 1
    val REQUEST_GAllery = 2
    val REQUEST_Video = 100
    val SELECT_PDF = 8778
    lateinit var selectedadapter: AlbumImageAdapter
    var imageFilePath: String? = null
    var PDFTempFileWrite: File? = null
    var photoTempFileWrite: File? = null
    var photoURI: Uri? = null
    var outputDir: File? = null
    var imgChat: ImageView? = null
    var imgHome: ImageView? = null


    //s3 access varaibles

    var fileNameDateTime: String? = null
    var s3uploaderObj: S3Uploader? = null
    var urlFromS3: String? = null
    var pathIndex = 0
    var awsContentType: String? = ""

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }

    open fun initializeActionBar() {
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(R.layout.custom_action_bar)
        supportActionBar?.elevation = 0f
        val view = supportActionBar!!.customView
        cart = view.findViewById<ImageButton>(R.id.action_bar_cart)
        searchView = view.findViewById<SearchView>(R.id.action_bar_search)
        title = view.findViewById<TextView>(R.id.nav_title)

        cart?.setVisibility(View.VISIBLE)
        cart?.setOnClickListener {
            finish()
        }
    }

    open fun parentActionbar() {
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(R.layout.parent_custom_actionbar)
        supportActionBar?.elevation = 0f
        val view = supportActionBar!!.customView
        cart = view.findViewById<ImageButton>(R.id.action_bar_cart)
        searchView = view.findViewById<SearchView>(R.id.action_bar_search)
        title = view.findViewById<TextView>(R.id.nav_title)
        cart?.setVisibility(View.VISIBLE)
        cart?.setOnClickListener {
            finish()
        }
    }
    open fun parentActionbarforVoice(mediaPlayer: MediaPlayer) {
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(R.layout.parent_custom_actionbar)
        supportActionBar?.elevation = 0f
        val view = supportActionBar!!.customView
        cart = view.findViewById<ImageButton>(R.id.action_bar_cart)
        searchView = view.findViewById<SearchView>(R.id.action_bar_search)
        title = view.findViewById<TextView>(R.id.nav_title)
        cart?.setVisibility(View.VISIBLE)
        cart?.setOnClickListener {
            if(mediaPlayer.isPlaying) {
                mediaPlayer.stop()
            }
            finish()

        }
    }

    open fun teacherActionbarforVoice(mediaPlayer: MediaPlayer) {
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(R.layout.parent_custom_actionbar)
        supportActionBar?.elevation = 0f
        val view = supportActionBar!!.customView
        cart = view.findViewById<ImageButton>(R.id.action_bar_cart)
        searchView = view.findViewById<SearchView>(R.id.action_bar_search)
        title = view.findViewById<TextView>(R.id.nav_title)
        cart?.setVisibility(View.VISIBLE)
        cart?.setOnClickListener {
            if(mediaPlayer.isPlaying) {
                mediaPlayer.stop()
            }
            finish()

        }
    }

    fun enableSearch(type: Boolean) {
        if (type) {
            searchView?.visibility = View.VISIBLE
        } else {
            searchView?.visibility = View.GONE

        }
    }

    fun setTitle(s: String?) {
        if (s != null) {
            title?.text = s

        }
    }

    open fun setEditTextMaxLength(editText: EditText, length: Int) {
        val FilterArray = arrayOfNulls<InputFilter>(1)
        FilterArray[0] = LengthFilter(length)
        editText.filters = FilterArray
    }
//    fun EditTextLength(){
//        fun EditText.setMaxLength(maxLength: Int) {
//            filters = arrayOf(InputFilter.LengthFilter(maxLength))
//        }
//    }
    fun AssignmentsetTextStyle(
        lblDays: TextView,
        lblTotalDays: TextView,
        lblAttendend: TextView,
        lbldaysAttended: TextView,
        lblOnLeave: TextView,
        lblOnleavedays: TextView
    ) {
        lblDays.setTypeface(Typeface.DEFAULT_BOLD)
        lblTotalDays.setTypeface(Typeface.DEFAULT_BOLD)
        lblAttendend.setTypeface(Typeface.DEFAULT_BOLD)
        lbldaysAttended.setTypeface(Typeface.DEFAULT_BOLD)
        lblOnLeave.setTypeface(Typeface.DEFAULT_BOLD)
        lblOnleavedays.setTypeface(Typeface.DEFAULT_BOLD)
    }

    fun setChatClick(imgChat: ImageView, imgHome: ImageView, imgProfile: ImageView) {

        imgChat.setImageResource(R.drawable.prnt_chat)
        imgHome.setImageResource(R.drawable.prnt_group_white)
        imgProfile.setImageResource(R.drawable.prnt_profile__white)

    }

    open fun enableCrashLytics() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1")
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "crash")
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image")
        mFirebaseAnalytics?.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
    }


    fun HideKeyboard_Fragment(context: Activity?) {
        if (context != null) {
            val inputMethodManager =
                context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = context.currentFocus
            if (view == null) {
                view = View(context)
            }
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun videoAlbumselection(activity: Activity) {

        val intent1 = Intent(activity, AlbumVideoSelectVideoActivity::class.java)
        intent1.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(intent1, REQUEST_Video)
    }

    fun teacherPhotoscreenGallery(activity: Activity) {
        val photointent = Intent(activity, AlbumSelectActivity::class.java)
        photointent.putExtra("Gallery", "Images")
        startActivityForResult(photointent, REQUEST_GAllery)
    }


    fun teacherPhotoscreenCamera(activity: Activity) {

        //  SelcetedFileList.clear()
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            photoTempFileWrite = createImageFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (photoTempFileWrite != null) {
            photoURI = FileProvider.getUriForFile(
                activity,
                "com.vsnapnewschool.voicesnapmessenger.provider",
                photoTempFileWrite!!
            )
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            startActivityForResult(intent, REQUEST_Camera)
        }
    }

    fun EditTextWatcher(button: Button, txtDescription: EditText) {
        txtDescription.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length > 0) {
                    //lblCount.setText("" + (460 - s.length))
                    button.setEnabled(true)
                } else {
                    button.setEnabled(false)
                }

            }

        })
    }


    fun choosePdfFilesOnly(activity: Activity) {

        //  SelcetedFileList.clear()
//        val intent = Intent()
//        intent.type = "application/pdf"
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//        intent.setAction(Intent.ACTION_OPEN_DOCUMENT)
//        startActivityForResult(intent, SELECT_PDF)

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(intent, SELECT_PDF)


    }

    fun ChooseFile(activity: Activity, filetype: String) {

        if (MENU_TYPE == MENU_NOTICEBOARD) {
            SelcetedFileList.clear()
        }
        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialog: View = inflater.inflate(R.layout.popup_filechoose, null)
        FilePopup = PopupWindow(
            dialog,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )
        FilePopup?.showAtLocation(dialog, Gravity.BOTTOM, 0, 0)
        FilePopup?.setContentView(dialog)
        FilePopup?.setOutsideTouchable(true)
        FilePopup?.setFocusable(true)
        val container = FilePopup?.getContentView()?.getParent() as View
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val p = container.layoutParams as WindowManager.LayoutParams
        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        p.dimAmount = 0.7f
        wm.updateViewLayout(container, p)

        if (filetype.equals("pdf")) {
            dialog.LayoutGallery.visibility = View.VISIBLE
            dialog.LayoutCamera.visibility = View.VISIBLE
            dialog.LayoutDocuments.visibility = View.VISIBLE
        } else {
            dialog.LayoutGallery.visibility = View.VISIBLE
            dialog.LayoutCamera.visibility = View.VISIBLE
            dialog.LayoutDocuments.visibility = View.GONE

        }
        dialog.popClose.setOnClickListener {
            FilePopup?.dismiss()
        }
        dialog.LayoutGallery.setOnClickListener {
            ParentAssingmentFileType="IMAGE"
            val intent1 = Intent(this, AlbumSelectActivity::class.java)
            intent1.putExtra("Gallery", "Images")
            startActivityForResult(intent1, REQUEST_GAllery)
            FilePopup!!.dismiss()


        }
        dialog.LayoutCamera.setOnClickListener {
            ParentAssingmentFileType="IMAGE"

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                photoTempFileWrite = createImageFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (photoTempFileWrite != null) {
                photoURI = FileProvider.getUriForFile(
                    this,
                    "com.vsnapnewschool.voicesnapmessenger.provider",
                    photoTempFileWrite!!
                )
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(intent, REQUEST_Camera)
                FilePopup?.dismiss()
            }

        }
        dialog.LayoutDocuments.setOnClickListener({
            //     SelcetedFileList.clear()
            ParentAssingmentFileType="PDF"

            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            startActivityForResult(intent, SELECT_PDF)


//            val mimeTypes = arrayOf(
//                "application/msword",
//                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
//                "application/vnd.ms-excel",
//                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
//                "text/plain",
//                "text/csv",
//                "application/csv",
//                "application/pdf",
//                "image/jpeg",
//                "image/jpg",
//                "image/png",
//                "image/gif",
//                "application/rar",
//                "application/zip"
//            )
//
//            val intent = Intent(Intent.ACTION_GET_CONTENT)
//                .setType("application/pdf")
//                .putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
//                .addCategory(Intent.CATEGORY_OPENABLE)
//            startActivityForResult(intent, SELECT_PDF)

            FilePopup!!.dismiss()

        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        SelcetedFileList.clear()
        pathlist.clear()
        if (resultCode != Activity.RESULT_CANCELED) {
            lblAttachment?.visibility = View.VISIBLE
            rcyleSelectedFiles?.visibility = View.VISIBLE

            if (requestCode == REQUEST_Camera) {
                SelcetedFileList.add(imageFilePath.toString())
                Apifiletype = filetype

            } else if (requestCode == REQUEST_GAllery) {

                if (data != null) {
                    SelcetedFileList = data.getStringArrayListExtra("images")!!
                    Apifiletype = filetype
                    pathlist.clear()

                   SelcetedFileList.forEach {
                       var path=it
                       pathlist.add(SelectedFilesClass(path, filetype))
                    }

                }
            } else if (requestCode == SELECT_PDF && resultCode == RESULT_OK && data != null) {
                Apifiletype = filetypePdf
                if (data!!.clipData != null) {
                    val mClipData = data.clipData
                    val mArrayUri = ArrayList<Uri>()


                    for (i in 0 until mClipData!!.itemCount) {
                        val item = mClipData!!.getItemAt(i)
                        val uri = item.uri
                        mArrayUri.add(uri)

                        Apifiletype = filetypePdf
                        outputDir = externalCacheDir!!
                        ReadAndWriteFile(uri, ".pdf")


                    }

                }else if(data!!.data != null){
                    Apifiletype = filetypePdf
                    val fileuri = data!!.data
                    outputDir = externalCacheDir!!

                    ReadAndWriteFile(fileuri, ".pdf")
                }
            } else if (requestCode == REQUEST_Video) {
                if (data != null) {
                    SelcetedFileList = data.getStringArrayListExtra("images")!!
                    Apifiletype = filetypeVideo

                    UtilConstants.VideoFilePath= SelcetedFileList.get(0)


                    pathlist.clear()

                    SelcetedFileList.forEach {
                        var path=it
                        pathlist.add(SelectedFilesClass(path, filetypeVideo))
                    }

                }
            }

            //for text change
            if (filetype.equals(Apifiletype)) {
                lblAddFile?.setText(getString(R.string.txt_change_img))
                lblAddImage?.setText(getString(R.string.txt_change_img))
                lblAssingmentAddFile?.setText(getString(R.string.txt_change_file))

            }
            if (filetypePdf.equals(Apifiletype)) {
                lblAddFile?.setText(getString(R.string.txt_change_pdf))
                lblAddPDF?.setText(getString(R.string.txt_change_pdf))
                lblAssingmentAddFile?.setText(getString(R.string.txt_change_file))

            }
            if (filetypeVideo.equals(Apifiletype)) {
                lblAddFile?.setText(getString(R.string.txt_change_video))

            }


            selectedadapter = AlbumImageAdapter(pathlist, Apifiletype!!, this,
                object : AlbumImageAdapter.BtnClickListener {
                    override fun onBtnClick(position: Int) {
                        pathlist.removeAt(position)
                        selectedadapter.notifyItemRemoved(position)

                        if (pathlist.size == 0) {
//                            lblAddFile?.setText(getString(R.string.btn_addimg_pdf))

                            if (filetype.equals(Apifiletype)) {
                                lblAddFile?.setText(getString(R.string.btn_addimg_pdf))

                            } else if (filetypePdf.equals(Apifiletype)) {
                                lblAddFile?.setText(getString(R.string.btn_add_pdf))
                                lblAddPDF?.setText(getString(R.string.btn_add_pdf))

                            } else if (filetypeVideo.equals(Apifiletype)) {
                                lblAddFile?.setText(getString(R.string.txt_change_video))

                            }
                            lblAttachment?.visibility = View.GONE
                            rcyleSelectedFiles?.visibility = View.GONE
                        }
                    }
                })
            val mLayoutManager = LinearLayoutManager(this)
            rcyleSelectedFiles?.layoutManager = mLayoutManager
            rcyleSelectedFiles?.itemAnimator = DefaultItemAnimator()
            rcyleSelectedFiles?.adapter = selectedadapter

        }
    }

    @Throws(IOException::class)
    fun createImageFile(): File? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "IMG_" + timeStamp + "_"
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )
        imageFilePath = image.absolutePath
        return image
    }

    fun ReadAndWriteFile(uri: Uri?, type: String) {
        try {
            uri?.let {
                contentResolver?.openInputStream(it).use { `in` ->
                    if (`in` == null) return
                    try {
                        Apifiletype = filetypePdf
                        PDFTempFileWrite = File.createTempFile("NEWSCHOOLAPP", type, outputDir)
                        var pdfPath: String = PDFTempFileWrite?.path!!
                        extension = pdfPath.substring(pdfPath.toString().lastIndexOf("."))
                        Log.d("extensionpdf", extension!!)
                        Log.d("PDFTempFileWrite", PDFTempFileWrite.toString()!!)
                        SelcetedFileList.add(pdfPath)
                        pathlist.clear()

                        SelcetedFileList.forEach {
                            var path=it
                            Log.d("test", path)
                            pathlist.add(SelectedFilesClass(path, filetypePdf))
                        }

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    contentResolver?.openOutputStream(Uri.fromFile(PDFTempFileWrite))
                        .use { out ->
                            if (out == null) return
                            val buf = ByteArray(1024)
                            var len = 0
                            while (true) {
                                try {
                                    if (`in`.read(buf).also({ len = it }) <= 0) break
                                } catch (e: IOException) {
                                    e.printStackTrace()
                                }
                                try {
                                    out.write(buf, 0, len)
                                } catch (e: IOException) {
                                    e.printStackTrace()
                                }
                            }
                        }
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun awsFileUpload(activity: Activity?, pathind: Int?) {

        Log.d("SelcetedFileList", SelcetedFileList.size.toString())
        val s3uploaderObj: S3Uploader
        s3uploaderObj = S3Uploader(activity)
        pathIndex = pathind!!

        for (index in pathIndex until SelcetedFileList.size) {
            uploadFilePath = SelcetedFileList.get(index)
            extension = uploadFilePath.substring(uploadFilePath.lastIndexOf("."))
            if (extension.equals(".pdf")) {
                contentType = contentTypePdf
            } else if (extension.equals(".mp3")) {
                contentType = contentTypeVoice
            } else {
                contentType = contentTypeImg
            }
            break

        }
        if (AWSUploadedFilesList.size < SelcetedFileList.size) {
            Log.d("test", uploadFilePath)
            if (uploadFilePath != null) {
                GifLoading.loading(activity, true)
                fileNameDateTime = SimpleDateFormat("yyyyMMddHHmmss").format(
                    Calendar.getInstance().getTime()
                )
                fileNameDateTime = "File_" + fileNameDateTime
                s3uploaderObj.initUpload(uploadFilePath, contentType, fileNameDateTime)
                s3uploaderObj!!.setOns3UploadDone(object : S3Uploader.S3UploadInterface {
                    override fun onUploadSuccess(response: String?) {
                        if (response!!.equals("Success")) {
                            urlFromS3 = S3Utils.generates3ShareUrl(
                                activity!!.applicationContext,
                                uploadFilePath,
                                fileNameDateTime
                            )
                            if (!TextUtils.isEmpty(urlFromS3)) {

                                fileName = File(uploadFilePath)
                                filename = fileName!!.name
                                AWSUploadedFilesList.add(
                                    AWSUploadedFiles(
                                        urlFromS3!!,
                                        filename,
                                        contentType
                                    )
                                )
                                awsFileUpload(activity, pathIndex + 1)

                                if (SelcetedFileList.size == AWSUploadedFilesList.size) {

                                    if (MENU_TYPE == MENU_EVENTS) {
                                        if (UtilConstants.RecipientsType == UtilConstants.EntireSchool) {
                                            SchoolAPIServices.sendEventsToEntireSchool(activity)
                                        } else if (UtilConstants.RecipientsType == UtilConstants.StandardSection) {
                                            SchoolAPIServices.sendEventsToStdGrpStaffStudSection(
                                                activity
                                            )
                                        } else if (UtilConstants.RecipientsType == UtilConstants.Standard) {
                                            SchoolAPIServices.sendEventsToStdGrpStaffStudSection(
                                                activity
                                            )
                                        } else if (UtilConstants.RecipientsType == UtilConstants.Students) {
                                            SchoolAPIServices.sendEventsToStdGrpStaffStudSection(
                                                activity
                                            )
                                        } else if (UtilConstants.RecipientsType == UtilConstants.Staff) {
                                            SchoolAPIServices.sendEventsToStdGrpStaffStudSection(
                                                activity
                                            )
                                        } else if (UtilConstants.RecipientsType == UtilConstants.Group) {
                                            SchoolAPIServices.sendEventsToStdGrpStaffStudSection(
                                                activity
                                            )
                                        }

                                    } else if ((MENU_TYPE == MENU_IMGAE_PDF) || (MENU_TYPE == MENU_PDF_UPLOAD)) {
                                        if (UtilConstants.RecipientsType == UtilConstants.EntireSchool) {
                                            SchoolAPIServices.sendFilesToEntireSchool(activity)
                                        } else if (UtilConstants.RecipientsType == UtilConstants.StandardSection) {
                                            SchoolAPIServices.sendFilesTostudentGrpStaffSec(activity)
                                        } else if (UtilConstants.RecipientsType == UtilConstants.Standard) {
                                            SchoolAPIServices.sendFilesTostudentGrpStaffSec(activity)
                                        } else if (UtilConstants.RecipientsType == UtilConstants.Students) {
                                            SchoolAPIServices.sendFilesTostudentGrpStaffSec(activity)
                                        } else if (UtilConstants.RecipientsType == UtilConstants.Staff) {
                                            SchoolAPIServices.sendFilesTostudentGrpStaffSec(activity)
                                        } else if (UtilConstants.RecipientsType == UtilConstants.Group) {
                                            SchoolAPIServices.sendFilesTostudentGrpStaffSec(activity)
                                        }

                                    } else if (MENU_TYPE == MENU_NOTICEBOARD) {
                                        if (UtilConstants.RecipientsType == UtilConstants.EntireSchool) {
                                            SchoolAPIServices.sendNoticeboardToEntireSchool(activity)
                                        } else if (UtilConstants.RecipientsType == UtilConstants.StandardSection) {
                                            SchoolAPIServices.sendNoticeboardTostudentGrpStaffSec(
                                                activity
                                            )
                                        } else if (UtilConstants.RecipientsType == UtilConstants.Standard) {
                                            SchoolAPIServices.sendNoticeboardTostudentGrpStaffSec(
                                                activity
                                            )
                                        } else if (UtilConstants.RecipientsType == UtilConstants.Students) {
                                            SchoolAPIServices.sendNoticeboardTostudentGrpStaffSec(
                                                activity
                                            )
                                        } else if (UtilConstants.RecipientsType == UtilConstants.Staff) {
                                            SchoolAPIServices.sendNoticeboardTostudentGrpStaffSec(
                                                activity
                                            )
                                        } else if (UtilConstants.RecipientsType == UtilConstants.Group) {
                                            SchoolAPIServices.sendNoticeboardTostudentGrpStaffSec(
                                                activity
                                            )
                                        }

                                    } else if (MENU_TYPE == MENU_VOICE_HOMEWORK) {
                                        SchoolAPIServices.sendHomeWork(activity)

                                    } else if (MENU_TYPE == MENU_ASSIGNMENT) {
                                        SchoolAPIServices.sendAssignment(activity)

                                    }


                                    if (PARENT_MENU_TYPE == PARENT_MENU_ASSIGNMENT) {
                                        Log.d("test","testmenu")
                                        StudentAPIServices.parentSubmitAssingment(activity)

                                    }
                                }
                            }
                        }
                    }

                    override fun onUploadError(response: String?) {
                        GifLoading.loading(activity, false)
                        Log.d("error", "Error Uploading")
                    }
                })

            }
        }
    }

    fun btnSignature() {
        val bitmap = signaturePad.getSignatureBitmap()
        if (addJpgSignatureToGallery(bitmap)) {
            Toast.makeText(
                applicationContext,
                "Signature saved into the Gallery",
                Toast.LENGTH_SHORT
            ).show();
        } else {
            Toast.makeText(applicationContext, "Unable to store the signature", Toast.LENGTH_SHORT)
                .show();
        }
        if (addSvgSignatureToGallery(signaturePad.getSignatureSvg())) {
            Toast.makeText(
                applicationContext,
                "SVG Signature saved into the Gallery",
                Toast.LENGTH_SHORT
            ).show();
        } else {
            Toast.makeText(
                applicationContext,
                "Unable to store the SVG signature",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun getAlbumStorageDir(albumName: String?): File {
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName
        )
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created")
        }
        return file
    }

    @Throws(IOException::class)
    fun saveBitmapToJPG(bitmap: Bitmap, photo: File?): String {
        val newBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(newBitmap)
        canvas.drawColor(Color.WHITE)
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        val stream: OutputStream = FileOutputStream(photo)
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
        stream.close()

        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            Environment.getExternalStorageDirectory(), Environment.DIRECTORY_PICTURES /*folder*/
        )

        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs()
        }
        try {
            val file = File(
                wallpaperDirectory,
                Calendar.getInstance().getTimeInMillis().toString() + ".jpg"
            )
            file.createNewFile()
            val fo = FileOutputStream(file)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this, arrayOf(file.path), arrayOf("image/jpeg"), null)
            fo.close()
            Log.d("TAG", "File Saved::--->" + file.absolutePath)
            return file.absolutePath
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        return ""

    }

    fun addJpgSignatureToGallery(signature: Bitmap): Boolean {
        var result = false
        try {
            val photo = File(
                getAlbumStorageDir("SignaturePad"),
                String.format("Signature_%d.jpg", System.currentTimeMillis())
            )
            saveBitmapToJPG(signature, photo)
            scanMediaFile(photo)
            result = true
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return result
    }

    private fun scanMediaFile(photo: File) {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val contentUri: Uri = Uri.fromFile(photo)
        mediaScanIntent.data = contentUri
        applicationContext.sendBroadcast(mediaScanIntent)
    }

    fun addSvgSignatureToGallery(signatureSvg: String?): Boolean {
        var result = false
        try {
            val svgFile = File(
                getAlbumStorageDir("SignaturePad"),
                String.format("Signature_%d.jpg", System.currentTimeMillis())
            )
            val stream: OutputStream = FileOutputStream(svgFile)
            val writer = OutputStreamWriter(stream)
            writer.write(signatureSvg)
            writer.close()
            stream.flush()
            stream.close()
            scanMediaFile(svgFile)
            result = true
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return result
    }

    fun scrollAdds(activity: Activity, sliderView: SliderView) {
        slideadapter = ImageSliderAdapter(activity)
        sliderView.setSliderAdapter(slideadapter!!)
        sliderView.setIndicatorAnimation(IndicatorAnimationType.THIN_WORM) //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        sliderView!!.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
        // sliderView!!.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_RIGHT
        // sliderView!!.indicatorSelectedColor = Color.GREEN
        sliderView.indicatorUnselectedColor = Color.GRAY
        sliderView.scrollTimeInSec = 4 //set scroll delay in seconds
        sliderView.startAutoCycle()
        renewItems()

    }

    private fun renewItems() {
        sliderItemList.clear()
        var sliderItem = SliderItem()
        sliderItem.setDescription("Slider Item 00")
        sliderItem.setImageUrl("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260")
        sliderItemList.add(sliderItem)
        sliderItem = SliderItem()
        sliderItem.setDescription("Slider Item 01")
        sliderItem.setImageUrl("https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260")
        sliderItemList.add(sliderItem)
        sliderItem = SliderItem()
        sliderItem.setDescription("Slider Item 03")
        sliderItem.setImageUrl("https://homepages.cae.wisc.edu/~ece533/images/arctichare.png")
        sliderItemList.add(sliderItem)
        sliderItem = SliderItem()
        sliderItem.setDescription("Slider Item 04")
        sliderItem.setImageUrl("https://homepages.cae.wisc.edu/~ece533/images/boat.png")
        sliderItemList.add(sliderItem)
        slideadapter!!.renewItems(sliderItemList)
    }

    fun finalPreviewReceipientsAdpter(activity: Activity?) {
        if (UtilConstants.RecipientsType == UtilConstants.Standard) {
            lblrecipient!!.text = getString(R.string.recipient_standard)
            UtilConstants.textBold(this, lblrecipient)
            UtilConstants.selectedFinalStandardList.forEach {
                val selecteditem: String = it.standard_name
                SelectedReceipientsList.add(selecteditem)

            }
        } else if (UtilConstants.RecipientsType == UtilConstants.Group) {
            lblrecipient!!.text = getString(R.string.recipient_group)
            UtilConstants.textBold(this, lblrecipient)
            UtilConstants.selectedFinalGroupsList.forEach {
                var selecteditem: String = it.group_name
                SelectedReceipientsList.add(selecteditem)
            }

        } else if (UtilConstants.RecipientsType == UtilConstants.Staff) {
            lblrecipient!!.text = getString(R.string.recipient_staff)
            UtilConstants.textBold(this, lblrecipient)
            UtilConstants.selectedFinalStaffsList.forEach {
                var selecteditem: String = it.staff_name
                SelectedReceipientsList.add(selecteditem)

            }
        } else if (UtilConstants.RecipientsType == UtilConstants.StandardSection) {

            if (MENU_TYPE == MENU_TEXT_HOMEWORK) {
                lblrecipient!!.text = getString(R.string.recipient_stdnsec)
                UtilConstants.textBold(this, lblrecipient)
                UtilConstants.selectedFinalSectionList.forEach {
                    var selecteditem: String = it.sectionName!!
                    SelectedReceipientsList.add(selecteditem)

                }
            }
            lblrecipient!!.text = getString(R.string.recipient_stdnsec)
            UtilConstants.textBold(this, lblrecipient)
            UtilConstants.selectedFinalSectionList.forEach {
                var selecteditem: String = it.sectionName!!
                SelectedReceipientsList.add(selecteditem)

            }

        } else if (UtilConstants.RecipientsType == UtilConstants.Students) {
            lblrecipient!!.text = getString(R.string.recipient_student)
            UtilConstants.textBold(this, lblrecipient)
            selectedFinalStudentList.forEach {
                var selectedName: String = it.standardSectionName!!
                var selecteditem: String? = null
                it.studentData.forEach({
                    selecteditem = selectedName + "-" + it.student_name
                    SelectedReceipientsList.add(selecteditem!!)
                })
            }
        }
        recipientsAdapter = RecipientsAdapter(SelectedReceipientsList, this)
        rcyleRecipients!!.layoutManager = GridLayoutManager(this, 3) as RecyclerView.LayoutManager?
        rcyleRecipients!!.adapter = recipientsAdapter
        recipientsAdapter!!.notifyDataSetChanged()
    }


    fun PickDate(context: Activity, lblDate: TextView, type: Boolean, endcall: Int?) {
        val myCalendar = Calendar.getInstance()

        if (type) {
            val datePickerDialog: DatePickerDialog = DatePickerDialog(
                context, R.style.TeacherDatePicker,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    myCalendar[Calendar.YEAR] = year
                    myCalendar[Calendar.MONTH] = monthOfYear
                    myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                    val myFormat = "dd MMM yyyy" //In which you need put here
                    val sdf = SimpleDateFormat(myFormat, Locale.US)
                    lblDate.text = sdf.format(myCalendar.time)
                    val myFormatapi = "dd/MM/yyyy"//In which you need put here
                    val sdfapi = SimpleDateFormat(myFormatapi, Locale.US)
                    val dateapi=sdfapi.format(myCalendar.time)
                    if (endcall == 1) {
                        UtilConstants.EndDate = dateapi
                        Log.d("testDate", UtilConstants.EndDate!!)

                    } else {
                        UtilConstants.Date = dateapi
                        Log.d("ScheduleDate", UtilConstants.Date!!)

                    }
                },
                myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            )
            datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
            datePickerDialog.show()

        } else {
            val datePickerDialog: DatePickerDialog = DatePickerDialog(
                context,
                R.style.ParentCalendarTheme,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    myCalendar[Calendar.YEAR] = year
                    myCalendar[Calendar.MONTH] = monthOfYear
                    myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                    val myFormat = "dd MMM yyyy" //In which you need put here
                    val sdf = SimpleDateFormat(myFormat, Locale.US)
                    lblDate.text = sdf.format(myCalendar.time)
                    fromdate = lblDate.text.toString()
                    Log.d("fromdate",fromdate!!)


                },
                myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            )
            datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
            datePickerDialog.show()

        }
    }

    fun PickTime(context: Context?, timeset: TextView?, endcall: Int?) {
        val c = Calendar.getInstance()
        val hourOfDay = c[Calendar.HOUR_OF_DAY]
        val minute = c[Calendar.MINUTE]
        val dpd = TimePickerDialog(
            context,
            TimePickerDialog.OnTimeSetListener { timePicker: TimePicker?, hourOfDay1: Int, minute1: Int ->
                val time = Time(hourOfDay1, minute1, 0)
                val simpleDateFormat = SimpleDateFormat("hh:mm aa", Locale.getDefault())
                val s = simpleDateFormat.format(time)
                Log.d("testTime", s)
                timeset!!.setText(s)
                UtilConstants.EventTime = s

                if (endcall == 1) {
                    UtilConstants.EndHour = hourOfDay1.toString()
                    UtilConstants.EndMinute = minute1.toString()

                } else {
                    UtilConstants.Hour = hourOfDay1.toString()
                    UtilConstants.Minute = minute1.toString()

                }
            },
            hourOfDay,
            minute,
            false
        )
        dpd.show()
    }

    fun fromDate(lblDate: TextView) {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        lblDate.text = sdf.format(cal.getTime())
        fromdate = lblDate.text.toString()
    }

    fun ToDate(lblDate: TextView) {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        lblDate.text = sdf.format(cal.getTime())
        Todate = lblDate.text.toString()
    }


    fun EndDate(context: Activity, lblenddate: TextView) {
Log.d("tesrt","test")
        val date =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                Log.d("tesrt","test")

                ToDate(lblenddate)
            }

            val datePickerDialog = DatePickerDialog(context,
                R.style.DialogThemeparent,
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )

                val myFormat = "dd/MM/yyyy"
                val sdf =
                    SimpleDateFormat(myFormat, Locale.FRANCE)
                var datefrom: Date? = null
                try {
                    datefrom = sdf.parse(fromdate!!)
                    //                     dateto = sdf.parse(TOdate);
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                datePickerDialog.datePicker.minDate = datefrom!!.time
                //                datePickerDialog.getDatePicker().setMaxDate(dateto.getTime());
                datePickerDialog.show()


//        val myCalendar = Calendar.getInstance()
//Log.d("test","test")
//        val datePickerDialog: DatePickerDialog = DatePickerDialog(context, R.style.ParentCalendarTheme, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
//                myCalendar[Calendar.YEAR] = year
//                myCalendar[Calendar.MONTH] = monthOfYear
//                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
//
//            Log.d("test123","test")
//
//                val myFormat = "dd/MM/yyyy"
//                val sdf = SimpleDateFormat(myFormat, Locale.US)
//                try {
//                    Log.d("datefrom","test")
//
//                    datefrom = sdf.parse(fromdate)
//                } catch (e: ParseException) {
//                    e.printStackTrace()
//                }
//
//                lblenddate.text = sdf.format(myCalendar.time)
//                Todate = lblenddate.text.toString()
//
//
//            },
//            myCalendar[Calendar.YEAR],
//            myCalendar[Calendar.MONTH],
//            myCalendar[Calendar.DAY_OF_MONTH]
//        )
//        datePickerDialog.datePicker.minDate = datefrom!!.time
//        Log.d("datefrom", datefrom?.toString()!!)
//
//        datePickerDialog.show()
//




    }

    fun RadioButtonClick(
        context: Activity,
        rbEnrol: RadioGroup,
        signaturePad: SignaturePad,
        btnClear: Button,
        btnSave: Button
    ) {

        rbEnrol.setOnCheckedChangeListener { group, checkedId ->
            if (R.id.rbsignatureYes == checkedId) {
                HideKeyboard_Fragment(this)

                LayoutSignature.visibility = View.VISIBLE
                signaturePad.visibility = View.VISIBLE
                btnClear.visibility = View.VISIBLE
                btnSave.visibility = View.VISIBLE
            } else {
                HideKeyboard_Fragment(this)

                LayoutSignature.visibility = View.GONE
                signaturePad.visibility = View.GONE
                btnClear.visibility = View.GONE
                btnSave.visibility = View.GONE
            }
            signaturePad.setOnSignedListener(object : SignaturePad.OnSignedListener {
                override fun onStartSigning() {
                }

                override fun onSigned() {
                    btnSave.setEnabled(true)
                    btnClear.setEnabled(true)
                }

                override fun onClear() {
                    btnSave.setEnabled(false)
                    btnClear.setEnabled(false)
                }
            })
        }
    }


}




