package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.circular_publish.*
import kotlinx.android.synthetic.main.video.*


class TeacherVideoUploadScreen : BaseActivity(),View.OnClickListener{
    var viewPager: ViewPager? = null
    var imagelist = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video)
        enableCrashLytics()

        initializeActionBar()
        setTitle(getString(R.string.title_Video))
        enableSearch(false)
        rytAddFiles?.setOnClickListener(this)
        btnNext?.setOnClickListener(this)
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)
        EditTextWatcher(btnNext,edVideoDescription)
        lblAttachment = findViewById<TextView>(R.id.lblattach)
        rcyleSelectedFiles = findViewById<RecyclerView>(R.id.rcyleVideoFiles)
        lblAddFile = findViewById<TextView>(R.id.lblAddVideo)

    }
    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.rytAddFiles -> {
                HideKeyboard_Fragment(this)
                videoAlbumselection(this)
            }
            R.id.btnNext -> {
                HideKeyboard_Fragment(this)
                UtilConstants.Title = lblContent.text.toString()
                UtilConstants.Description = edVideoDescription.text.toString()
                UtilConstants.recipientsActivity(this)
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
