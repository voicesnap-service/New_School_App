package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.AttendanceCheckListener
import com.vsnapnewschool.voicesnapmessenger.Models.AttendanceClass
import com.vsnapnewschool.voicesnapmessenger.R
import java.util.*

class MarkAttendanceAdapter(private val imagelist: ArrayList<AttendanceClass>, private val context: Context?, val AttendanceType:String,  val listenercheck: AttendanceCheckListener?) : RecyclerView.Adapter<MarkAttendanceAdapter.MyViewHolder>() {
    private var selectedPos = -1
    companion object {
        var mClickListener: AttendanceCheckListener? = null
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var lblname: TextView
        internal var attendance: ConstraintLayout
        internal var checkbox: CheckBox
        init {
            lblname = view.findViewById<View>(R.id.lblname) as TextView
            attendance = view.findViewById<View>(R.id.attendance) as ConstraintLayout
            checkbox = view.findViewById<View>(R.id.checkbox) as CheckBox
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.mark_attendance_adpt, parent, false)
        return MyViewHolder(itemView)
    }
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        mClickListener = listenercheck

        val text_info = imagelist[position]
        holder.run {
            lblname.text = text_info.day
            if (AttendanceType.equals("Show Attendance")) {
                if (text_info.attendancetype) {
                    text_info.setSelectedstatus(true)
                    holder.checkbox.setChecked(text_info.isSelectedstatus()!!)
                    holder.checkbox.setClickable(false)

                } else {
                    holder.checkbox.setChecked(false)
                    holder.checkbox.setClickable(true)
                }
            } else {
                if (text_info.selectedstatus == true) {
                    listenercheck!!.uncheck(text_info)
                    text_info.setSelectedstatus(false)
                    holder.checkbox.setBackgroundResource(R.drawable.attendance_checkbox)
                } else {
                    listenercheck!!.check(text_info)
                    holder.checkbox.setBackgroundResource(R.drawable.attendance_uncheckbox)
                    text_info.setSelectedstatus(true)
                }
            }
        }
    }
    interface BtnClickListener {
        fun onBtnClick(position: Int)
    }
    override fun getItemCount(): Int {
        return imagelist.size

    }
}
