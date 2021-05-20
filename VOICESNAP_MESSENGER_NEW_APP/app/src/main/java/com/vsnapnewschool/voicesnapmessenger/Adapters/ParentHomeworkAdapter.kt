package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.homeworkListener
import com.vsnapnewschool.voicesnapmessenger.Models.Leave_Class
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetHomeWorkListResponse
import com.vsnapnewschool.voicesnapmessenger.R


class ParentHomeworkAdapter(
    private var homeworklist: ArrayList<GetHomeWorkListResponse.ParentHomeworklist>,
    private val context: Context?,
    private val type: String,
    val homeworkListener: homeworkListener
) : RecyclerView.Adapter<ParentHomeworkAdapter.MyViewHolder>() {
    companion object {
        var homeworkClick: homeworkListener? = null
    }
    fun update(modelList:ArrayList<GetHomeWorkListResponse.ParentHomeworklist>){
        homeworklist = modelList
        notifyDataSetChanged()
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
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.assignment_history_card, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val text_info = homeworklist[position]
        holder.run {
            rytDetails1.setOnClickListener({
                homeworkClick = homeworkListener
                homeworkClick?.onhomeworkClick(holder,text_info)
            })
            lblType.visibility=View.GONE
            date.visibility=View.GONE
            content.text = text_info.homework_text
            subject.text = text_info.subject_name
            sentat.text = text_info.created_on
//            lblType.text = text_info.leavetype
//            sentat.visibility = View.INVISIBLE
            if (type.equals("0")) {
                date.setBackgroundResource(R.drawable.parent_blue_bg)

            } else {
                date.visibility = View.INVISIBLE

            }

        }
    }
    override fun getItemCount(): Int {
        return homeworklist.size

    }
}
