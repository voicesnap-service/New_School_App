package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vsnapnewschool.voicesnapmessenger.Interfaces.ApproveLeaveListener
import com.vsnapnewschool.voicesnapmessenger.Models.EventsImageClass
import com.vsnapnewschool.voicesnapmessenger.R
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*


class ApproveLeaveAdapter(private val listname: ArrayList<EventsImageClass>,
                          private val context: Context?,
                          val approvelistener:ApproveLeaveListener) : RecyclerView.Adapter<ApproveLeaveAdapter.MyViewHolder>() {
    companion object {
        var leavelistenrer: ApproveLeaveListener? = null
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var lblStudentName: TextView
        internal var lblRollNo: TextView
        internal var lblDayCount: TextView
        internal var lblFromDate: TextView
        internal var lblToDate: TextView
        internal var lblReason: TextView
        internal var btnApprove: Button
        internal var btnReject: Button
        internal var imgMember: CircleImageView
        internal var FromDateLayout: ConstraintLayout
        internal var ToDateLayout: ConstraintLayout
        init {
            lblStudentName = view.findViewById<View>(R.id.lblStudentName) as TextView
            lblRollNo = view.findViewById<View>(R.id.lblRollNo) as TextView
            lblDayCount = view.findViewById<View>(R.id.lblDayCount) as TextView
            lblReason = view.findViewById<View>(R.id.lblReason) as TextView
            lblFromDate = view.findViewById<View>(R.id.lblFromDate) as TextView
            lblToDate = view.findViewById<View>(R.id.lblToDate) as TextView
            imgMember = view.findViewById<View>(R.id.imgMember) as CircleImageView
            FromDateLayout = view.findViewById<View>(R.id.FromDateLayout) as ConstraintLayout
            ToDateLayout = view.findViewById<View>(R.id.ToDateLayout) as ConstraintLayout
            btnApprove = view.findViewById<View>(R.id.btnApprove) as Button
            btnReject = view.findViewById<View>(R.id.btnReject) as Button

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.approve_leave_adapter, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val text_info = listname[position]
        holder.lblStudentName.text = text_info.Day
        holder.lblRollNo.text = text_info.Month
        holder.lblDayCount.text = text_info.Content
        holder.lblReason.text = text_info.description
        leavelistenrer = approvelistener
        leavelistenrer?.onapproveleveClick(holder,text_info)
        Glide.with(context!!)
            .load(text_info.image)
            .into(holder.imgMember)
    }
    override fun getItemCount(): Int {
        return listname.size
    }
}



