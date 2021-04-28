package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import kotlinx.android.synthetic.main.dial_numbers_item.view.*

class ForgotDialNumbers(
    private val DialNumberList: Array<String>,
    private val context: Context,
    private  val popupWindow: PopupWindow) :
    RecyclerView.Adapter<ForgotDialNumbers.ViewHolder>() {

    override fun getItemCount() = DialNumberList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.dial_numbers_item,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val number = DialNumberList[position]
        holder.lblNumber.setText(number)
        holder.DialParent.setOnClickListener {
            Util_shared_preferences.putForgotPassword(context,true)
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:$number")
            if (ActivityCompat.checkSelfPermission(context,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return@setOnClickListener
            }
            (context as Activity).startActivity(intent)
            popupWindow.dismiss()
            context.startActivity(intent)
            context.finishAffinity()
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val DialParent: ConstraintLayout = itemView.DialParent
        val lblNumber: TextView = itemView.lblNumber
    }
}