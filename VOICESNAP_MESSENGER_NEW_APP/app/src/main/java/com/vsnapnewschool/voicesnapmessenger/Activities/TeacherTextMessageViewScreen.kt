package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.text_publish.*
import kotlinx.android.synthetic.main.text_scroll.*


class TeacherTextMessageViewScreen : BaseActivity(), View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.text_publish)
        enableCrashLytics()
        initializeActionBar()
        setTitle(getString(R.string.title_Text))
        enableSearch(false)
        btnNext?.setOnClickListener(this)
        setTitle(getString(R.string.forward))
        btnNext.text = getString(R.string.forward)
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)
        HideKeyboard_Fragment(this)
        btnNext.isEnabled=true

        edTitle.isEnabled=false
        edDescription.isEnabled=false

    }
    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.btnNext -> {
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
