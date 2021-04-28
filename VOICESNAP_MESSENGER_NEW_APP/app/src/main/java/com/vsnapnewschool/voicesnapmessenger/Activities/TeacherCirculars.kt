package com.vsnapnewschool.voicesnapmessenger.Activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.SelcetedFileList
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.assignment_publish_scroll.*
import kotlinx.android.synthetic.main.circular_publish.*
import kotlinx.android.synthetic.main.circular_publish.btnClear
import kotlinx.android.synthetic.main.circular_publish.btnSave
import kotlinx.android.synthetic.main.circular_publish.edDescription
import kotlinx.android.synthetic.main.circular_publish.edTitle
import kotlinx.android.synthetic.main.circular_scroll.*
import kotlinx.android.synthetic.main.circular_scroll.btnNext


class TeacherCirculars : BaseActivity(), View.OnClickListener {
    var viewPager: ViewPager? = null

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.circular_scroll)
        enableCrashLytics()
        initializeActionBar()
        setTitle(getString(R.string.title_Circular))
        enableSearch(false)
        lblAttachment = findViewById<TextView>(R.id.txtAttachments)
        rcyleSelectedFiles = findViewById<RecyclerView>(R.id.rcyleSelectedFiles)
        lblAddFile = findViewById<TextView>(R.id.lblAddFiles)
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)
        btnSave?.setOnClickListener(this)
        btnClear?.setOnClickListener(this)
        btnNext?.setOnClickListener(this)
        rytAddFile?.setOnClickListener(this)
        this.setFinishOnTouchOutside(false)

        EditTextWatcher(btnNext, edDescription)
        rbEnrol.setOnCheckedChangeListener { group, checkedId ->
            if (R.id.rbYes == checkedId) {
                HideKeyboard_Fragment(this)
                EnrollLayout.visibility = View.VISIBLE
            } else {
                HideKeyboard_Fragment(this)
                LayoutSignature.visibility = View.GONE
                lblQue.setText("")
                EnrollLayout.visibility = View.GONE
            }
        }
        RadioButtonClick(this, rbSignature, signaturePad, btnClear, btnSave)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnSave -> {
                HideKeyboard_Fragment(this)
                btnSignature()
            }
            R.id.btnClear -> {
                HideKeyboard_Fragment(this)
                signaturePad.clear()
            }
            R.id.btnNext -> {
                HideKeyboard_Fragment(this)
                UtilConstants.Title = edTitle.text.toString()
                UtilConstants.Description = edDescription.text.toString()
                UtilConstants.recipientsActivity(this)


            }
            R.id.rytAddFile -> {
                HideKeyboard_Fragment(this)
                if (SelcetedFileList.size <= 1) {
                    choosePdfFilesOnly(this@TeacherCirculars)

                } else {
                    Toast.makeText(this, "Only two images allowed to send", Toast.LENGTH_SHORT).show()
                    lblAddFiles.setText(getString(R.string.txt_change_pdf))
                }
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



