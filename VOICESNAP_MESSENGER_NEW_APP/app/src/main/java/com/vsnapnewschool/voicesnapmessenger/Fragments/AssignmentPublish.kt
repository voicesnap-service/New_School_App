package com.vsnapnewschool.voicesnapmessenger.Fragments


import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Activities.BaseActivity

import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.SelcetedFileList
import kotlinx.android.synthetic.main.assignment_publish.*
import kotlinx.android.synthetic.main.assignment_publish_scroll.*
import java.util.*


class Assignment_PublishFragment : Fragment(), View.OnClickListener {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.assignment_publish, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as BaseActivity?)!!.lblAttachment =
            view.findViewById<TextView>(R.id.lblattachment)
        (activity as BaseActivity?)!!.rcyleSelectedFiles =
            view.findViewById<RecyclerView>(R.id.rcyleAssignmentFiles)
        (activity as BaseActivity?)!!.lblAddPDF = view.findViewById<TextView>(R.id.lblPdf)
        (activity as BaseActivity?)!!.lblAddImage = view.findViewById<TextView>(R.id.lblAddImage)

//        UploadFiles?.setOnClickListener(this)
        btnAssignment?.setOnClickListener(this)
        rytMonth?.setOnClickListener(this)
        rytTime?.setOnClickListener(this)
        lblAddImage?.setOnClickListener(this)
        lblPdf?.setOnClickListener(this)

        (activity as BaseActivity?)!!.EditTextWatcher(btnAssignment, edDescription)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.lblAddImage -> {
                (activity as BaseActivity?)!!.HideKeyboard_Fragment(context as Activity?)
                (activity as BaseActivity?)!!.ChooseFile(activity!!, "image")

//                if (UtilConstants.SelcetedFileList.size <= 1) {
//                    (activity as BaseActivity?)!!.ChooseFile(activity!!, "image")
//                } else {
//                    Toast.makeText(context, "Only two images allowed to send", Toast.LENGTH_SHORT).show()
//                }
            }
            R.id.lblPdf -> {
                (activity as BaseActivity?)!!.HideKeyboard_Fragment(context as Activity?)
                (activity as BaseActivity?)!!.choosePdfFilesOnly(activity!!)
            }
            R.id.btnAssignment -> {
                (activity as BaseActivity?)!!.HideKeyboard_Fragment(context as Activity?)
                UtilConstants.Title = edTitle.text.toString()
                UtilConstants.Description = edDescription.text.toString()
                UtilConstants.Date = lblDate.text.toString()
                UtilConstants.recipientsActivity(context as Activity?)
            }
            R.id.rytMonth -> {
                (activity as BaseActivity?)!!.HideKeyboard_Fragment(context as Activity?)
                (activity as BaseActivity?)!!.PickDate(activity!!, lblDate, true, 0)
            }
            R.id.rytTime -> {
                (activity as BaseActivity?)!!.HideKeyboard_Fragment(context as Activity?)
                (activity as BaseActivity?)!!.PickTime(context, lbltime, 0)
            }
        }
    }

}


