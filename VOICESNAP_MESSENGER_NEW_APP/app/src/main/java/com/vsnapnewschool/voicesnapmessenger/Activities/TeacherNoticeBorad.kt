package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.homework_publish.*
import kotlinx.android.synthetic.main.notice_public.*
import kotlinx.android.synthetic.main.notice_public.edDescription
import kotlinx.android.synthetic.main.notice_public.edTitle
import kotlinx.android.synthetic.main.scroll_noticeboard_publish.*


class TeacherNoticeBorad : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scroll_noticeboard_publish)
        enableCrashLytics()

        initializeActionBar()
        setTitle(getString(R.string.title_notice_board))
        enableSearch(false)
        rytAddFiles?.setOnClickListener(this)
        btnNext?.setOnClickListener(this)
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)
        EditTextWatcher(btnNext, edDescription)
        lblAttachment = findViewById<TextView>(R.id.txtAttachments)
        rcyleSelectedFiles = findViewById<RecyclerView>(R.id.rcyleSelectedImages)
        lblAddFile = findViewById<TextView>(R.id.lblAddFile)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.rytAddFiles -> {
                HideKeyboard_Fragment(this)

                UtilConstants.MENU_TYPE=UtilConstants.MENU_NOTICEBOARD

                if (UtilConstants.SelcetedFileList.size <= 1) {
                    ChooseFile(this, "image")
                } else {
                    Toast.makeText(
                        this@TeacherNoticeBorad,
                        "Only two images allowed to send",
                        Toast.LENGTH_SHORT
                    ).show()
                    lblAddFile!!.setText(getString(R.string.txt_change_image_pdf))
                }

            }
            R.id.btnNext -> {
                HideKeyboard_Fragment(this)
                UtilConstants.recipientsActivity(this)

                UtilConstants.Title = edTitle.text.toString()
                UtilConstants.Description = edDescription.text.toString()

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

