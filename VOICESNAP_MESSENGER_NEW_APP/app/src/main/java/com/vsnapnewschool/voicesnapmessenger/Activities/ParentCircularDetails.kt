package com.vsnapnewschool.voicesnapmessenger.Activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.parent_circular_history.*

class ParentCircularDetails : BaseActivity(), View.OnClickListener {

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.parent_circular_view)
        enableCrashLytics()
        scrollAdds(this, imageSlider)
        parentActionbar()
        enableSearch(false)
        val content: String = intent.getStringExtra("content")!!
        setTitle(content)
        RadioButtonClick(this, rbEnrol, signaturePad, btnClear, btnSave)
        btnSave?.setOnClickListener(this)
        btnClear?.setOnClickListener(this)
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnSave -> {
                btnSignature()
            }
            R.id.btnClear -> {
                signaturePad.clear()
            }
            R.id.rytViewDownload -> {
                //  UtilConstants.viewFileIntent(this, Leave_Class)
            }
            R.id.imgchat -> {
                //  setChatClick(imgChat?, imgHome?, imgProfile?)
            }
            R.id.imgHomeMenu -> {
                UtilConstants.imgHomeIntent(this)
            }
            R.id.imgSettings -> {
                UtilConstants.imgProfileIntent(this)
            }
        }
    }
}


