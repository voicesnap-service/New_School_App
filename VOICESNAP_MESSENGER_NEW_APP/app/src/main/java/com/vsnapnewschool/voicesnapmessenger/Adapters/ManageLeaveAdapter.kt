package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.manageLeaveListener
import com.vsnapnewschool.voicesnapmessenger.Models.Leave_Class
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.ApproveLeaveData
import java.util.*


class ManageLeaveAdapter(private var imagelist: ArrayList<ApproveLeaveData>,
                         private val context: Context?,

                         private val approvelistener: manageLeaveListener
) : RecyclerView.Adapter<ManageLeaveAdapter.MyViewHolder>() {

    companion object {
        var leavelistenrer: manageLeaveListener? = null
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
     leavelistenrer = approvelistener
     leavelistenrer?.onapproveleveClick(holder, text_info)

        holder.run {

            if(text_info.status.equals("0")){
                holder.status.text = context!!.getString(R.string.lbl_pending)

            }else if (text_info.status.equals("1")){
                holder.status.text = context!!.getString(R.string.lbl_approved)


            }else if(text_info.equals("2")){
                holder.status.text = context!!.getString(R.string.lbl_rejected)


            }
            lblstartdate.text = text_info.leave_from
            lblenddate.text = text_info.leave_to
            leavereason.text = text_info.reason
            leavetype.text = text_info.leave_type
        }
    }
    override fun getItemCount(): Int {
        return imagelist.size

    }
}
