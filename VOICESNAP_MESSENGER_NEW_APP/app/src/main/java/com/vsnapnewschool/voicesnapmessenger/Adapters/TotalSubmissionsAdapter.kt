package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.totalsubmissionsListener
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetAssingmentMemberSubmissions
import java.util.ArrayList

class TotalSubmissionsAdapter (private val imagelist: ArrayList<GetAssingmentMemberSubmissions.MemberSubmmisionsData>, val context: Context?, val totalsubmissionListener: totalsubmissionsListener) : RecyclerView.Adapter<TotalSubmissionsAdapter.MyViewHolder>() {
    companion object {
        var submissionsListener: totalsubmissionsListener? = null
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var lblStudentName: TextView
        internal var lblSection: TextView
        internal var lblStatus: TextView
        internal var btnView: Button
        init {
            lblStudentName = view.findViewById<View>(R.id.lblStudentName) as TextView
            lblSection = view.findViewById<View>(R.id.lblsection) as TextView
            lblStatus = view.findViewById<View>(R.id.lblstatus) as TextView
            btnView = view.findViewById<View>(R.id.btnViewFile) as Button

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_total_submissions, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val text_info = imagelist[position]



        holder.run {

            submissionsListener = totalsubmissionListener
            submissionsListener?.onAssingmentSubmissionsClick(holder,text_info)
            holder.lblStudentName.text = text_info.member_name
            holder.lblSection.text = text_info.section_name
            if(text_info.issubmitted==1){
                holder.lblStatus.text=context!!.getString(R.string.lbl_submitted)

            }else{
                holder.lblStatus.text=context!!.getString(R.string.lbl_not_submitted)
                btnView.visibility=View.GONE

            }
        }
    }
    override fun getItemCount(): Int {
        return imagelist.size

    }
}
