package com.vsnapnewschool.voicesnapmessenger.Models

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager.BadTokenException
import com.vsnapnewschool.voicesnapmessenger.R

class CustomLoading {
    fun createProgressDialog(context: Context?): ProgressDialog? {
        val dialog = ProgressDialog(context)
        try {
            dialog.show()
        } catch (e: BadTokenException) {
        }
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_loading)
        // dialog.setMessage(Message);
        return dialog
    }
}