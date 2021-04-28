package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.checkStudentListener
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StudentData
import kotlinx.android.synthetic.main.section_student_list_item.view.*

class StudentListAdapter(
    private val studentList: ArrayList<StudentData>,
    private val studentlistener: checkStudentListener?,
    private val SelectedHashmapStudentList: HashMap<String, MutableList<StudentData>>,
    private val StandardScetionID: String
) :
    RecyclerView.Adapter<StudentListAdapter.ViewHolder>() {

    override fun getItemCount() = studentList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.section_student_list_item,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Section = studentList[position]
        holder.lblSectinName.setText(Section.student_name)
        holder.checkbox.setOnCheckedChangeListener(null)

        if(!SelectedHashmapStudentList.isEmpty()) {
            Log.d("SelectedHashmapStudents",SelectedHashmapStudentList.toString())
            if(SelectedHashmapStudentList.containsKey(StandardScetionID)) {
                if (SelectedHashmapStudentList.get(StandardScetionID)!!.contains(Section)) {
                    holder.checkbox.isChecked = true
                }
            }
        }
        holder.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                studentlistener!!.add(Section)
            } else {
                studentlistener!!.remove(Section)
            }
        })

    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkbox: CheckBox = itemView.chooseCheck
        val lblSectinName: TextView = itemView.lblSectinName
    }
}