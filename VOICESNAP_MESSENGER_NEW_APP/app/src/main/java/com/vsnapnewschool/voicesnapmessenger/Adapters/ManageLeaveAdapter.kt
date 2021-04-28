package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Models.Leave_Class
import com.vsnapnewschool.voicesnapmessenger.R
import java.util.*


class ManageLeaveAdapter(private var imagelist: ArrayList<Leave_Class>, private val context: Context?) : RecyclerView.Adapter<ManageLeaveAdapter.MyViewHolder>() {
    fun update(modelList:ArrayList<Leave_Class>){
        imagelist = modelList
        notifyDataSetChanged()
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var status: TextView
        internal var day: TextView
        internal var lblstartdate: TextView
        internal var lblenddate: TextView
        internal var leavereason: TextView
        internal var leavetype: TextView
        internal var manageleave: ConstraintLayout
        init {
            status = view.findViewById<View>(R.id.lblstatus) as TextView
            day = view.findViewById<View>(R.id.lblDay) as TextView
            lblstartdate = view.findViewById<View>(R.id.lblstartdate) as TextView
            lblenddate = view.findViewById<View>(R.id.lblenddate) as TextView
            leavereason = view.findViewById<View>(R.id.leavereason) as TextView
            leavetype = view.findViewById<View>(R.id.leavetype) as TextView
            manageleave = view.findViewById<View>(R.id.manageleave) as ConstraintLayout
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.manage_leave_design, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val text_info = imagelist[position]
        holder.status.text = text_info.status
        holder.day.text = text_info.day
        holder.lblstartdate.text = text_info.lblstartdate
        holder.lblenddate.text = text_info.lblenddate
        holder.leavereason.text = text_info.leavereason
        holder.leavetype.text = text_info.leavetype
        holder.run {
            status.text = text_info.status
            day.text = text_info.day
            lblstartdate.text = text_info.lblstartdate
            lblenddate.text = text_info.lblenddate
            leavereason.text = text_info.leavereason
            leavetype.text = text_info.leavetype
        }
    }
    override fun getItemCount(): Int {
        return imagelist.size

    }
}
