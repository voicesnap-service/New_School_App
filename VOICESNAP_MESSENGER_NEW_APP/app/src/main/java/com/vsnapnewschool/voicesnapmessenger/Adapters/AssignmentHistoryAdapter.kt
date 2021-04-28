package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Models.EventsImageClass
import com.vsnapnewschool.voicesnapmessenger.R


class AssignmentHistoryAdapter(
    private var imagelist: ArrayList<EventsImageClass>,
    private val context: Context?,
    val btnlistener: AssignmentHistoryAdapter.layoutClickListener) : RecyclerView.Adapter<AssignmentHistoryAdapter.MyViewHolder>() {
    companion object {
        var mClickListener: layoutClickListener? = null
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var content: TextView
        internal var subject: TextView
        internal var sentat: TextView
        internal var date: TextView
        internal var lblType: TextView
        internal var rytDetails1: RelativeLayout
        internal var assignmentList: ConstraintLayout
        internal var viewLine: View
        init {
            content = view.findViewById<View>(R.id.lblAssignmnetTitle) as TextView
            subject = view.findViewById<View>(R.id.lblSubject) as TextView
            sentat = view.findViewById<View>(R.id.lblSentAt) as TextView
            date = view.findViewById<View>(R.id.lblDate) as TextView
            lblType = view.findViewById<View>(R.id.lblType) as TextView
            rytDetails1 = view.findViewById<View>(R.id.rytDetails1) as RelativeLayout
            assignmentList = view.findViewById<View>(R.id.assignmentList) as ConstraintLayout
            viewLine = view.findViewById<View>(R.id.viewLine) as View
        }
        fun bind(holder: MyViewHolder,leaveClass: EventsImageClass) {
            itemView.setOnClickListener {
                mClickListener?.onBtnClick(holder,leaveClass)
            }
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

            mClickListener = btnlistener
            mClickListener?.onBtnClick(holder,text_info)
            content.text = text_info.Content
            subject.text = text_info.Day
            date.text = text_info.Month
            lblType.text = text_info.filetype
            sentat.visibility = View.VISIBLE
            holder.lblType.setBackgroundResource(R.drawable.bg_semihalf_orange)
            viewLine.visibility=View.INVISIBLE
        }
    }
    override fun getItemCount(): Int {
        return imagelist.size
    }
    interface layoutClickListener {
        fun onBtnClick(holder:MyViewHolder,EventsImageClass:EventsImageClass)
    }
}
