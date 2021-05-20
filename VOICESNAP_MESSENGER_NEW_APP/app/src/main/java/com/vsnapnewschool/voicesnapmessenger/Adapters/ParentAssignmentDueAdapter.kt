package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.assignmentDueListener
import com.vsnapnewschool.voicesnapmessenger.Models.EventsImageClass
import com.vsnapnewschool.voicesnapmessenger.Models.Leave_Class
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetParentAssignmentResponse
import com.vsnapnewschool.voicesnapmessenger.R


class ParentAssignmentDueAdapter(
    private var imagelist: ArrayList<GetParentAssignmentResponse.AssingmentDueData>,
    private val context: Context?,
    private val type: Boolean, val assignmentListener: assignmentDueListener
) : RecyclerView.Adapter<ParentAssignmentDueAdapter.MyViewHolder>() {
    companion object {
        var assignmentduelistener: assignmentDueListener? = null
    }

    fun update(modelList: ArrayList<GetParentAssignmentResponse.AssingmentDueData>) {
        imagelist = modelList
        notifyDataSetChanged()
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var content: TextView
        internal var subject: TextView
        internal var sentat: TextView
        internal var date: TextView
        internal var lblNew: TextView
        internal var lblType: TextView
        internal var rytDetails1: RelativeLayout
        internal var assignmentList: ConstraintLayout
        internal var viewLine: ImageView

        init {
            content = view.findViewById<View>(R.id.lblAssignmnetTitle) as TextView
            subject = view.findViewById<View>(R.id.lblSubject) as TextView
            sentat = view.findViewById<View>(R.id.lblSentAt) as TextView
            date = view.findViewById<View>(R.id.lblDate) as TextView
            lblType = view.findViewById<View>(R.id.lblType) as TextView
            rytDetails1 = view.findViewById<View>(R.id.rytDetails1) as RelativeLayout
            assignmentList = view.findViewById<View>(R.id.assignmentList) as ConstraintLayout
            viewLine = view.findViewById<View>(R.id.viewLine) as ImageView
            lblNew = view.findViewById<View>(R.id.lblNew) as TextView

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.assignment_history_card, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val text_info = imagelist[position]
        holder.run {

            assignmentduelistener = assignmentListener
            assignmentduelistener?.onassignmentClick(holder, text_info)


            content.text = text_info.title
            subject.text = text_info.description
            date.text = text_info.end_date
            lblType.text = text_info.subject
            sentat.text = text_info.created_on
            sentat.visibility = View.VISIBLE
            viewLine.visibility=View.VISIBLE


            if (type) {
                date.setBackgroundResource(R.drawable.parent_blue_bg)
            } else {
                date.visibility = View.INVISIBLE
                viewLine.visibility = View.INVISIBLE
            }

            if(text_info.app_viewed == 1){
                holder.lblNew.visibility=View.GONE
            }
            else{
                holder.lblNew.visibility=View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int {
        return imagelist.size

    }
}
