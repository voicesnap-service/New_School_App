package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.examScheduleListener
import com.vsnapnewschool.voicesnapmessenger.Models.ImageClass
import com.vsnapnewschool.voicesnapmessenger.R
import java.util.*



class ExamScheduleAdapter(private val listname: ArrayList<ImageClass>, private val context: Context?, val examScheduleListener:examScheduleListener) : RecyclerView.Adapter<ExamScheduleAdapter.MyViewHolder>() {
    companion object {
        var examlistener: examScheduleListener? = null
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var lblMonth: TextView
        internal var lblDate: TextView
        internal var lblSubject: TextView
        internal var lblTimings: TextView
        internal var ExamSchedule: ConstraintLayout
        init {
            lblMonth = view.findViewById<View>(R.id.lblMonth) as TextView
            lblDate = view.findViewById<View>(R.id.lblDate) as TextView
            lblSubject = view.findViewById<View>(R.id.lblSubject) as TextView
            lblTimings = view.findViewById<View>(R.id.lblTimings) as TextView
            ExamSchedule = view.findViewById<View>(R.id.ExamSchedule) as ConstraintLayout
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_examschedule, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val text_info = listname[position]
        holder.lblMonth.text = text_info.Month
        holder.lblDate.text = text_info.Day
        holder.lblSubject.text = text_info.Content
        holder.lblTimings.text = text_info.description
        examlistener = examScheduleListener
        examlistener?.onexamScheduleclick(holder,text_info)
    }
    override fun getItemCount(): Int {
        return listname.size
    }
}
