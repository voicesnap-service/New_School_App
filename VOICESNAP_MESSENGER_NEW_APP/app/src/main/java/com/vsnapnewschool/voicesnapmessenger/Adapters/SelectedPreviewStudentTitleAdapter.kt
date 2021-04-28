package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.checkFinalStudentListener
import com.vsnapnewschool.voicesnapmessenger.Models.SelectedPreviewStudent
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants

class SelectedPreviewStudentTitleAdapter(private val mContext: Context, private val studentlistener: checkFinalStudentListener?,
                                         private var studentList: List<SelectedPreviewStudent>) :
    RecyclerView.Adapter<SelectedPreviewStudentTitleAdapter.MyViewHolder>() {

    var recipientadpt: ChildStudentsNamesAdapter? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.selected_students_items, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.lblStandardSectionName.setText(studentList[position].standardSectionName)
        UtilConstants.textBoldContext(mContext,holder.lblStandardSectionName)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(mContext)
        holder.recycle_student_names?.setLayoutManager(mLayoutManager)
        holder.recycle_student_names?.setItemAnimator(DefaultItemAnimator())
        recipientadpt = ChildStudentsNamesAdapter(mContext,studentlistener, studentList[position].studentData,studentList[position].standardSectionName,studentList[position].sectionID,position)
        holder.recycle_student_names?.setAdapter(recipientadpt)

    }
    inner class MyViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        var lblStandardSectionName: TextView
        var recycle_student_names: RecyclerView
        init {
            lblStandardSectionName = view.findViewById<View>(R.id.lblStandardSectionName) as TextView
            recycle_student_names = view.findViewById<View>(R.id.recycle_student_names) as RecyclerView
        }
    }
    override fun getItemCount(): Int {
        return studentList.size
    }

}