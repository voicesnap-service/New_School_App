package com.vsnapnewschool.voicesnapmessenger.Fragments

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vsnapnewschool.voicesnapmessenger.Activities.BaseActivity
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.text_publish.btnNext
import kotlinx.android.synthetic.main.text_scroll.*
class TextPublish: Fragment(),View.OnClickListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {return inflater?.inflate(
            R.layout.text_publish, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnNext?.setOnClickListener(this)
        edDescription.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length > 0) {
                    lblRemaining.setText("" + (460 - s.length))
                    btnNext.setEnabled(true)
                } else {
                    btnNext.setEnabled(false)
                }
            }
        })
    }
    override fun onClick(v: View?) {
        when(v!!.id) {

            R.id.btnNext -> {
                UtilConstants.Title=edTitle.text.toString()
                UtilConstants.Description=edDescription.text.toString()
                (activity as BaseActivity?)!!. HideKeyboard_Fragment(context as Activity?)
                UtilConstants.recipientsActivity(activity!!)

            }
        }
    }
}