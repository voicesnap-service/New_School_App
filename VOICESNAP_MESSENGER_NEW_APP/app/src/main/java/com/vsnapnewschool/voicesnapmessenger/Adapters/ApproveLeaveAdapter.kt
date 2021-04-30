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
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.ApproveLeaveData
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*


class ApproveLeaveAdapter(
    private val listname: ArrayList<ApproveLeaveData>,
    private val context: Context?,
    val approvelistener: ApproveLeaveListener
) : RecyclerView.Adapter<ApproveLeaveAdapter.MyViewHolder>() {
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
        internal var lblClass: TextView
        internal var lblSection: TextView
        internal var lblstatus: TextView
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
            lblClass = view.findViewById<View>(R.id.lblClass) as TextView
            lblSection = view.findViewById<View>(R.id.lblSection) as TextView
            lblstatus = view.findViewById<View>(R.id.lblstatus) as TextView
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
        holder.lblStudentName.text = text_info.student_name
//        holder.lblRollNo.text = text_info.Month
        holder.lblDayCount.text = text_info.no_of_days + " " + text_info.leave_type
        holder.lblFromDate.text = text_info.leave_from
        holder.lblToDate.text = text_info.leave_to
        holder.lblClass.text = text_info.`class`
        holder.lblSection.text = text_info.section
        holder.lblReason.text = text_info.reason
        holder.lblstatus.text = text_info.status

        if(text_info.status.equals("1")){
            holder.lblstatus.text = context!!.getString(R.string.lbl_approved)
            holder.lblstatus.setBackgroundResource(R.drawable.bg_approve_leave)


        }else if(text_info.status.equals("2")){
            holder.lblstatus.text = context!!.getString(R.string.lbl_rejected)
            holder.lblstatus.setBackgroundResource(R.drawable.rectangle_half_red)

        }

        if (text_info.status.equals("0")) {
            holder.btnApprove.visibility = View.VISIBLE
            holder.btnReject.visibility = View.VISIBLE

        } else {
            holder.btnApprove.visibility = View.GONE
            holder.btnReject.visibility = View.GONE

        }


        leavelistenrer = approvelistener
        leavelistenrer?.onapproveleveClick(holder, text_info)
        Glide.with(context!!)
            .load(R.drawable.man)
            .into(holder.imgMember)
    }

    override fun getItemCount(): Int {
        return listname.size
    }
}



