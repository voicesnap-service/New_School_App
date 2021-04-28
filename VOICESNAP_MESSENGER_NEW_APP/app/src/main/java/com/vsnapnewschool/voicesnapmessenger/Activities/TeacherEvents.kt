package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.vsnapnewschool.voicesnapmessenger.Adapters.AlbumImageAdapter
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.activity_events_publish.*

import kotlinx.android.synthetic.main.events_scroll.btnNext
import java.util.*


class TeacherEvents : BaseActivity(), View.OnClickListener {
    var viewPager: ViewPager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.events_scroll)
        enableCrashLytics()

        initializeActionBar()
        setTitle(getString(R.string.title_Events))
        enableSearch(false)

        rytUploadImages?.setOnClickListener(this)
        rytEventMonth?.setOnClickListener(this)
        rytEventTime?.setOnClickListener(this)
        btnNext?.setOnClickListener(this)
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)

        EditTextWatcher(btnNext,edEventDescription)

        lblAttachment = findViewById<TextView>(R.id.txtAttachments)
        rcyleSelectedFiles = findViewById<RecyclerView>(R.id.rcyEventSelectedFile)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.rytUploadImages -> {
                HideKeyboard_Fragment(this)
                teacherPhotoscreenGallery(this)

            }

            R.id.rytEventMonth -> {
                HideKeyboard_Fragment(this)
              PickDate(this,lblEventDate,true,0)

            }
            R.id.rytEventTime -> {
                HideKeyboard_Fragment(this)
                PickTime(this, lblEventTime,0)
            }
            R.id.btnNext -> {
                HideKeyboard_Fragment(this)
                UtilConstants.Title=edEventTitle.text.toString()
                UtilConstants.Description=edEventDescription.text.toString()

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

