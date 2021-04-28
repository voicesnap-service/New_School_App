package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.checkFinalStudentListener
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StudentData

class ChildStudentsNamesAdapter(private val mContext: Context, private val studentlistener: checkFinalStudentListener?,
                                private var studentList: ArrayList<StudentData>,private var standardSectionName:String?,private var SectionID:String?,private var TitlePosition:Int?
) :
    RecyclerView.Adapter<ChildStudentsNamesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.selected_section_items, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.lblStudentName.setText(studentList[position].student_name)
        holder.chbox.isChecked=true

        studentlistener!!.addFinalStudent(studentList[position],standardSectionName,SectionID,TitlePosition)
        holder.chbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                studentlistener!!.addFinalStudent(studentList[position],standardSectionName,SectionID,TitlePosition)
            } else {
                studentlistener!!.removeFinalStudent(studentList[position],standardSectionName,SectionID,TitlePosition)
            }
        })
    }
    inner class MyViewHolder internal constructor(view: View) :
        RecyclerView.ViewHolder(view) {
        var lblStudentName: TextView
        var chbox: CheckBox
        init {
            lblStudentName = view.findViewById<View>(R.id.lblStandardSectionName) as TextView
            chbox = view.findViewById<View>(R.id.chbox) as CheckBox
        }
    }
    override fun getItemCount(): Int {
        return studentList.size
    }

}