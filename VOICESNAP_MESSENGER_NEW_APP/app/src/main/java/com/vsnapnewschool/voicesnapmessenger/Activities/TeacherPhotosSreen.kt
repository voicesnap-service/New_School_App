package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.activity_events_publish.*
import kotlinx.android.synthetic.main.image_publish.*
import kotlinx.android.synthetic.main.image_publish.imgCamera


class TeacherPhotosSreen : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.image_publish)
        enableCrashLytics()

        initializeActionBar()
        setTitle(getString(R.string.title_Images))
        enableSearch(false)

        btnNext?.setOnClickListener(this)
        imgGallery?.setOnClickListener(this)
        imgCamera?.setOnClickListener(this)

        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)
        EditTextWatcher(btnNext, edTitle)
        lblAttachment = findViewById<TextView>(R.id.lblAttachment)
        rcyleSelectedFiles = findViewById<RecyclerView>(R.id.rcyleSelectedImages)
        lblAddFile = findViewById<TextView>(R.id.lblAddFile)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnNext -> {
                HideKeyboard_Fragment(this)
                UtilConstants.Title=edTitle.text.toString()
                UtilConstants.recipientsActivity(this)
            }
            R.id.imgGallery -> {
                HideKeyboard_Fragment(this)
                teacherPhotoscreenGallery(this)

            }
            R.id.imgCamera -> {
                HideKeyboard_Fragment(this)

                teacherPhotoscreenCamera(this)

            }
            R.id.imgTeacherChat -> {

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
