package com.vsnapnewschool.voicesnapmessenger.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.activity_parent_assingnment_submit_screen.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.parent_submitassingment_scroll.*
import kotlinx.android.synthetic.main.scroll_noticeboard_publish.*

class ParentAssingnmentSubmitScreen : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.parent_submitassingment_scroll)
        enableCrashLytics()
        parentActionbar()
        setTitle(getString(R.string.title_Assignment))
        enableSearch(false)
        rytAddFiles?.setOnClickListener(this)
        btnAssignment?.setOnClickListener(this)
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)
        EditTextWatcher(btnAssignment, edDescription)
        lblAttachment = findViewById<TextView>(R.id.txtAttachments)
        rcyleSelectedFiles = findViewById<RecyclerView>(R.id.rcyleSelectedImages)
        lblAssingmentAddFile = findViewById<TextView>(R.id.lblAssingmentAddFile)


    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.rytAddFiles -> {
                HideKeyboard_Fragment(this)

                UtilConstants.MENU_TYPE= UtilConstants.MENU_NOTICEBOARD

                //if (UtilConstants.SelcetedFileList.size <= 1) {
                    ChooseFile(this, "pdf")
//                } else {
//                    Toast.makeText(
//                        this,
//                        "Only two images allowed to send",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    lblAddFile!!.setText(getString(R.string.txt_change_image_pdf))
//                }

            }
            R.id.btnAssignment -> {
                HideKeyboard_Fragment(this)

                UtilConstants.Title = edTitle.text.toString()
                UtilConstants.Description = edDescription.text.toString()

                if(edTitle.text.toString().isEmpty()||edDescription.text.toString().isEmpty()|| UtilConstants.SelcetedFileList.size==0){
                    UtilConstants.normalToast(this,"Enter Details")
                }else{
                    awsFileUpload(this, 0)

                }

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